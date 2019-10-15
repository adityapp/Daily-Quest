package com.dailyquest.register.presenter

import com.dailyquest.Constants
import com.dailyquest.isEmailValid
import com.dailyquest.isPasswordValid
import com.dailyquest.register.RegisterPresenterContract
import com.dailyquest.register.RegisterViewContract
import com.dailyquest.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterPresenter(private val view: RegisterViewContract) : RegisterPresenterContract {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    override fun register(
        fullName: String,
        email: String,
        password: String,
        confirmationPassword: String,
        role: String
    ) {
        view.showLoadingDialog()
        when {
            fullName.isNullOrBlank() -> view.showFailedMessage("Nama lengkap tidak boleh kosong!")
            !email.isEmailValid() -> view.showFailedMessage("E-mail tidak valid!")
            !password.isPasswordValid() -> view.showFailedMessage("Kata sandi harus lebih dari 6 karakter!")
            !confirmationPassword.isPasswordValid() -> view.showFailedMessage("Kata sandi harus lebih dari 6 karakter!")
            confirmationPassword != password -> view.showFailedMessage("Kata sandi tidak sama!")
            else -> createNewAccount(fullName, email, password, role)
        }
    }

    private fun createNewAccount(
        fullName: String,
        email: String,
        password: String,
        role: String
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener { auth ->
            auth?.user?.let { user ->
                firebaseDatabase.getReference(Constants.DATABASE_USER).child(user.uid)
                    .setValue(UserModel(fullName, role)).addOnSuccessListener {
                        view.dismissLoadingDialog()
                        when (role) {
                            Constants.ANAK -> view.navigateToHomeChild()
                            Constants.ORANG_TUA -> view.navigateToHomeParent()
                        }
                    }.addOnFailureListener { e ->
                        view.showFailedMessage(e.message.toString())
                    }
            }
        }.addOnFailureListener { e ->
            view.showFailedMessage(e.message.toString())
        }
    }
}