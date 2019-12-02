package com.dailyquest.feature.common.detailQuest

import android.net.Uri
import com.dailyquest.base.BasePresenter
import com.dailyquest.model.QuestModel

interface DetailQuestViewContract {
    fun setupContent(newQuest: QuestModel)
    fun showFailedMessage(message: String)
    fun showLoadingDialog(): Unit?
    fun dismissLoadingDialog(): Unit?
}

interface DetailQuestPresenterContract : BasePresenter {
    fun getToken(id: String, childrenUid: String)
    fun updateQuest(quest: QuestModel, selectImage: Uri?)
}