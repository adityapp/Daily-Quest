package com.dailyquest.dialog

import android.content.Context
import com.dailyquest.R
import com.dailyquest.base.BaseDialog
import com.dailyquest.model.QuestModel
import com.dailyquest.utils.*
import kotlinx.android.synthetic.main.dialog_detail_quest.*

class DetailQuestDialog(context: Context, private val pref: SessionManager) : BaseDialog(context) {
    var quest = QuestModel()

    override fun layoutId() = R.layout.dialog_detail_quest

    override fun setupView() {
        super.setupView()

        beginWith { setupContent() }
            .then { setupOnClick() }
    }

    private fun setupContent() {
        tv_create_time.text = quest.createdAt.timestampToDate()
        tv_title.text = quest.title
        setStatus(quest.status)
        tv_description.text = quest.description
        tv_start_time.text = quest.startTime.timestampToDate()
        tv_end_time.text = quest.endTime.timestampToDate()
    }

    private fun setupOnClick() {
        b_ok.setOnClickListener { dismiss() }
    }

    private fun setStatus(status: String) {
        tv_status.text = status

        rl_status.setBackgroundResource(
            when (status) {
                Constants.STATUS_OPEN -> R.drawable.rounded_background_blue
                Constants.STATUS_ONGOING -> R.drawable.rounded_background_green
                Constants.STATUS_CLOSE -> R.drawable.rounded_background_red
                else -> R.drawable.rounded_background_gray
            }
        )
    }
}