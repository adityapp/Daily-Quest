package com.dailyquest.feature.parent.main

import com.dailyquest.base.BasePresenter

interface MainParentViewContract{
    fun navigateToRole()
}

interface MainParentPresenterContract: BasePresenter{
    fun logout()
}