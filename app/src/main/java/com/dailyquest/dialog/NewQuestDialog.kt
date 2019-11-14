package com.dailyquest.dialog

import android.content.Context
import com.dailyquest.R
import com.dailyquest.base.BaseDialog
import com.dailyquest.feature.parent.questList.view.QuestListActivity
import com.dailyquest.model.QuestModel
import com.dailyquest.utils.beginWith
import kotlinx.android.synthetic.main.dialog_new_quest.*

class NewQuestDialog(context: Context, private val addListener: (QuestModel) -> Unit) : BaseDialog(context) {
    override fun layoutId() = R.layout.dialog_new_quest

    override fun setupView() {
        super.setupView()

        beginWith { setupContent() }
    }

    private fun setupContent() {
        b_add.setOnClickListener {
            addListener.invoke(QuestModel("001", "coba1", "hello world"))
            dismiss()
        }
    }
}