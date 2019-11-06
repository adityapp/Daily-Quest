package com.dailyquest.feature.parent.questList.presenter

import com.dailyquest.feature.parent.questList.QuestListPresenterContract
import com.dailyquest.feature.parent.questList.QuestListViewContract

class QuestListPresenter(private val view: QuestListViewContract) : QuestListPresenterContract {
    override fun addNewQuest() {

    }

    override fun getQuestList() {
        view.showQuestList(listOf())
    }
}