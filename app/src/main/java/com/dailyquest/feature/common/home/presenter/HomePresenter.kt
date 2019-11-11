package com.dailyquest.feature.common.home.presenter

import com.dailyquest.feature.common.home.HomePresenterContract
import com.dailyquest.feature.common.home.HomeViewContract
import com.google.firebase.database.FirebaseDatabase

class HomePresenter(private val view: HomeViewContract) : HomePresenterContract {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    override fun getAllQuestList() {
        view.showAllQuestList(listOf())
    }
}