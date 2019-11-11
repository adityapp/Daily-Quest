package com.dailyquest.feature.parent.home

import com.dailyquest.base.BasePresenter

interface HomeParentViewContract{
    fun showAllQuestList(list: List<Any>)
}

interface HomeParentPresenterContract: BasePresenter{
    fun getAllQuestList()
}