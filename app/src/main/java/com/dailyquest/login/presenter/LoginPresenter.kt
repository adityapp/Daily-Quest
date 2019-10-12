package com.dailyquest.login.presenter

import com.dailyquest.isEmailValid
import com.dailyquest.isPasswordValid
import com.dailyquest.login.LoginPresenterContract
import com.dailyquest.login.LoginViewContract

class LoginPresenter(private val view: LoginViewContract) : LoginPresenterContract {
    override fun login(email: String, password: String) {
        view.showLoadingDialog()
        if (email.isEmailValid()) {
            if (password.isPasswordValid()) {

            } else {
                view.dismissLoadingDialog()
            }
        } else {
            view.dismissLoadingDialog()
        }
    }
}