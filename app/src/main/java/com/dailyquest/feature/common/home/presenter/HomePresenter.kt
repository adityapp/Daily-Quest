package com.dailyquest.feature.common.home.presenter

import android.util.Log
import com.dailyquest.feature.common.home.HomePresenterContract
import com.dailyquest.feature.common.home.HomeViewContract
import com.dailyquest.model.ChildrenModel
import com.dailyquest.model.QuestModel
import com.dailyquest.utils.Constants
import com.dailyquest.utils.SessionManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomePresenter(private val view: HomeViewContract, private val pref: SessionManager) :
    HomePresenterContract {
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun getAllQuestList() {
        pref.getParentUid()?.let { uid ->
            view.showLoadingDialog()

            firebaseDatabase.getReference(Constants.DATABASE_USER).child(uid)
                .child(Constants.ANAK.toLowerCase())
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        view.showFailedMessage(p0.message)
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        var allQuest = arrayListOf<QuestModel>()
                        for (children in p0.children) {
                            var child = children.getValue(ChildrenModel::class.java)
                            child?.let {
                                for ((key, value) in it.quest) {
                                    value.id = key
                                    value.fullName = child.fullName
                                    allQuest.add(value)
                                }
                            }
                        }
                        view.showQuestList(allQuest.sortedBy { it.createdAt })
                    }
                })
        }
    }

    override fun getQuestList() {
        firebaseAuth.uid?.let { uid ->
            pref.getParentUid()?.let { parentUid ->
                view.showLoadingDialog()

                firebaseDatabase.getReference(Constants.DATABASE_USER).child(parentUid)
                    .child(Constants.ANAK.toLowerCase()).child(uid).child(Constants.DATABASE_QUEST)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            view.showFailedMessage(p0.message)
                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            var questList = arrayListOf<QuestModel>()
                            for (quest in p0.children) {
                                quest.getValue(QuestModel::class.java)?.let {
                                    it.id = quest.key
                                    questList.add(it)
                                }
                            }
                            view.showQuestList(questList)
                        }
                    })
            }
        }
    }
}