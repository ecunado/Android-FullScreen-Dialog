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
    private var customAmountCard: MaterialCardView? = null
    private var skipCard: MaterialCardView? = null
    private var selectedTipView: MaterialCardView? = null

    private var tipAmount: BigInteger = BigInteger("0")
    private var selectedTip: Int = 0

    private var listener: TipDialogResultListener? = null

    // PAD
    private var customAmount: String = "0"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.tip_dialog, container, false)
    }

    override fun init() {
        tipConfiguration = this.arguments?.get(CONFIG_PARAM) as TipConfiguration
        currency = this.arguments?.get(CURRENCY_PARAM) as Currency
        listener = this.arguments?.get(LISTENER_PARAM) as TipDialogResultListener

        initTipFragment()
        initPadFragment()

        reset()
    }

    private fun initTipFragment() {
        addTips(tipConfiguration?.tipPercentages)
        if (tipConfiguration!!.isEnterAmountEnabled) {
            addCustomAmountAction("Custom Amount", "Enter a custom amount")   // TODO i18n
        }
        if (tipConfiguration!!.isSkipEnabled) {
            addSkipAction("Skip", "Continue")                  // TODO i18n
        }
        if (tipConfiguration!!.footer != null) {
            footer.text = tipConfiguration!!.footer
        }
        finishBtn.setOnClickListener(this);
    }

    private fun initPadFragment() {
        zeroButton.setOnClickListener(this)
        oneButton.setOnClickListener(this)
        twoButton.setOnClickListener(this)
        threeButton.setOnClickListener(this)
        fourButton.setOnClickListener(this)
        fiveButton.setOnClickListener(this)
        sixButton.setOnClickListener(this)
        sevenButton.setOnClickListener(this)
        eightButton.setOnClickListener(this)
        nineButton.setOnClickListener(this)
        acceptButton.setOnClickListener(this)
        deleteButton.setOnClickListener(this)
        cancelButton.setOnClickListener {
            hidePad()
            true
        }
    }

    private fun reset() {
        selectedTip = 0
        title.text = tipConfiguration?.headerName
        refreshAmounts()
    }

    private fun refreshAmounts() {
        // Action button
        finishBtn.text = "TOTAL  •  " + formatCurrency(getTotalAmount())  // i18n & TODO Format total amount
        finishBtn.isEnabled = selectedTipView != null
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
        // Remove the rest of placeholders in the row
        for (x in percentages.size until TIPS_PER_ROW) {
            hideCardTipPlaceholderFromRow(x, row)
        }
        // Add row to the UI
        tipLinearLayout.addView(row)
    }

    /**
     * Hides a tip card placeholder from the row
     */
    private fun hideCardTipPlaceholderFromRow(n: Int, row: View) {
        // Get card placeholder
        val materialCard = getCardTipById("$CARD_ID$n", row)
        materialCard.alpha = 0F
    }

    /**
     * Adds a tip card to the linear layout
     */
    private fun addCardTipToRow(percentage: Int, n: Int, row: View) {
        // Get card placeholder
        val materialCard = getCardTipById("$CARD_ID$n", row)

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

    private fun getCardTipById(id: String, row: View): MaterialCardView {
        var id: Int = resources.getIdentifier(id, "id", context?.packageName)
        return row.findViewById(id) as MaterialCardView
    }

    private fun formatPercentage(percentage: Int): String {
        return "$percentage %" // TODO i18n
    }

    private fun formatCurrency(amount: BigInteger): String {
         return i18n.formatCurrencyCode(amount.toString(), currency?.alpha ?: Currency.GBP.alpha)
    }

    private fun addCustomAmountAction(title: String, subtitle: String) {
        customAmountCard = createCardAction(title, subtitle)

        // Set click listener to check the card
        customAmountCard!!.setOnClickListener {
            // Show pad
            showPad()
            true
        }
    }

    private fun addSkipAction(title: String, subtitle: String) {
        skipCard = createCardAction(title, subtitle)

        // Set click listener to check the card
        skipCard!!.setOnClickListener {
            skip()
            true
        }
    }

    private fun createCardAction(title: String, subtitle: String): MaterialCardView? {
        // Create the row
        val row: View = layoutInflater.inflate(R.layout.action_row, null,false)

        var card = getCardTipById(CARD_ID, row)

        // Set title / subtitle
        var titleView: TextView = row.findViewById<TextView>(R.id.percentage)
        var amountView: TextView = row.findViewById<TextView>(R.id.amount)

        titleView.text = title
        amountView.text = subtitle

        // Add row to the UI
        tipLinearLayout.addView(row)

        return card
    }


    private fun showPad() {
        refreshPad()
        containerFlipper.inAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right)
        containerFlipper.outAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_out_left)
        containerFlipper.showNext()
    }

    private fun hidePad() {
        containerFlipper.inAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_in_left)
        containerFlipper.outAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_out_right)
        containerFlipper.showPrevious()
    }

    private fun skip() {
        reset()
        callback()
    }

    private fun callback() {
        Toast.makeText(this.context, "Tip $tipAmount", Toast.LENGTH_SHORT).show()
        listener?.addTip(tipAmount)
        dismiss()
    }

    private fun padNumberPressed(n: String) {
        customAmount = customAmount.plus(n)
        refreshPad()
    }

    private fun delPressed() {
        if (customAmount.isNotEmpty()) {
            customAmount = customAmount.dropLast(1)
        }
        refreshPad()
    }

    private fun acceptCustomAmountPressed() {
        acceptCustomAmount()
        hidePad()
    }

    private fun acceptCustomAmount() {
        getTotalAmount()
        tipAmount = safeAmount(customAmount)
        selectedTip = getTipPercentage(tipAmount).toInt()
        refreshAmounts()
    }

    private fun refreshPad() {
        var customAmountSafe: BigInteger = safeAmount(customAmount)
        val total: BigInteger = tipConfiguration?.baseAmount?.plus(customAmountSafe) ?: BigInteger("0")
        padAmount.text = formatCurrency(customAmountSafe)
        padTotalAmount.text = "Total: " + formatCurrency(total)
    }

    private fun safeAmount(str: String): BigInteger {
        return if (str.isNotEmpty()) {
            BigInteger(str)
        } else {
            BigInteger("0")
        }
    }

    private fun getTipAmount(percentage: Int): BigInteger {
        val amount: Double = ((tipConfiguration?.baseAmount?.toInt() ?: 0) / BigInteger("100").toDouble()) * percentage.toDouble()
        return BigDecimal.valueOf(amount).toBigInteger()
    }

    private fun getTipPercentage(tip: BigInteger): BigInteger {
        val amount: Double = (tip.toDouble() * BigInteger("100").toDouble()) /  (tipConfiguration?.baseAmount?.toInt() ?: 0)
        return BigDecimal.valueOf(amount).toBigInteger()
    }

    private fun getTotalAmount(): BigInteger {
        return tipConfiguration?.baseAmount?.plus(getTipAmount(selectedTip ?: 0)) ?: BigInteger("0")
    }

    override fun onClick(v: View) {
        // Finish button pressed
        if (v?.id?.equals(R.id.finishBtn)) {
            callback()
        }
        // Pad number pressed
        else if (v?.tag == NUMBER_KEY) {
            padNumberPressed((v as TextView).text.toString())
        }
        // Delete pressed
        else if (v?.tag == DEL_KEY) {
            delPressed()
        }
        // Accept pressed
        else if (v?.tag == ACCEPT_KEY) {
            acceptCustomAmountPressed()
        }

    }

    companion object {

        const val CARD_ID: String = "card"
        const val NUMBER_KEY: String = "number"
        const val DEL_KEY: String = "delete"
        const val ACCEPT_KEY: String = "accept"

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
