package com.dailyquest.dialog

import android.content.Context
import com.dailyquest.R
import com.dailyquest.base.BaseDialog
import com.dailyquest.utils.beginWith
import com.dailyquest.utils.isPasswordValid
import com.dailyquest.utils.value
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.dialog_change_password.*

class ChangePasswordDialog(context: Context) : BaseDialog(context) {
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun layoutId() = R.layout.dialog_change_password

    override fun setupView() {
        super.setupView()

        beginWith { setupOnClick() }
    }

    private fun setupOnClick() {
        b_change.setOnClickListener { changePassword() }
        iv_close.setOnClickListener { dismiss() }
    }

    private fun changePassword() {
        when {
            !et_password.value().isPasswordValid() -> showToast("Kata sandi harus lebih dari 6 karakter!")
            !et_new_password.value().isPasswordValid() -> showToast("Kata sandi baru harus lebih dari 6 karakter!")
            et_confirm_password.value() != et_new_password.value() -> showToast("Kata sandi tidak sama!")
            else -> updatePassword()
        }
    }

    private fun updatePassword() {
        firebaseAuth.currentUser?.let { user ->
            user.email?.let { email ->
                changeStateButton(true)

                user.reauthenticate(EmailAuthProvider.getCredential(email, et_password.value()))
                    .addOnSuccessListener {
                        user.updatePassword(et_new_password.value())
                            .addOnSuccessListener {
                                showToast("Kata sandi berhasil diubah")
                                dismiss()
                            }
                            .addOnFailureListener {
                                showToast("Kata sandi gagal diubah!")
                                changeStateButton(false)
                            }
                    }
                    .addOnFailureListener {
                        showToast("Kata sandi salah!")
                        changeStateButton(false)
                    }
            }
        }
    }

    private fun changeStateButton(isLoading: Boolean) {
        if (isLoading) {
            b_change.isClickable = false
            b_change.text = "Sedang Diproses"
            b_change.setBackgroundResource(R.drawable.rounded_background_gray)
        } else {
            b_change.isClickable = true
            b_change.text = "Simpan"
            b_change.setBackgroundResource(R.drawable.button_background)
        }
    }
}