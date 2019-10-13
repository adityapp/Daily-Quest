package com.dailyquest.login

interface LoginViewContract {
    fun showLoadingDialog()
    fun dismissLoadingDialog()
    fun navigateToHomeChild()
    fun navigateToHomeParent()
    fun showFailedMessage(message: String)
}

interface LoginPresenterContract {
    fun login(email: String, password: String, role: String)
}