package com.dailyquest.feature.common.detailQuest.presenter

import com.dailyquest.feature.common.detailQuest.DetailQuestPresenterContract
import com.dailyquest.feature.common.detailQuest.DetailQuestViewContract
import com.dailyquest.model.QuestModel
import com.dailyquest.utils.Constants
import com.dailyquest.utils.SessionManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

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
        view.showLoadingDialog()

        firebaseAuth.uid?.let { uid ->
            pref.getParentUid()?.let { parentUid ->
                quest.id?.let { id ->
                    firebaseDatabase.getReference(Constants.DATABASE_USER).child(parentUid)
                        .child(Constants.ANAK.toLowerCase()).child(uid)
                        .child(Constants.DATABASE_QUEST).child(id)
                        .updateChildren(mapOf("status" to updateStatus(quest.status)))
                        .addOnSuccessListener {
                            view.dismissLoadingDialog()
                        }
                        .addOnFailureListener {
                            view.showFailedMessage(it.message.toString())
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