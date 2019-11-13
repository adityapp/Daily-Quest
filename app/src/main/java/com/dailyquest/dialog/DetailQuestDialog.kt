package com.dailyquest.dialog

import android.content.Context
import com.dailyquest.R
import com.dailyquest.base.BaseDialog
import com.dailyquest.utils.SessionManager
import com.dailyquest.utils.beginWith

class DetailQuestDialog(context: Context, private val pref: SessionManager): BaseDialog(context) {

    override fun layoutId() = R.layout.dialog_detail_quest

    override fun setupView() {
        beginWith { setupContent() }
    }

    private fun setupContent(){

    }
}