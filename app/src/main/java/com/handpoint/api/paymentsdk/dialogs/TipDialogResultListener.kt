package com.handpoint.api.paymentsdk.dialogs

import java.io.Serializable

interface TipDialogResultListener : Serializable {

    fun addTip(tip: Int)

}