package com.dailyquest.questList.presenter

import com.dailyquest.questList.QuestListPresenterContract
import com.dailyquest.questList.QuestListViewContract

class QuestListPresenter(private val view: QuestListViewContract) : QuestListPresenterContract {
    override fun addNewQuest() {

    }

    override fun getQuestList() {
        view.showQuestList(listOf())
    }
}