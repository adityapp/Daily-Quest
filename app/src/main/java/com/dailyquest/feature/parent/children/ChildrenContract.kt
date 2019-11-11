package com.dailyquest.feature.parent.children

import com.dailyquest.base.BasePresenter

interface ChildrenViewContract {
    fun showChildrenList(list: List<Any>)
    fun openNewChildrenDialog()
}

interface ChildrenPresenterContract : BasePresenter{
    fun addNewChildren()
    fun getChildrenList()
}