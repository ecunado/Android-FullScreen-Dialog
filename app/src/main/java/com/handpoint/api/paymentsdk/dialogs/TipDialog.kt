package com.handpoint.api.paymentsdk.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.card.MaterialCardView
import com.handpoint.api.shared.TipConfiguration
import io.alexanderschaefer.fullscreendialog.R
import kotlinx.android.synthetic.main.tip_dialog.*


const val CONFIG_PARAM = "config"
const val LISTENER_PARAM = "listener"
const val TIPS_PER_ROW = 1 // Number of tip cards per row

class TipDialog: FullScreenDialog(), View.OnClickListener {

    private var tipConfiguration: TipConfiguration? = null
    private var tipList: MutableList<MaterialCardView> = mutableListOf()
    private var selectedTip: Int = 0
    private var selectedTipView: MaterialCardView? = null
    private var listener: TipDialogResultListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.tip_dialog, container, false)
    }

    override fun init() {
        tipConfiguration = this.arguments?.get(CONFIG_PARAM) as TipConfiguration
        listener = this.arguments?.get(LISTENER_PARAM) as TipDialogResultListener
        addTips(tipConfiguration?.tipPercentages)
        finishBtn.setOnClickListener(this);
        skip.setOnClickListener(this);
        reset()
    }

    private fun reset() {
        selectedTip = 0
        title.text = tipConfiguration?.headerName
        refreshAmounts()
    }

    private fun refreshAmounts() {
        val total: Float? = tipConfiguration?.baseAmount?.toFloat()?.plus(getTipAmount(selectedTip ?: 0))
        totalValue.text = formatCurrency(total ?: 0f)
        // Action button
        finishBtn.text = "Finish  •  " + totalValue.text  // i18n & TODO Format total amount
        finishBtn.isEnabled = selectedTipView != null
    }

    private fun getTipAmount(percentage: Int): Float {
        val tip: Float? = tipConfiguration?.baseAmount?.toFloat()?.times(percentage.toFloat() / 100f)
        return (tip ?: 0f)
    }

    /**
     * Adds a set of tips to the list.
     * The list will be cleared before adding the new list
     */
    private fun addTips(percentages: List<Int>?) {
        // Remove existing cards
        tipLinearLayout.removeAllViews()
        // Iterate over [TIPS_PER_ROW] tips at a time
        percentages?.chunked(TIPS_PER_ROW)?.forEach {
            addTipRow(it)
        }
    }

    /**
     * Adds a row of tip cards to the tip list
     */
    private fun addTipRow(percentages: List<Int>) {
        if (percentages.size > TIPS_PER_ROW) {
            throw Exception("Only $TIPS_PER_ROW tips allowed for each row")
        }
        for (percentage in percentages) {
            addTip(percentage)
        }
    }

    /**
     * Adds a tip card to the linear layout
     */
    private fun addTip(percentage: Int) {
        // Create the card
        val tipView: View = layoutInflater.inflate(R.layout.tip_card, null,false)
        val materialCard = (tipView as MaterialCardView)

        var percentageView: TextView = materialCard.findViewById<TextView>(R.id.percentage)
        var amountView: TextView = materialCard.findViewById<TextView>(R.id.amount)
        val tipAmount: Float = getTipAmount(percentage)

        percentageView.text = formatPercentage(percentage)
        amountView.text = formatCurrency(tipAmount)

        // Set click listener to check the card
        materialCard.setOnClickListener {
            // Uncheck the rest of cards
            tipList.forEach {
                if (it != tipView) it.isChecked = false
            }
            // Check clicked card
            tipView.isChecked = !tipView.isChecked
            if (tipView.isChecked) {
                // Save selected percentage
                selectedTip = percentage
                selectedTipView = materialCard
            } else {
                selectedTip = 0
                selectedTipView = null
            }
            refreshAmounts()

            true
        }
        // Add to local collection
        tipList.add(materialCard)
        // Add to the UI
        tipLinearLayout.addView(tipView)
    }

    private fun formatPercentage(percentage: Int): String {
        return "$percentage %" // TODO i18n
    }

    private fun formatCurrency(amount: Float): String {
        return "$amount €" // TODO i18n
    }

    override fun onClick(v: View) {
        if (v?.id?.equals(R.id.finishBtn) || v?.id?.equals(R.id.skip)) {
            if (v?.id?.equals(R.id.skip)) {
                reset()
            }
            Toast.makeText(this.context, "Percentage $selectedTip", Toast.LENGTH_SHORT).show()
            listener?.addTip(selectedTip)
            dismiss()
        }

    }

    companion object {
        private const val TAG = "tip_dialog"

        @JvmStatic
        fun display(fragmentManager: FragmentManager, config: TipConfiguration, listener: TipDialogResultListener): TipDialog {
            val args = Bundle().apply {
                putSerializable(CONFIG_PARAM, config)
                putSerializable(LISTENER_PARAM, listener)
            }
            val tipDialog = TipDialog()
            tipDialog.arguments = args
            tipDialog.show(fragmentManager, TAG)
            return tipDialog
        }
    }

}
