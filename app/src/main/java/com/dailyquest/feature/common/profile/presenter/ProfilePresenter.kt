package com.dailyquest.feature.common.profile.presenter

import com.dailyquest.feature.common.profile.ProfilePresenterContract
import com.dailyquest.feature.common.profile.ProfileViewContract
import com.dailyquest.model.UserModel
import com.dailyquest.utils.Constants
import com.dailyquest.utils.SessionManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfilePresenter(private val view: ProfileViewContract, private val pref: SessionManager) :
    ProfilePresenterContract {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    override fun getUserProfile() {
        pref.getRole()?.let { role ->
            when (role) {
                Constants.ANAK -> getChildrenProfile()
                else -> getParentProfile()
            }
        }
    }

    private fun getChildrenProfile() {
        pref.getParentUid()?.let { parentUid ->
            firebaseAuth.uid?.let { uid ->
                view.showLoadingDialog()

                firebaseDatabase.getReference(Constants.DATABASE_USER).child(parentUid)
                    .child(Constants.ANAK.toLowerCase()).child(uid)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            view.showFailedMessage(p0.message)
                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            p0.getValue(UserModel::class.java)?.let { user ->
                                view.showProfile(user)
                            }
                        }
                    })
            }
        }
    }

    private fun getParentProfile() {
        firebaseAuth.uid?.let { uid ->
            view.showLoadingDialog()

            firebaseDatabase.getReference(Constants.DATABASE_USER).child(uid)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        view.showFailedMessage(p0.message)
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        p0.getValue(UserModel::class.java)?.let { user ->
                            view.showProfile(user)
                        }
                    }
                })
        }
    }
}