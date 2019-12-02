package com.dailyquest.feature.common.register.presenter

import com.dailyquest.feature.common.register.RegisterPresenterContract
import com.dailyquest.feature.common.register.RegisterViewContract
import com.dailyquest.model.UserModel
import com.dailyquest.utils.Constants
import com.dailyquest.utils.isEmailValid
import com.dailyquest.utils.isPasswordValid
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterPresenter(private val view: RegisterViewContract) :
    RegisterPresenterContract {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    override fun register(
        fullName: String,
        email: String,
        password: String,
        confirmationPassword: String,
        role: String,
        parentUid: String
    ) {
        view.showLoadingDialog()
        when {
            fullName.isBlank() -> view.showFailedMessage("Nama lengkap tidak boleh kosong!")
            !email.isEmailValid() -> view.showFailedMessage("E-mail tidak valid!")
            !password.isPasswordValid() -> view.showFailedMessage("Kata sandi harus lebih dari 6 karakter!")
            confirmationPassword != password -> view.showFailedMessage("Kata sandi tidak sama!")
            role == Constants.ANAK && parentUid.isBlank() -> view.showFailedMessage("Silahkan Lakukan Scan Orang Tua!")
            else -> createNewAccount(fullName, email, password, role, parentUid)
        }
    }

    private fun createNewAccount(
        fullName: String,
        email: String,
        password: String,
        role: String,
        parentUid: String
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { auth ->
                auth?.user?.let { user ->
                    when (role) {
                        Constants.ANAK -> registerAsChildren(fullName, role, user.uid, parentUid)
                        Constants.ORANG_TUA -> registerAsParent(fullName, role, user.uid)
                    }
                }
            }
            .addOnFailureListener { e ->
                view.showFailedMessage(e.message.toString())
            }
    }

    private fun registerAsChildren(
        fullName: String,
        role: String,
        userUid: String,
        parentUid: String
    ) {
        firebaseDatabase.getReference(Constants.DATABASE_USER)
            .child(parentUid).child(Constants.ANAK.toLowerCase()).child(userUid)
            .setValue(UserModel(fullName = fullName, role = role, reward = 0))
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                view.showFailedMessage(e.message.toString())
            }
    }

    private fun registerAsParent(
        fullName: String,
        role: String,
        userUid: String
    ) {
        firebaseDatabase.getReference(Constants.DATABASE_USER).child(userUid)
            .setValue(UserModel(fullName = fullName, role = role)).addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                view.showFailedMessage(e.message.toString())
            }
    }

    private fun onSuccess() {
        firebaseAuth.signOut()
        view.dismissLoadingDialog()
        view.navigateToRole()
    }
}