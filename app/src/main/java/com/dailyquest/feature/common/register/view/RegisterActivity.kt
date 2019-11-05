package com.dailyquest.feature.common.register.view

import android.content.Intent
import com.dailyquest.R
import com.dailyquest.base.BaseActivity
import com.dailyquest.feature.child.main.view.MainChildActivity
import com.dailyquest.feature.common.register.RegisterPresenterContract
import com.dailyquest.feature.common.register.RegisterViewContract
import com.dailyquest.feature.common.register.presenter.RegisterPresenter
import com.dailyquest.feature.parent.main.view.MainParentActivity
import com.dailyquest.utils.beginWith
import com.dailyquest.utils.startAndFinish
import com.dailyquest.utils.then
import com.dailyquest.utils.value
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity<RegisterPresenterContract>(), RegisterViewContract {
    override fun layoutId() = R.layout.activity_register

    override fun setupView() {
        beginWith { setupActionBarAndToolbar() }
            .then { setupPresenter() }
            .then { setupOnClick() }
    }

    override fun navigateToHomeParent() {
        val intent = Intent(this@RegisterActivity, MainParentActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startAndFinish(intent)
    }

    override fun navigateToHomeChild() {
        val intent = Intent(this@RegisterActivity, MainChildActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startAndFinish(intent)
    }

    override fun showFailedMessage(message: String) {
        dismissLoadingDialog()
        showError(message)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setupActionBarAndToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            title = "Register"
        }
    }

    private fun setupOnClick() {
        b_daftar.setOnClickListener { doRegistration() }
    }

    private fun setupPresenter() {
        presenter = RegisterPresenter(this)
    }

    private fun doRegistration() {
        presenter.register(
            et_nama_lengkap.value(),
            et_email.value(),
            et_kata_sandi.value(),
            et_konfirmasi_kata_sandi.value(),
            s_role.value()
        )
    }
}
