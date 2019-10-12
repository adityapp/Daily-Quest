package com.dailyquest.splash

interface SplashViewContract {
    fun navigateToSelectRole()
    fun navigateToHomeParent()
    fun navigateToHomeChild()
}

interface SplashPresenterContract{
    fun auth()
}