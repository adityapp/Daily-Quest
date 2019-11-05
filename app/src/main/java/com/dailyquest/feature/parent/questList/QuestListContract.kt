package com.dailyquest.feature.parent.questList

import com.dailyquest.base.BasePresenter

interface QuestListViewContract{
    fun showQuestList(list: List<Any>)
    fun openNewChildrenDialog()
}

interface QuestListPresenterContract: BasePresenter{
    fun addNewQuest()
    fun getQuestList()
}