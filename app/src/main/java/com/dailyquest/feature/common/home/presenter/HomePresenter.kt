package com.dailyquest.feature.common.home.presenter

import com.dailyquest.feature.common.home.HomeParentPresenterContract
import com.dailyquest.feature.common.home.HomeParentViewContract
import com.google.firebase.database.FirebaseDatabase

class HomePresenter(private val view: HomeParentViewContract) : HomeParentPresenterContract {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    override fun getAllQuestList() {
        view.showAllQuestList(listOf())
    }
}