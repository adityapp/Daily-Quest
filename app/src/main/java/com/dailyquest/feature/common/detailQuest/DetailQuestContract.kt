package com.dailyquest.feature.common.detailQuest

import com.dailyquest.base.BasePresenter
import com.dailyquest.model.QuestModel

interface DetailQuestViewContract {
    fun showFailedMessage(message: String)
    fun showLoadingDialog(): Unit?
    fun dismissLoadingDialog(): Unit?
}

interface DetailQuestPresenterContract : BasePresenter {
    fun uploadImageToDatabse()
    fun updateQuest(quest: QuestModel)
}