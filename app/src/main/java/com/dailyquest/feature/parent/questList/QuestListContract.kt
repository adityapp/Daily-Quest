package com.dailyquest.feature.parent.questList

import com.dailyquest.base.BasePresenter
import com.dailyquest.model.QuestModel

interface QuestListViewContract {
    fun showQuestList(list: List<QuestModel>)
    fun openNewQuestDialog(addListener: (QuestModel) -> Unit)
    fun showFailedMessage(message: String)
    fun showLoadingDialog()
    fun dismissLoadingDialog()
}

interface QuestListPresenterContract : BasePresenter {
    fun addNewQuest()
    fun getQuestList()
}