package com.dailyquest.mainParent.presenter

import com.dailyquest.mainParent.MainParentPresenterContract
import com.dailyquest.mainParent.MainParentViewContract
import com.google.firebase.auth.FirebaseAuth

class MainParentPresenter(private val view: MainParentViewContract) : MainParentPresenterContract {
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun logout() {
        firebaseAuth.signOut()
        view.navigateToRole()
    }
}