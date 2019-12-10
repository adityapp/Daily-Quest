package com.dailyquest.feature.children.scanUid.presenter

import android.util.Log
import com.dailyquest.feature.children.scanUid.ScanUidPresenterContract
import com.dailyquest.feature.children.scanUid.ScanUidViewContract
import com.dailyquest.utils.Constants
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ScanUidPresenter(private val view: ScanUidViewContract) :
    ScanUidPresenterContract {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    override fun validateParentUid(parentUid: String, sourceActivity: String) {

        firebaseDatabase.getReference(Constants.DATABASE_USER)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    view.showFailedMessage("Barcode tidak valid!")
                }

                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.child(parentUid).exists()){
                        when (sourceActivity) {
                            "RoleActivity" -> view.navigateToLogin(parentUid)
                            "RegisterActivity" -> view.navigateToRegister(parentUid)
                        }
                    }else{
                        view.showFailedMessage("Barcode tidak valid!")
                    }
                }
            })
    }
}