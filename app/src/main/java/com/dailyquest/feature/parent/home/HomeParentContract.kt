package com.dailyquest.feature.parent.homeParent

interface HomeParentViewContract{
    fun showChildrenList(list: List<Any>)
    fun openNewChildrenDialog()
}

interface HomeParentPresenterContract{
    fun addNewChildren()
    fun getChildrenList()
}