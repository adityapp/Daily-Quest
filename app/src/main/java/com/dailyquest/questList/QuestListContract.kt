package com.dailyquest.questList

interface QuestListViewContract{
    fun showQuestList(list: List<Any>)
    fun openNewChildrenDialog()
}

interface QuestListPresenterContract{
    fun addNewQuest()
    fun getQuestList()
}