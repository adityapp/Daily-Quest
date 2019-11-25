package com.dailyquest.feature.common.main.presenter

import android.util.Log
import com.dailyquest.feature.common.main.MainPresenterContract
import com.dailyquest.feature.common.main.MainViewContract
import com.dailyquest.utils.Constants
import com.dailyquest.utils.SessionManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainPresenter(private val view: MainViewContract, private val pref: SessionManager) :
    MainPresenterContract {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    override fun logout() {
        firebaseAuth.signOut()
        pref.clear()
        view.navigateToRole()
    }

    override fun getReward() {
        pref.getRole()?.let { role ->
            if (role == Constants.ANAK) {
                pref.getParentUid()?.let { parentUid ->
                    firebaseAuth.uid?.let { uid ->
                        view.showLoadingDialog()

                        firebaseDatabase.getReference(Constants.DATABASE_USER).child(parentUid)
                            .child(Constants.ANAK.toLowerCase()).child(uid)
                            .child(Constants.DATABASE_REWARD)
                            .addValueEventListener(object : ValueEventListener {
                                override fun onCancelled(p0: DatabaseError) {
                                    view.showFailedMessage(p0.message)
                                }

                                override fun onDataChange(p0: DataSnapshot) {
                                    p0.getValue(Int::class.java)?.let {
                                        view.showReward(it)
                                    }
                                }
                            })
                    }
                }
            }
        }
    }
}