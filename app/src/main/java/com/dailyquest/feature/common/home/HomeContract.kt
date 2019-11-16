package com.dailyquest.feature.common.home

import com.dailyquest.base.BasePresenter
import com.dailyquest.model.QuestModel

interface HomeViewContract {
    fun showQuestList(list: List<QuestModel>)
    fun showFailedMessage(message: String)
    fun showLoadingDialog(): Unit?
    fun dismissLoadingDialog(): Unit?
}

interface HomePresenterContract : BasePresenter {
    fun getAllQuestList()
    fun getQuestList()
}