package com.handpoint.api.paymentsdk.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.handpoint.api.shared.TipConfiguration
import io.alexanderschaefer.fullscreendialog.R

class TipDialog : DialogFragment() {

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
        /*toolbar!!.setNavigationOnClickListener { v: View? -> dismiss() }
        toolbar!!.title = config.headerName
        toolbar!!.inflateMenu(R.menu.tipping_dialog)
        toolbar!!.setOnMenuItemClickListener { item: MenuItem? ->
            dismiss()
            true
        }*/
    }

    companion object {
        const val TAG = "tipping_dialog"

        @JvmStatic
        fun display(fragmentManager: FragmentManager, config: TipConfiguration): TipDialog {
            val args = Bundle()
            args.putSerializable("config", config)
            val tipDialog = TipDialog()
            tipDialog.arguments = args
            tipDialog.show(fragmentManager, TAG)
            return tipDialog
        }
    }

}