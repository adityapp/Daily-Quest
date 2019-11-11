package com.dailyquest.feature.parent.home.presenter

import com.dailyquest.feature.parent.home.HomeParentPresenterContract
import com.dailyquest.feature.parent.home.HomeParentViewContract
import com.google.firebase.database.FirebaseDatabase

class HomeParentPresenter(private val view: HomeParentViewContract) : HomeParentPresenterContract {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    override fun getAllQuestList() {
        view.showAllQuestList(listOf())
    }
}