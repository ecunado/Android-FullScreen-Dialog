package com.handpoint.api.paymentsdk.dialogs

import java.io.Serializable
import java.math.BigInteger

interface TipDialogResultListener : Serializable {

    fun addTip(tip: BigInteger)

}