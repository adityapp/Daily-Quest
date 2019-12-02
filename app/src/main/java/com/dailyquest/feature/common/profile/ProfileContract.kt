package com.dailyquest.feature.common.profile

import com.dailyquest.base.BasePresenter
import com.dailyquest.model.UserModel

interface ProfileViewContract{
    fun showProfile(user: UserModel)
    fun showFailedMessage(message: String)
    fun showLoadingDialog(): Unit?
    fun dismissLoadingDialog(): Unit?
}

interface ProfilePresenterContract: BasePresenter{
    fun getUserProfile()
}