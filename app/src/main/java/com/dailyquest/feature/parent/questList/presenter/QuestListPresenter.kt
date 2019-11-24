package com.dailyquest.feature.parent.questList.presenter

import com.dailyquest.feature.parent.questList.QuestListPresenterContract
import com.dailyquest.feature.parent.questList.QuestListViewContract
import com.dailyquest.model.QuestModel
import com.dailyquest.utils.Constants
import com.dailyquest.utils.SessionManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

class QuestListPresenter(
    private val view: QuestListViewContract,
    private val pref: SessionManager,
    private val childrenUid: String
) :
    QuestListPresenterContract {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    override fun addNewQuest() {
        pref.getParentUid()?.let { uid ->
            val uuid = UUID.randomUUID().toString()

            view.openNewQuestDialog {
                view.showLoadingDialog()

                firebaseDatabase.getReference(Constants.DATABASE_USER).child(uid)
                    .child(Constants.ANAK.toLowerCase()).child(childrenUid)
                    .child(Constants.DATABASE_QUEST).child(uuid).setValue(it)
                    .addOnSuccessListener {
                        view.dismissLoadingDialog()
                    }
                    .addOnFailureListener { e ->
                        view.showFailedMessage(e.message.toString())
                    }
            }
        }
    }

    override fun getQuestList() {
        pref.getParentUid()?.let { uid ->
            view.showLoadingDialog()

            firebaseDatabase.getReference(Constants.DATABASE_USER).child(uid)
                .child(Constants.ANAK.toLowerCase()).child(childrenUid)
                .child(Constants.DATABASE_QUEST).addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        view.showFailedMessage(p0.message)
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val questList = arrayListOf<QuestModel>()
                        for (quest in p0.children) {
                            quest.getValue(QuestModel::class.java)?.let {
                                it.id = quest.key
                                it.childrenUid = childrenUid
                                questList.add(it)
                            }
                        }
                        view.showQuestList(questList)
                    }
                })
        }
    }
}