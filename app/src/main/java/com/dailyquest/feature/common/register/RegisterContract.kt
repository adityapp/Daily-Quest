package com.dailyquest.feature.common.register

import com.dailyquest.base.BasePresenter

interface RegisterViewContract {
    fun navigateToRole()
    fun showFailedMessage(message: String)
    fun showLoadingDialog()
    fun dismissLoadingDialog()
}

interface RegisterPresenterContract: BasePresenter {
    fun register(
        fullName: String,
        email: String,
        password: String,
        confirmationPassword: String,
        role: String,
        parentUid: String
    )
}