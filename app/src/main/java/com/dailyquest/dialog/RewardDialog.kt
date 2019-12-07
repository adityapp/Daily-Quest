package com.dailyquest.dialog

import android.content.Context
import com.bumptech.glide.Glide
import com.dailyquest.R
import com.dailyquest.base.BaseDialog
import com.dailyquest.utils.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.dialog_reward.*

class RewardDialog(context: Context, private val parentUid: String) : BaseDialog(context) {

    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun layoutId() = R.layout.dialog_reward

    override fun setupView() {
        super.setupView()

        beginWith { getReward() }
            .then { setupOnClick() }
    }

    private fun getReward() {
        firebaseAuth?.uid?.let { uid ->
            firebaseDatabase.getReference(Constants.DATABASE_USER).child(parentUid)
                .child(Constants.ANAK.toLowerCase()).child(uid).child(Constants.DATABASE_REWARD)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        showToast("Maaf, telah terjadi kesalahan")
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        p0.getValue(Int::class.java)?.let {
                            tv_reward.text = "Rp. $it"
                            if (it > 0) {
                                ll_barcode.show()
                                Glide.with(context)
                                    .load(Constants.BARCODE_BASE_URL + uid + "-" + it)
                                    .into(iv_barcode)
                            } else {
                                ll_barcode.remove()
                            }
                        }
                    }
                })
        }
    }

    private fun setupOnClick() {
        b_ok.setOnClickListener {
            dismiss()
        }
    }
}