package com.dailyquest.feature.parent.homeParent.presenter

import com.dailyquest.feature.parent.homeParent.HomeParentPresenterContract
import com.dailyquest.feature.parent.homeParent.HomeParentViewContract
import com.google.firebase.database.FirebaseDatabase

class HomeParentPresenter(private val view: HomeParentViewContract) : HomeParentPresenterContract {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    override fun addNewChildren() {

    }

    override fun getChildrenList() {
        view.showChildrenList(listOf())
    }
}