package com.dailyquest.feature.parent.mainParent.presenter

import com.dailyquest.feature.parent.mainParent.MainParentPresenterContract
import com.dailyquest.feature.parent.mainParent.MainParentViewContract
import com.dailyquest.utils.SessionManager
import com.google.firebase.auth.FirebaseAuth

class MainParentPresenter(private val view: MainParentViewContract, private val pref: SessionManager) : MainParentPresenterContract {
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun logout() {
        firebaseAuth.signOut()
        pref.clear()
        view.navigateToRole()
    }
}