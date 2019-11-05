package com.dailyquest.feature.child.scanUid

import com.dailyquest.base.BasePresenter

interface ScanUidViewContract {
    fun navigateToLogin(parentUid: String)
    fun showFailedMessage(message: String)
}

interface ScanUidPresenterContract: BasePresenter {
    fun validateParentUid(parentUid: String)
}