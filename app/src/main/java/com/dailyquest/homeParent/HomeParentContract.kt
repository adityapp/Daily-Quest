package com.dailyquest.homeParent

interface HomeParentViewContract{
    fun showQuestList(list: List<Any>)
    fun openNewQuestDialog()
}

interface HomeParentPresenterContract{
    fun addNewQuest()
    fun getQuestList()
}