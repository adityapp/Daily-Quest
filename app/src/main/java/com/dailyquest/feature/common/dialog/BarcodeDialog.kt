package com.dailyquest.feature.common.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.dailyquest.R
import com.dailyquest.utils.Constants
import com.dailyquest.utils.SessionManager
import kotlinx.android.synthetic.main.dialog_barcode.*

class BarcodeDialog(context: Context, private val pref: SessionManager) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.dialog_barcode)
        window?.let {
            it.setBackgroundDrawableResource(android.R.color.transparent)
            it.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }

        Glide.with(context).load(Constants.BARCODE_BASE_URL + pref.getParentUid())
            .into(iv_barcode)

        b_ok.setOnClickListener {
            dismiss()
        }
    }
}