package com.handpoint.api.paymentsdk.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.handpoint.api.shared.Currency
import io.alexanderschaefer.fullscreendialog.R

open class PadDialog : FullScreenDialog(), View.OnClickListener {

    private var currency: Currency? = null
    private var listener: PadDialogResultListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.pad_dialog, container, false)
    }

    override fun init() {
        currency = this.arguments?.get(CURRENCY_PARAM) as Currency
        listener = this.arguments?.get(LISTENER_PARAM) as PadDialogResultListener
        //finishBtn.setOnClickListener(this);
        //skip.setOnClickListener(this);
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    companion object {

        const val CURRENCY_PARAM: String = "currency"
        const val LISTENER_PARAM: String = "listener"
        private const val TAG: String = "pad_dialog"

        @JvmStatic
        fun display(fragmentManager: FragmentManager, currency: Currency, listener: PadDialogResultListener): PadDialog {
            val args = Bundle().apply {
                putSerializable(CURRENCY_PARAM, currency)
                putSerializable(LISTENER_PARAM, listener)
            }
            val padDialog = PadDialog()
            padDialog.arguments = args
            padDialog.show(fragmentManager, TAG)
            return padDialog
        }
    }

}