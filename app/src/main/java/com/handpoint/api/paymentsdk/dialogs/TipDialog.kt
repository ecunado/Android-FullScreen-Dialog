package com.handpoint.api.paymentsdk.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.google.android.material.card.MaterialCardView
import com.handpoint.api.shared.Currency
import com.handpoint.api.shared.TipConfiguration
import com.handpoint.api.shared.i18n.i18n
import io.alexanderschaefer.fullscreendialog.R
import kotlinx.android.synthetic.main.tip_dialog.*
import java.math.BigDecimal
import java.math.BigInteger

class TipDialog: FullScreenDialog(), View.OnClickListener {

    private var tipConfiguration: TipConfiguration? = null
    private var currency: Currency? = null
    private var rowList: MutableList<View> = mutableListOf()
    private var cardList: MutableList<MaterialCardView> = mutableListOf()
    private var selectedTip: Int = 0
    private var selectedTipView: MaterialCardView? = null
    private var listener: TipDialogResultListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.tip_dialog, container, false)
    }

    override fun init() {

        // Set view animations
        containerFlipper.inAnimation = AnimationUtils.loadAnimation(context, R.anim.right_to_left)
        containerFlipper.outAnimation = AnimationUtils.loadAnimation(context, R.anim.left_to_right)

        tipConfiguration = this.arguments?.get(CONFIG_PARAM) as TipConfiguration
        currency = this.arguments?.get(CURRENCY_PARAM) as Currency
        listener = this.arguments?.get(LISTENER_PARAM) as TipDialogResultListener
        addTips(tipConfiguration?.tipPercentages)
        if (tipConfiguration!!.isEnterAmountEnabled) {
            addCustomAmountAction("Custom Amount")   // TODO i18n
        }
        if (tipConfiguration!!.isSkipEnabled) {
            addSkipAction("Skip")                  // TODO i18n
        }
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
        val total: BigInteger? = tipConfiguration?.baseAmount?.plus(getTipAmount(selectedTip ?: 0))
        // Action button
        finishBtn.text = "TOTAL  •  " + formatCurrency(total ?: BigInteger("0"))  // i18n & TODO Format total amount
        finishBtn.isEnabled = selectedTipView != null
    }

    private fun getTipAmount(percentage: Int): BigInteger {
        val amount: Double = ((tipConfiguration?.baseAmount?.toInt() ?: 0) / BigInteger("100").toDouble()) * percentage.toDouble()
        return BigDecimal.valueOf(amount).toBigInteger()
    }

    /**
     * Adds a set of tips to the list.
     * The list will be cleared before adding the new list
     */
    private fun addTips(percentages: List<Int>?) {
        // Remove existing cards
        tipLinearLayout.removeAllViews()
        rowList = mutableListOf()
        cardList = mutableListOf()
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

        // Create the row
        val row: View = layoutInflater.inflate(R.layout.tip_row, null,false)
        for ((i, percentage) in percentages.withIndex()) {
            addCardTipToRow(percentage, i, row)
            rowList.add(i, row)
        }
        // Add row to the UI
        tipLinearLayout.addView(row)
    }

    /**
     * Adds a tip card to the linear layout
     */
    private fun addCardTipToRow(percentage: Int, n: Int, row: View) {
        // Create the card
        val materialCard = getCardTipByIdRowById("$CARD_ID$n", row)

        var percentageView: TextView = materialCard.findViewById<TextView>(R.id.percentage)
        var amountView: TextView = materialCard.findViewById<TextView>(R.id.amount)
        val tipAmount: BigInteger = getTipAmount(percentage)

        percentageView.text = formatPercentage(percentage)
        amountView.text = formatCurrency(tipAmount)

        // Set click listener to check the card
        materialCard.setOnClickListener {
            // Uncheck the rest of cards
            uncheckCards(materialCard)
            // Check clicked card
            materialCard.isChecked = !materialCard.isChecked
            if (materialCard.isChecked) {
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

        cardList.add(materialCard)
    }

    private fun uncheckCards(materialCard: MaterialCardView) {
        for (i in 0 until cardList.size) {
            if (cardList[i] != materialCard) cardList[i].isChecked = false
        }
    }

    private fun getCardTipByIdRowById(id: String, row: View): MaterialCardView {
        var id: Int = resources.getIdentifier(id, "id", context?.packageName)
        return row.findViewById(id) as MaterialCardView
    }

    private fun formatPercentage(percentage: Int): String {
        return "$percentage %" // TODO i18n
    }

    private fun formatCurrency(amount: BigInteger): String {
         return i18n.formatCurrencyCode(amount.toString(), currency?.alpha ?: Currency.GBP.alpha)
    }

    private fun addCustomAmountAction(title: String): MaterialCardView {
        // Create the row
        val row: View = layoutInflater.inflate(R.layout.action_row, null,false)
        val materialCard = getCardTipByIdRowById(CARD_ID, row)
        // Attache
        var titleView: TextView = row.findViewById<TextView>(R.id.percentage)
        var amountView: TextView = row.findViewById<TextView>(R.id.amount)

        titleView.text = title

        // Set click listener to check the card
        materialCard.setOnClickListener {
            // Uncheck the rest of cards
            uncheckCards(materialCard)
            // Check clicked card
            materialCard.isChecked = !materialCard.isChecked

            containerFlipper.showNext()
            true
        }

        // Add row to the UI
        tipLinearLayout.addView(row)

        return materialCard
    }

    private fun addSkipAction(title: String): MaterialCardView? {
        return null
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

        const val CARD_ID: String = "card"

        const val CONFIG_PARAM: String = "config"
        const val CURRENCY_PARAM: String = "currency"
        const val LISTENER_PARAM: String = "listener"
        const val TIPS_PER_ROW: Int = 2 // Number of tip cards per row

        private const val TAG = "tip_dialog"

        @JvmStatic
        fun display(fragmentManager: FragmentManager, config: TipConfiguration, currency: Currency, listener: TipDialogResultListener): TipDialog {
            val tipDialog = TipDialog()
            val args = Bundle().apply {
                putSerializable(CONFIG_PARAM, config)
                putSerializable(CURRENCY_PARAM, currency)
                putSerializable(LISTENER_PARAM, listener)
            }
            tipDialog.arguments = args
            tipDialog.show(fragmentManager, TAG)
            return tipDialog
        }
    }

}
