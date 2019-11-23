package com.dailyquest.feature.common.detailQuest.presenter

import android.util.Log
import com.dailyquest.feature.common.detailQuest.DetailQuestPresenterContract
import com.dailyquest.feature.common.detailQuest.DetailQuestViewContract
import com.dailyquest.model.QuestModel
import com.dailyquest.utils.Constants
import com.dailyquest.utils.SessionManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DetailQuestPresenter(
    private val view: DetailQuestViewContract,
    private val pref: SessionManager
) :
    DetailQuestPresenterContract {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    override fun uploadImageToDatabse() {

    }

    override fun updateQuest(quest: QuestModel) {
        firebaseAuth.uid?.let { uid ->
            pref.getParentUid()?.let { parentUid ->
                quest.id?.let { id ->
                    view.showLoadingDialog()

                    firebaseDatabase.getReference(Constants.DATABASE_USER).child(parentUid)
                        .child(Constants.ANAK.toLowerCase()).child(uid)
                        .child(Constants.DATABASE_QUEST).child(id)
                        .updateChildren(mapOf("status" to updateStatus(quest.status)))
                        .addOnSuccessListener {
                            getQuest(id)
                        }
                        .addOnFailureListener {
                            view.showFailedMessage(it.message.toString())
                        }
                }
            }
        }
    }

    override fun getQuest(id: String) {
        firebaseAuth.uid?.let { uid ->
            pref.getParentUid()?.let { parentUid ->
                view.showLoadingDialog()

                firebaseDatabase.getReference(Constants.DATABASE_USER).child(parentUid)
                    .child(Constants.ANAK.toLowerCase()).child(uid)
                    .child(Constants.DATABASE_QUEST).child(id)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            view.showFailedMessage(p0.message)
                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            val quest = p0.getValue(QuestModel::class.java)
                            quest?.let {
                                quest.id = p0.key
                                view.setupContent(it)
                            }
                        }
                    })
            }
        }
    }

    private fun updateStatus(status: String): String {
        return when (status) {
            Constants.STATUS_OPEN -> Constants.STATUS_ONGOING
            else -> Constants.STATUS_FINISH
        }
    }
}