package com.dailyquest.feature.common.splash.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dailyquest.R
import com.dailyquest.base.BaseActivity
import com.dailyquest.feature.child.main.view.MainChildActivity
import com.dailyquest.feature.parent.main.view.MainParentActivity
import com.dailyquest.feature.common.role.view.RoleActivity
import com.dailyquest.feature.common.splash.SplashPresenterContract
import com.dailyquest.feature.common.splash.SplashViewContract
import com.dailyquest.feature.common.splash.presenter.SplashPresenter
import com.dailyquest.utils.beginWith
import com.dailyquest.utils.then

class SplashActivity : BaseActivity<SplashPresenterContract>(), SplashViewContract {
    override fun layoutId() = R.layout.activity_splash

    override fun setupView() {
        beginWith { setupPresenter() }
            .then { presenter.auth() }
    }

    override fun navigateToSelectRole() {
        Handler().postDelayed(
            {
                startActivity(Intent(this, RoleActivity::class.java))
                finish()
            }, 3000
        )
    }

    override fun navigateToHomeParent() {
        startActivity(Intent(this, MainParentActivity::class.java))
        finish()
    }

    override fun navigateToHomeChild() {
        startActivity(Intent(this, MainChildActivity::class.java))
        finish()
    }

    override fun showFailedMessage(message: String) {
        showError(message)
    }

    private fun setupPresenter(){
        presenter = SplashPresenter(this)
    }
}
