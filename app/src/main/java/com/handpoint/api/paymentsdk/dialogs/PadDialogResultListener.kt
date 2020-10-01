package com.handpoint.api.paymentsdk.dialogs

import java.io.Serializable

interface PadDialogResultListener : Serializable {

    fun amount(amount: Int)

}