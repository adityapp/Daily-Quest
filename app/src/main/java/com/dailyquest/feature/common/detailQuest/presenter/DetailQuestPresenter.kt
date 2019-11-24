package com.dailyquest.feature.common.detailQuest.presenter

import android.net.Uri
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
import com.google.firebase.storage.FirebaseStorage

class DetailQuestPresenter(
    private val view: DetailQuestViewContract,
    private val pref: SessionManager
) :
    DetailQuestPresenterContract {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val firebaseStorage = FirebaseStorage.getInstance()

    override fun updateQuest(quest: QuestModel, selectImage: Uri?) {
        when (quest.status) {
            Constants.STATUS_OPEN -> setToOngoingStatus(quest)
            else -> setToFinishStatus(quest, selectImage)
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

    private fun setToOngoingStatus(quest: QuestModel) {
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

    private fun setToFinishStatus(quest: QuestModel, imageUri: Uri?) {
        firebaseAuth.uid?.let { uid ->
            pref.getParentUid()?.let { parentUid ->
                quest.id?.let { id ->
                    view.showLoadingDialog()

                    imageUri?.let { uri ->
                        firebaseStorage.getReference(Constants.DATABASE_QUEST_IMAGE).child(uid)
                            .child(id)
                            .putFile(uri)
                            .addOnSuccessListener {
                                it.metadata?.reference?.downloadUrl?.addOnSuccessListener { uriResult ->
                                    quest.image = uriResult.toString()
                                    quest.status = updateStatus(quest.status)
                                    firebaseDatabase.getReference(Constants.DATABASE_USER)
                                        .child(parentUid)
                                        .child(Constants.ANAK.toLowerCase()).child(uid)
                                        .child(Constants.DATABASE_QUEST).child(id)
                                        .setValue(quest)
                                        .addOnSuccessListener {
                                            getQuest(id)
                                        }
                                        .addOnFailureListener { e ->
                                            view.showFailedMessage(e.message.toString())
                                        }
                                }?.addOnFailureListener { e ->
                                    view.showFailedMessage(e.message.toString())
                                }
                            }
                            .addOnFailureListener { e ->
                                view.showFailedMessage(e.message.toString())
                            }
                    }
                }
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