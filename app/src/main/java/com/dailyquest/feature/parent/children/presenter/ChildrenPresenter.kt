package com.dailyquest.feature.parent.children.presenter

import com.dailyquest.feature.parent.children.ChildrenPresenterContract
import com.dailyquest.feature.parent.children.ChildrenViewContract
import com.dailyquest.model.ChildrenModel
import com.dailyquest.utils.Constants
import com.dailyquest.utils.SessionManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChildrenPresenter(private val view: ChildrenViewContract, private val pref: SessionManager) :
    ChildrenPresenterContract {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    override fun getChildrenList() {
        view.showLoadingDialog()

        pref.getParentUid()?.let { uid ->
            firebaseDatabase.getReference(Constants.DATABASE_USER).child(uid)
                .child(Constants.ANAK.toLowerCase())
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        view.showFailedMessage("Maaf, telah terjadi kesalahan")
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        var childrenList = arrayListOf<ChildrenModel>()
                        for (children in p0.children) {
                            children.getValue(ChildrenModel::class.java)?.let {
                                it.uid = children.key ?: ""
                                childrenList.add(it)
                            }
                        }
                        view.showChildrenList(childrenList.sortedBy { it.fullName.toLowerCase() })
                    }
                })
        }
    }
}