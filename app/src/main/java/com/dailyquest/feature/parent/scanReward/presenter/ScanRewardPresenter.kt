package com.dailyquest.feature.parent.scanReward.presenter

import com.dailyquest.feature.parent.scanReward.ScanRewardPresenterContract
import com.dailyquest.feature.parent.scanReward.ScanRewardViewContract
import com.dailyquest.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ScanRewardPresenter(private val view: ScanRewardViewContract) : ScanRewardPresenterContract {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    override fun updateReward(value: String) {
        view.showLoadingDialog()
        value.split("-").also { valueSplit ->
            if (valueSplit.size == 2) {
                firebaseAuth?.uid?.let { uid ->
                    firebaseDatabase.getReference(Constants.DATABASE_USER).child(uid)
                        .child(Constants.ANAK.toLowerCase()).child(valueSplit[0])
                        .updateChildren(mapOf(Constants.DATABASE_REWARD to 0))
                        .addOnSuccessListener {
                            view.navigateToMain(valueSplit[1])
                        }
                        .addOnFailureListener {
                            view.showFailedMessage("Barkode tidak valid!")
                        }
                }
            } else {
                view.showFailedMessage("Barkode tidak valid!")
            }
        }

    }
}