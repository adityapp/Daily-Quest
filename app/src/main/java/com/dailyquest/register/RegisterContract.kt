package com.dailyquest.register

interface RegisterViewContract {
    fun navigateToHomeParent()
    fun navigateToHomeChild()
    fun showFailedMessage(message: String)
    fun showLoadingDialog()
    fun dismissLoadingDialog()
}

interface RegisterPresenterContract {
    fun register(
        fullName: String,
        email: String,
        password: String,
        confirmationPassword: String,
        role: String
    )
}