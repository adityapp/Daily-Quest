package com.dailyquest.login

interface LoginViewContract {
    fun showLoadingDialog()
    fun dismissLoadingDialog()
}

interface LoginPresenterContract {
    fun login(email: String, password: String)
}