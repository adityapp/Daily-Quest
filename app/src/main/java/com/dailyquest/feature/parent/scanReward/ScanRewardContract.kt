package com.dailyquest.feature.parent.scanReward

import com.dailyquest.base.BasePresenter

interface ScanRewardViewContract{
    fun navigateToMain(value: String)
    fun showFailedMessage(message: String)
    fun showLoadingDialog()
    fun dismissLoadingDialog()
}

interface ScanRewardPresenterContract: BasePresenter{
    fun updateReward(value: String)
}