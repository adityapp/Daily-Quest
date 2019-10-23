package com.dailyquest.feature.splash

interface SplashViewContract {
    fun navigateToSelectRole()
    fun navigateToHomeParent()
    fun navigateToHomeChild()
    fun showFailedMessage(message: String)
}

interface SplashPresenterContract{
    fun auth()
}