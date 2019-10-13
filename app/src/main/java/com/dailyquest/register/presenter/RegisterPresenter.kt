package com.dailyquest.register.presenter

import com.dailyquest.isEmailValid
import com.dailyquest.isPasswordValid
import com.dailyquest.register.RegisterPresenterContract
import com.dailyquest.register.RegisterViewContract
import com.google.firebase.auth.FirebaseAuth

class RegisterPresenter(private val view: RegisterViewContract) : RegisterPresenterContract {
    private val firebaseAuth = FirebaseAuth.getInstance()

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
            else -> {
                createNewAccount(fullName, email, password, role)
            }
        }
    }

    private fun createNewAccount(
        fullName: String,
        email: String,
        password: String,
        role: String
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {

            }
        }
    }
}