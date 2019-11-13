package com.dailyquest.dialog

import android.content.Context
import com.dailyquest.R
import com.dailyquest.base.BaseDialog
import com.dailyquest.utils.SessionManager
import com.dailyquest.utils.beginWith
import com.dailyquest.utils.then
import kotlinx.android.synthetic.main.dialog_detail_quest.*

class DetailQuestDialog(context: Context, private val pref: SessionManager) : BaseDialog(context) {

    override fun layoutId() = R.layout.dialog_detail_quest

    override fun setupView() {
        super.setupView()

        beginWith { setupContent() }
            .then { setupOnClick() }
    }

    private fun setupContent() {

    }

    private fun setupOnClick() {
        b_ok.setOnClickListener { dismiss() }
    }
}