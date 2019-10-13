package com.dailyquest.login.presenter

import com.dailyquest.isEmailValid
import com.dailyquest.isPasswordValid
import com.dailyquest.login.LoginPresenterContract
import com.dailyquest.login.LoginViewContract
import com.google.firebase.auth.FirebaseAuth

class LoginPresenter(private val view: LoginViewContract) : LoginPresenterContract {
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun login(email: String, password: String) {
        view.showLoadingDialog()
        if (email.isEmailValid()) {
            if (password.isPasswordValid()) {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        view.navigateToHomeParent()
                    }
                }
            } else {
                view.dismissLoadingDialog()
            }
        } else {
            view.dismissLoadingDialog()
        }
    }
}