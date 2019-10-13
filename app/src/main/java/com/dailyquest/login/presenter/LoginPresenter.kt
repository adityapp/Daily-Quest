package com.dailyquest.login.presenter

import com.dailyquest.Constants
import com.dailyquest.isEmailValid
import com.dailyquest.isPasswordValid
import com.dailyquest.login.LoginPresenterContract
import com.dailyquest.login.LoginViewContract
import com.google.firebase.auth.FirebaseAuth

class LoginPresenter(private val view: LoginViewContract) : LoginPresenterContract {
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun login(email: String, password: String, role: String) {
        view.showLoadingDialog()
        when {
            !email.isEmailValid() -> view.showFailedMessage("E-mail tidak valid!")
            !password.isPasswordValid() -> view.showFailedMessage("Kata sandi yang dimasukan salah!")
            else -> auth(email, password, role)
        }
    }

    private fun auth(email: String, password: String, role: String){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            when(role){
                Constants.ANAK -> view.navigateToHomeChild()
                Constants.ORANG_TUA -> view.navigateToHomeParent()
            }
        }.addOnFailureListener {e ->
            view.showFailedMessage(e.message.toString())
        }
    }
}