package com.dailyquest.feature.scanUid.presenter

import com.dailyquest.feature.scanUid.ScanUidPresenterContract
import com.dailyquest.feature.scanUid.ScanUidViewContract
import com.dailyquest.utils.Constants
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError   
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ScanUidPresenter(private val view: ScanUidViewContract) : ScanUidPresenterContract {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    override fun validateParentUid(parentUid: String) {
        firebaseDatabase.getReference(Constants.DATABASE_USER).child(parentUid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    view.showFailedMessage("Barcode tidak valid!")
                }

                override fun onDataChange(p0: DataSnapshot) {
                    view.navigateToLogin(parentUid)
                }
            })
    }
}