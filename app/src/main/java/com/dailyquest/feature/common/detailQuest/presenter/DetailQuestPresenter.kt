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
import com.trenzlr.firebasenotificationhelper.FirebaseNotificationHelper

class DetailQuestPresenter(
    private val view: DetailQuestViewContract,
    private val pref: SessionManager
) :
    DetailQuestPresenterContract {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val firebaseStorage = FirebaseStorage.getInstance()
    private var token: String? = null

    override fun getToken(id: String, childrenUid: String) {
        pref.getRole()?.let { role ->
            when (role) {
                Constants.ANAK -> getParentToken(id, childrenUid)
                else -> getChildrenToken(id, childrenUid)
            }
        }
    }

    override fun updateQuest(quest: QuestModel, selectImage: Uri?) {
        pref.getRole()?.let { role ->
            when {
                role == Constants.ANAK && Constants.STATUS_OPEN == quest.status -> {
                    setToOngoingStatus(quest)
                }

                role == Constants.ANAK && Constants.STATUS_ONGOING == quest.status -> {
                    setToFinishStatus(quest, selectImage)
                }

                role == Constants.ORANG_TUA && Constants.STATUS_FINISH == quest.status -> {
                    setToCloseStatus(quest)
                }
            }
        }
    }

    private fun getChildrenToken(id: String, childrenUid: String) {
        pref.getParentUid()?.let { parentUid ->
            firebaseDatabase.getReference(Constants.DATABASE_USER).child(parentUid)
                .child(Constants.ANAK.toLowerCase()).child(childrenUid)
                .child(Constants.DATABASE_TOKEN)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        view.showFailedMessage("Maaf, telah terjadi kesalahan")
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        p0.getValue(String::class.java)?.let {
                            token = it
                            getQuest(id, childrenUid)
                        }
                    }
                })
        }
    }

    private fun getParentToken(id: String, childrenUid: String) {
        pref.getParentUid()?.let { parentUid ->
            firebaseDatabase.getReference(Constants.DATABASE_USER).child(parentUid)
                .child(Constants.DATABASE_TOKEN)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        view.showFailedMessage("Maaf, telah terjadi kesalahan")
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        p0.getValue(String::class.java)?.let {
                            token = it
                            getQuest(id, childrenUid)
                        }
                    }
                })
        }
    }

    private fun getQuest(id: String, childrenUid: String) {
        firebaseAuth.uid?.let { uid ->
            pref.getParentUid()?.let { parentUid ->
                view.showLoadingDialog()

                firebaseDatabase.getReference(Constants.DATABASE_USER).child(parentUid)
                    .child(Constants.ANAK.toLowerCase())
                    .child(if (pref.getRole() == Constants.ANAK) uid else childrenUid)
                    .child(Constants.DATABASE_QUEST).child(id)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            view.showFailedMessage("Maaf, telah terjadi kesalahan")
                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            val quest = p0.getValue(QuestModel::class.java)
                            quest?.let {
                                it.id = p0.key
                                it.childrenUid = childrenUid
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
                view.showLoadingDialog()

                updateQuestDatabase(parentUid, uid, quest, null)
            }
        }
    }

    private fun setToFinishStatus(quest: QuestModel, imageUri: Uri?) {
        firebaseAuth.uid?.let { uid ->
            pref.getParentUid()?.let { parentUid ->
                view.showLoadingDialog()

                imageUri?.let { uri ->
                    firebaseStorage.getReference(Constants.DATABASE_QUEST_IMAGE).child(uid)
                        .child(quest.id.toString())
                        .putFile(uri)
                        .addOnSuccessListener {
                            it.metadata?.reference?.downloadUrl?.addOnSuccessListener { uriResult ->
                                updateQuestDatabase(parentUid, uid, quest, uriResult)
                            }?.addOnFailureListener {
                                view.showFailedMessage("Maaf, telah terjadi kesalahan")
                            }
                        }
                        .addOnFailureListener {
                            view.showFailedMessage("Maaf,  telah terjadi kesalahan")
                        }
                }
            }
        }
    }

    private fun setToCloseStatus(quest: QuestModel) {
        firebaseAuth.uid?.let { uid ->
            view.showLoadingDialog()

            firebaseDatabase.getReference(Constants.DATABASE_USER).child(uid)
                .child(Constants.ANAK.toLowerCase())
                .child(quest.childrenUid.toString())
                .child(Constants.DATABASE_REWARD)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        view.showFailedMessage("Maaf, telah terjadi kesalahan")
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        p0.getValue(Int::class.java)?.let { currentReward ->
                            firebaseDatabase.getReference(Constants.DATABASE_USER).child(uid)
                                .child(Constants.ANAK.toLowerCase())
                                .child(quest.childrenUid.toString())
                                .updateChildren(mapOf(Constants.DATABASE_REWARD to currentReward + quest.reward))
                                .addOnSuccessListener {
                                    updateQuestDatabase(
                                        uid,
                                        quest.childrenUid.toString(),
                                        quest,
                                        Uri.parse(quest.image)
                                    )
                                }
                                .addOnFailureListener {
                                    view.showFailedMessage("Maaf, telah terjadi kesalahan")
                                }
                        }
                    }
                })
        }
    }

    private fun updateQuestDatabase(
        parentUid: String,
        userUid: String,
        quest: QuestModel,
        imageUri: Uri?
    ) {
        firebaseDatabase.getReference(Constants.DATABASE_USER).child(parentUid)
            .child(Constants.ANAK.toLowerCase()).child(userUid)
            .child(Constants.DATABASE_QUEST).child(quest.id.toString())
            .updateChildren(
                mapOf(
                    "status" to updateStatus(quest.status),
                    "image" to imageUri?.toString()
                )
            )
            .addOnSuccessListener {
                sendNotification(updateStatus(quest.status), quest.title)
                getQuest(quest.id.toString(), quest.childrenUid.toString())
            }
            .addOnFailureListener {
                view.showFailedMessage("Maaf, telah terjadi kesalahan")
            }
    }

    private fun updateStatus(status: String): String {
        return when (status) {
            Constants.STATUS_OPEN -> Constants.STATUS_ONGOING
            Constants.STATUS_ONGOING -> Constants.STATUS_FINISH
            else -> Constants.STATUS_CLOSE
        }
    }

    private fun sendNotification(status: String, questTitle: String) {
        pref.getRole()?.let { role ->
            var title = ""
            var message = ""

            when {
                Constants.ANAK == role && Constants.STATUS_ONGOING == status -> {
                    title = "Tugas Diambil"
                    message = "Tugas $questTitle sedang dijalankan"
                }

                Constants.ANAK == role && Constants.STATUS_FINISH == status -> {
                    title = "Tugas Terselesaikan"
                    message = "Tugas $questTitle telah diselesaikan"
                }

                Constants.ORANG_TUA == role && Constants.STATUS_CLOSE == status -> {
                    title = "Selamat"
                    message = "Tugas yang kamu selesaikan berhasil, Mission Success!"
                }
            }

            token?.let {
                FirebaseNotificationHelper.initialize(Constants.FIREBASE_API_KEY)
                    .defaultJson(true, null)
                    .title(title)
                    .message(message)
                    .receiverFirebaseToken(it)
                    .send()
            }
        }
    }
}