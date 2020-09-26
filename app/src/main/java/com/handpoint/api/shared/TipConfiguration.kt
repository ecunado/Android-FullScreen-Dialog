package com.handpoint.api.shared

import java.io.Serializable
import java.math.BigInteger

class TipConfiguration(
        baseAmount: BigInteger,
        headerName: String,
        tipPercentages: List<Int>,
        enterAmountEnabled: Boolean,
        skipEnabled: Boolean,
        footer: String
) : Serializable {

    var baseAmount: BigInteger
    var headerName = "Tip"
    var tipPercentages: List<Int>
    var isEnterAmountEnabled: Boolean
    var isSkipEnabled: Boolean
    var footer = ""

    init {
        this.baseAmount = baseAmount
        this.headerName = headerName
        this.tipPercentages = tipPercentages
        isEnterAmountEnabled = enterAmountEnabled
        isSkipEnabled = skipEnabled
        this.footer = footer
    }
}