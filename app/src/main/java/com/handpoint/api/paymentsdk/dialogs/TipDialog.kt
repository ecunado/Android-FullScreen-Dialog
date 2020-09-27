package com.handpoint.api.paymentsdk.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.card.MaterialCardView
import com.handpoint.api.shared.TipConfiguration
import io.alexanderschaefer.fullscreendialog.R
import kotlinx.android.synthetic.main.tip_card.view.*
import kotlinx.android.synthetic.main.tipping_dialog.*


const val CONFIG_PARAM = "config"
const val LISTENER_PARAM = "listener"
const val TIPS_PER_ROW = 2 // Number of tip cards per row

class TipDialog : DialogFragment(), View.OnClickListener {

    var tipList: MutableList<MaterialCardView> = mutableListOf();

    var selectedTip: Int = 0;
    var selectedTipView: MaterialCardView? = null;

    var listener: TipDialogResultListener? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme_FullScreenDialog)
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width, height)
            dialog.window?.setWindowAnimations(R.style.AppTheme_Slide)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.tipping_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        /*toolbar!!.setNavigationOnClickListener { v: View? -> dismiss() }
        toolbar!!.title = config.headerName
        toolbar!!.inflateMenu(R.menu.tipping_dialog)
        toolbar!!.setOnMenuItemClickListener { item: MenuItem? ->
            dismiss()
            true
        }*/
    }

    private fun init() {
        val tipConfiguration: TipConfiguration = this.arguments?.get(CONFIG_PARAM) as TipConfiguration
        listener = this.arguments?.get(LISTENER_PARAM) as TipDialogResultListener

        totalLabel!!.text = "Total"
        totalValue!!.text = "1.200,12 €"
        tipLabel!!.text = tipConfiguration.headerName
        tipValue!!.text = "100 €"
        amountLabel!!.text = "Amount"
        amountValue.text = "1.100,12 €"
        addTips(tipConfiguration.tipPercentages)
        finishBtn.setOnClickListener(this);
    }

    /**
     * Adds a set of tips to the list.
     * The list will be cleared before adding the new list
     */
    private fun addTips(percentages: List<Int>) {
        // Remove existing cards
        tipLinearLayout.removeAllViews()
        // Iterate over [TIPS_PER_ROW] tips at a time
        percentages.chunked(TIPS_PER_ROW).forEach {
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
            addTipClosure(percentage)
        }
    }

    /**
     * Adds a tip card to the linear layout
     */
    private fun addTipClosure(percentage: Int) {
        // Create the card
        val tipView: View = layoutInflater.inflate(R.layout.tip_card, null,false)
        val materialCard = (tipView as MaterialCardView)

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
                selectedTipView = tipView
                // Update finish button and enable it
                finishBtn.text = "Finish  •  " + tipView.amount.text
                finishBtn.isEnabled = true
            } else {
                selectedTip = 0
                selectedTipView = null
                finishBtn.text = "Finish"
                finishBtn.isEnabled = false
            }

            true
        }
        // Add to local collection
        tipList.add(materialCard)
        // Add to the UI
        tipLinearLayout.addView(tipView)
    }

    companion object {
        const val TAG = "tipping_dialog"

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

    override fun onClick(v: View) {
        if (v?.id?.equals(R.id.finishBtn)) {
            Toast.makeText(this.context, "Percentage $selectedTip", Toast.LENGTH_SHORT).show()
            listener?.addTip(selectedTip)
            dismiss()
        }

    }

}
