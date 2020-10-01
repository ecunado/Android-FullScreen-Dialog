package com.handpoint.api.paymentsdk.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.handpoint.api.shared.Currency
import io.alexanderschaefer.fullscreendialog.R

const val CURRENCY_PARAM = "currency"

open class PadDialog : DialogFragment(), View.OnClickListener {

    private var currency: Currency? = null
    private var listener: PadDialogResultListener? = null

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
        return inflater.inflate(R.layout.pad_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        currency = this.arguments?.get(CURRENCY_PARAM) as Currency
        listener = this.arguments?.get(LISTENER_PARAM) as PadDialogResultListener
        //finishBtn.setOnClickListener(this);
        //skip.setOnClickListener(this);
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    companion object {
        private const val TAG = "pad_dialog"

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