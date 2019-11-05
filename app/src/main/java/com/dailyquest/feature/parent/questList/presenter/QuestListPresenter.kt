package com.dailyquest.feature.common.questList.presenter

import com.dailyquest.feature.common.questList.QuestListPresenterContract
import com.dailyquest.feature.common.questList.QuestListViewContract

class QuestListPresenter(private val view: QuestListViewContract) : QuestListPresenterContract {
    override fun addNewQuest() {

    }

    override fun getQuestList() {
        view.showQuestList(listOf())
    }
}