package com.dailyquest.splash.presenter

import com.dailyquest.splash.SplashPresenterContract
import com.dailyquest.splash.SplashViewContract
import com.google.firebase.auth.FirebaseAuth

class SplashPresenter(private val view: SplashViewContract) : SplashPresenterContract{
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun auth() {
        if (firebaseAuth.currentUser != null){
            view.navigateToHomeParent()
        }else{
            view.navigateToSelectRole()
        }
    }
}