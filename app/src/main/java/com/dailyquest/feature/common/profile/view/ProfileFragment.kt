package com.dailyquest.feature.common.profile.view


import com.dailyquest.R
import com.dailyquest.base.BaseFragment
import com.dailyquest.feature.common.profile.ProfilePresenterContract
import com.dailyquest.feature.common.profile.ProfileViewContract
import com.dailyquest.feature.common.profile.presenter.ProfilePresenter
import com.dailyquest.model.UserModel
import com.dailyquest.utils.SessionManager
import com.dailyquest.utils.beginWith
import com.dailyquest.utils.then
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : BaseFragment<ProfilePresenterContract>(), ProfileViewContract {
    private lateinit var pref: SessionManager

    override fun layoutId() = R.layout.fragment_profile

    override fun setupView() {
        beginWith { setupSession() }
            .then { setupPresenter() }
            .then { setupOnClick() }
    }

    override fun showProfile(user: UserModel) {
        view.tv_fullname.text = user.fullName
        view.tv_role.text = user.role

        dismissLoadingDialog()
    }

    override fun showFailedMessage(message: String) {
        showToast(message)
        dismissLoadingDialog()
    }

    override fun onResume() {
        super.onResume()
        presenter.getUserProfile()
    }

    private fun setupSession() {
        context?.let {
            pref = SessionManager(it)
        }
    }

    private fun setupPresenter() {
        presenter = ProfilePresenter(this, pref)
    }

    private fun setupOnClick() {
        view.ll_edit_name.setOnClickListener { }

        view.cv_change_password.setOnClickListener { }
    }
}
