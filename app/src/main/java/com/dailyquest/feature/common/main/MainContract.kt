package com.dailyquest.feature.common.main

import com.dailyquest.base.BasePresenter

interface MainViewContract {
    fun navigateToRole()
    fun showReward(reward: Int)
    fun showFailedMessage(message: String)
    fun showLoadingDialog()
    fun dismissLoadingDialog()
}

interface MainPresenterContract : BasePresenter {
    fun logout()
    fun getReward()
}