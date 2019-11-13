package com.dailyquest.dialog

import android.content.Context
import com.bumptech.glide.Glide
import com.dailyquest.R
import com.dailyquest.base.BaseDialog
import com.dailyquest.utils.Constants
import com.dailyquest.utils.SessionManager
import com.dailyquest.utils.beginWith
import com.dailyquest.utils.then
import kotlinx.android.synthetic.main.dialog_barcode.*

class BarcodeDialog(context: Context, private val pref: SessionManager) : BaseDialog(context) {
    override fun layoutId() = R.layout.dialog_barcode

    override fun setupView() {
        beginWith { setupContent() }
            .then { setupOnClick() }
    }

    private fun setupContent() {
        Glide.with(context).load(Constants.BARCODE_BASE_URL + pref.getParentUid())
            .into(iv_barcode)
    }

    private fun setupOnClick() {
        b_ok.setOnClickListener {
            dismiss()
        }
    }
}