package com.dailyquest.feature.parent.children

import com.dailyquest.base.BasePresenter
import com.dailyquest.model.ChildrenModel

interface ChildrenViewContract {
    fun showChildrenList(list: List<ChildrenModel>)
    fun showFailedMessage(message: String)
    fun showLoadingDialog(): Unit?
    fun dismissLoadingDialog(): Unit?
}

interface ChildrenPresenterContract : BasePresenter {
    fun getChildrenList()
}