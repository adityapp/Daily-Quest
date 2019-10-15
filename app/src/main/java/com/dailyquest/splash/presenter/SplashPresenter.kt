package com.dailyquest.splash.presenter

import com.dailyquest.utils.Constants
import com.dailyquest.splash.SplashPresenterContract
import com.dailyquest.splash.SplashViewContract
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SplashPresenter(private val view: SplashViewContract) : SplashPresenterContract {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    override fun auth() {
        firebaseAuth.currentUser?.let { user ->
            firebaseDatabase.getReference(Constants.DATABASE_USER).child(user.uid).child(
                Constants.ROLE)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(databaseError: DatabaseError) {
                        view.showFailedMessage(databaseError.message)
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        when (dataSnapshot.value) {
                            Constants.ANAK -> view.navigateToHomeChild()
                            Constants.ORANG_TUA -> view.navigateToHomeParent()
                        }
                    }
                })
        } ?: run {
            view.navigateToSelectRole()
        }
    }
}