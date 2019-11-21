package com.dailyquest.feature.common.main

import com.dailyquest.base.BasePresenter

interface MainViewContract{
    fun navigateToRole()
}

interface MainPresenterContract: BasePresenter{
    fun logout()
    fun sendTokenToDatabase()
}