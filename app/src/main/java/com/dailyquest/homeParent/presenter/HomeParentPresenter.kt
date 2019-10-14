package com.dailyquest.homeParent.presenter

import com.dailyquest.homeParent.HomeParentPresenterContract
import com.dailyquest.homeParent.HomeParentViewContract
import com.google.firebase.database.FirebaseDatabase

class HomeParentPresenter(private val view: HomeParentViewContract) : HomeParentPresenterContract {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    override fun addNewQuest() {

    }

    override fun getQuestList() {
        view.showQuestList(listOf())
    }
}