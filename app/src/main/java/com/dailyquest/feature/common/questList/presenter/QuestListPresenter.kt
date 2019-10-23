package com.dailyquest.feature.questList.presenter

import com.dailyquest.feature.questList.QuestListPresenterContract
import com.dailyquest.feature.questList.QuestListViewContract

class QuestListPresenter(private val view: QuestListViewContract) : QuestListPresenterContract {
    override fun addNewQuest() {

    }

    override fun getQuestList() {
        view.showQuestList(listOf())
    }
}