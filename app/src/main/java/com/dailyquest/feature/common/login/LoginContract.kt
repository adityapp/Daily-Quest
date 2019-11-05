package com.dailyquest.feature.common.login

import com.dailyquest.base.BasePresenter

interface LoginViewContract {
    fun showLoadingDialog()
    fun dismissLoadingDialog()
    fun navigateToHomeChild()
    fun navigateToHomeParent()
    fun showFailedMessage(message: String)
}

interface LoginPresenterContract: BasePresenter {
    fun login(email: String, password: String, role: String, parentUid: String?)
}