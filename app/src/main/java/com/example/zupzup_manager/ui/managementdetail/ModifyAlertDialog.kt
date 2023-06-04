package com.example.zupzup_manager.ui.managementdetail

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import com.example.zupzup_manager.R

class ModifyAlertDialog(context: Context) {

    lateinit var listener: MerchandiseDialogClickedListener
    lateinit var btnModify: Button
    lateinit var btnCancel: Button

    interface MerchandiseDialogClickedListener {
        fun onClicked()
    }

    private val dlg = Dialog(context)

    fun modify() {
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dlg.setContentView(R.layout.dialog_alert)

        btnModify = dlg.findViewById(R.id.btn_modify)
        btnModify.setOnClickListener {
            listener.onClicked()
            dlg.dismiss()
        }

        btnCancel = dlg.findViewById(R.id.btn_cancel_modify)
        btnCancel.setOnClickListener {
            dlg.dismiss()
        }
        dlg.show()
    }
}