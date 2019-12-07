package com.dailyquest.feature.children.scanUid

import com.dailyquest.base.BasePresenter

interface ScanUidViewContract {
    fun navigateToLogin(parentUid: String)
    fun navigateToRegister(parentUid: String)
    fun showFailedMessage(message: String)
}

interface ScanUidPresenterContract : BasePresenter {
    fun validateParentUid(parentUid: String, sourceActivity: String)
}