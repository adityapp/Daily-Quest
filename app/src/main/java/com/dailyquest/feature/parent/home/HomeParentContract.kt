package com.dailyquest.feature.parent.home

import com.dailyquest.base.BasePresenter

interface HomeParentViewContract{
    fun showChildrenList(list: List<Any>)
    fun openNewChildrenDialog()
}

interface HomeParentPresenterContract: BasePresenter{
    fun addNewChildren()
    fun getChildrenList()
}