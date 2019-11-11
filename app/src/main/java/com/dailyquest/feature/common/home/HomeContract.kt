package com.dailyquest.feature.common.home

import com.dailyquest.base.BasePresenter

interface HomeViewContract{
    fun showAllQuestList(list: List<Any>)
}

interface HomePresenterContract: BasePresenter{
    fun getAllQuestList()
}