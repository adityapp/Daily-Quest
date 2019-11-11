package com.dailyquest.feature.parent.children.presenter

import com.dailyquest.feature.parent.children.ChildrenPresenterContract
import com.dailyquest.feature.parent.children.ChildrenViewContract
import com.google.firebase.database.FirebaseDatabase

class ChildrenPresenter(private val view: ChildrenViewContract) : ChildrenPresenterContract{
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    override fun addNewChildren() {

    }

    override fun getChildrenList() {
        view.showChildrenList(listOf())
    }
}