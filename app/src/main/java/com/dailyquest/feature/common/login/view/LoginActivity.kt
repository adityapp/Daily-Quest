package com.dailyquest.feature.common.login.view

import android.content.Intent
import android.widget.Toast
import com.dailyquest.R
import com.dailyquest.base.BaseActivity
import com.dailyquest.feature.child.main.view.MainChildActivity
import com.dailyquest.feature.common.login.LoginPresenterContract
import com.dailyquest.feature.common.login.LoginViewContract
import com.dailyquest.feature.common.login.presenter.LoginPresenter
import com.dailyquest.feature.parent.main.view.MainParentActivity
import com.dailyquest.utils.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<LoginPresenterContract>(), LoginViewContract {
    private lateinit var role: String
    private var parenUid: String? = null

    override fun layoutId(): Int = R.layout.activity_login

    override fun setupView() {
        beginWith { setupActionBarAndToolbar() }
            .then { setupPresenter() }
            .then { getExtra() }
            .then { setupOnClick() }
    }

    override fun navigateToHomeChild() {
        val intent = Intent(this, MainChildActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    override fun navigateToHomeParent() {
        val intent = Intent(this, MainParentActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    override fun showFailedMessage(message: String) {
        dismissLoadingDialog()
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setupActionBarAndToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            title = "Login"
        }
    }

    private fun setupOnClick() {
        b_masuk.setOnClickListener { doLogin() }
    }

    private fun setupPresenter() {
        presenter = LoginPresenter(this, SessionManager(this))
    }

    private fun doLogin() {
        presenter.login(et_email.value(), et_kata_sandi.value(), role, parenUid)
    }

    private fun getExtra() {
        role = intent.getStringExtra(Constants.ROLE)
        parenUid = intent.getStringExtra(Constants.PARENT_UID)
    }

}
