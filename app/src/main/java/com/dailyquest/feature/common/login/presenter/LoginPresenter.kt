package com.dailyquest.feature.common.login.presenter

import com.dailyquest.feature.common.login.LoginPresenterContract
import com.dailyquest.feature.common.login.LoginViewContract
import com.dailyquest.utils.Constants
import com.dailyquest.utils.SessionManager
import com.dailyquest.utils.isEmailValid
import com.dailyquest.utils.isPasswordValid
import com.google.firebase.auth.FirebaseAuth

class LoginPresenter(private val view: LoginViewContract, private val pref: SessionManager) :
    LoginPresenterContract {
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun login(email: String, password: String, role: String, parentUid: String?) {
        view.showLoadingDialog()
        when {
            !email.isEmailValid() -> view.showFailedMessage("E-mail tidak valid!")
            !password.isPasswordValid() -> view.showFailedMessage("Kata sandi yang dimasukan salah!")
            else -> auth(email, password, role, "")
        }
    }

    private fun auth(email: String, password: String, role: String, parentUid: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            pref.setSession(parentUid, role)
            view.navigateToHome()
        }.addOnFailureListener { e ->
            view.showFailedMessage(e.message.toString())
        }
    }
}