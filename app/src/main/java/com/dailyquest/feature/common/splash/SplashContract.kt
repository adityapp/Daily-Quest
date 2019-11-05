package com.dailyquest.feature.common.splash

import com.dailyquest.base.BasePresenter

interface SplashViewContract {
    fun navigateToSelectRole()
    fun navigateToHomeParent()
    fun navigateToHomeChild()
    fun showFailedMessage(message: String)
}

interface SplashPresenterContract: BasePresenter{
    fun auth()
}