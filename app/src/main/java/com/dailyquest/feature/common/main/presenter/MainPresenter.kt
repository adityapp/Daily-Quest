package com.dailyquest.feature.common.main.presenter

import com.dailyquest.feature.common.main.MainPresenterContract
import com.dailyquest.feature.common.main.MainViewContract
import com.dailyquest.utils.Constants
import com.dailyquest.utils.SessionManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId

class MainPresenter(private val view: MainViewContract, private val pref: SessionManager) :
    MainPresenterContract {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    override fun logout() {
        firebaseAuth.signOut()
        pref.clear()
        view.navigateToRole()
    }

    override fun sendTokenToDatabase() {
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
            if (!it.isSuccessful) {
                return@addOnCompleteListener
            }
            it.result?.token?.let { token ->
                pref.getRole()?.let { role ->
                    when (role) {
                        Constants.ANAK -> sendChildrenTokenToDatabase(token)
                        Constants.ORANG_TUA -> sendParentTokenToDatabase(token)
                    }
                }
            }
        }
    }

    private fun sendParentTokenToDatabase(token: String) {
        firebaseAuth.uid?.let { uid ->
            firebaseDatabase.getReference(Constants.DATABASE_USER).child(uid)
                .child(Constants.DATABASE_TOKEN)
                .setValue(token)
        }
    }

    private fun sendChildrenTokenToDatabase(token: String) {
        firebaseAuth.uid?.let { uid ->
            pref.getParentUid()?.let { parentUid ->
                firebaseDatabase.getReference(Constants.DATABASE_USER).child(parentUid)
                    .child(Constants.ANAK.toLowerCase()).child(uid).child(Constants.DATABASE_TOKEN)
                    .setValue(token)
            }
        }
    }
}