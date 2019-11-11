package com.dailyquest.feature.common.main.presenter

import com.dailyquest.feature.common.main.MainParentPresenterContract
import com.dailyquest.feature.common.main.MainParentViewContract
import com.dailyquest.utils.SessionManager
import com.google.firebase.auth.FirebaseAuth

class MainPresenter(private val view: MainParentViewContract, private val pref: SessionManager) : MainParentPresenterContract {
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun logout() {
        firebaseAuth.signOut()
        pref.clear()
        view.navigateToRole()
    }
}