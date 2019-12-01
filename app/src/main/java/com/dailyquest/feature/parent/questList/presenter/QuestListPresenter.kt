package com.dailyquest.feature.parent.questList.presenter

import com.dailyquest.feature.parent.questList.QuestListPresenterContract
import com.dailyquest.feature.parent.questList.QuestListViewContract
import com.dailyquest.model.ChildrenModel
import com.dailyquest.model.QuestModel
import com.dailyquest.utils.Constants
import com.dailyquest.utils.SessionManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

class QuestListPresenter(
    private val view: QuestListViewContract,
    private val pref: SessionManager,
    private val children: ChildrenModel
) :
    QuestListPresenterContract {
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val firebaseMessaging = FirebaseMessaging.getInstance()

    override fun addNewQuest() {
        pref.getParentUid()?.let { uid ->
            children.uid?.let { childrenUid ->
                val uuid = UUID.randomUUID().toString()

                view.openNewQuestDialog {
                    view.showLoadingDialog()

                    firebaseDatabase.getReference(Constants.DATABASE_USER).child(uid)
                        .child(Constants.ANAK.toLowerCase()).child(childrenUid)
                        .child(Constants.DATABASE_QUEST).child(uuid).setValue(it)
                        .addOnSuccessListener {
                            sendNotification()
                            view.dismissLoadingDialog()
                        }
                        .addOnFailureListener { e ->
                            view.showFailedMessage(e.message.toString())
                        }
                }
            }
        }
    }

    override fun getQuestList() {
        pref.getParentUid()?.let { uid ->
            children.uid?.let { childrenUid ->
                view.showLoadingDialog()

                firebaseDatabase.getReference(Constants.DATABASE_USER).child(uid)
                    .child(Constants.ANAK.toLowerCase()).child(childrenUid)
                    .child(Constants.DATABASE_QUEST)
                    .addValueEventListener(object : ValueEventListener {
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
                            view.showQuestList(questList.sortedByDescending { it.createdAt })
                        }
                    })
            }
        }
    }

    private fun sendNotification() {
        children.token?.let { token ->
            firebaseMessaging.send(
                RemoteMessage.Builder(token)
                    .setMessageId(AtomicInteger().get().toString())
                    .addData("notification.title", "hello")
                    .addData("notification.body", "hello")
                    .build()
            )
        }
    }
}