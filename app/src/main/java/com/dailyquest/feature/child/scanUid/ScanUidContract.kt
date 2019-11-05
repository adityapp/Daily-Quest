package com.dailyquest.feature.child.scanUid

interface ScanUidViewContract {
    fun navigateToLogin(parentUid: String)
    fun showFailedMessage(message: String)
}

interface ScanUidPresenterContract {
    fun validateParentUid(parentUid: String)
}