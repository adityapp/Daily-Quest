package com.dailyquest.feature.common.home

import com.dailyquest.base.BasePresenter
import com.dailyquest.model.QuestModel

interface HomeViewContract{
    fun showAllQuestList(list: List<QuestModel>)
}

interface HomePresenterContract: BasePresenter{
    fun getAllQuestList()
}