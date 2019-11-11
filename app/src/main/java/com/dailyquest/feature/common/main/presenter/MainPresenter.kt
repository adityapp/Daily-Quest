package com.dailyquest.feature.common.main.presenter

import com.dailyquest.feature.common.main.MainPresenterContract
import com.dailyquest.feature.common.main.MainViewContract
import com.dailyquest.utils.SessionManager
import com.google.firebase.auth.FirebaseAuth

class MainPresenter(private val view: MainViewContract, private val pref: SessionManager) : MainPresenterContract {
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun logout() {
        firebaseAuth.signOut()
        pref.clear()
        view.navigateToRole()
    }
}