package com.dailyquest.feature.common.splash.view

import android.content.Intent
import android.os.Handler
import android.util.Log
import com.dailyquest.R
import com.dailyquest.base.BaseActivity
import com.dailyquest.feature.common.main.view.MainActivity
import com.dailyquest.feature.common.role.view.RoleActivity
import com.dailyquest.feature.common.splash.SplashPresenterContract
import com.dailyquest.feature.common.splash.SplashViewContract
import com.dailyquest.feature.common.splash.presenter.SplashPresenter
import com.dailyquest.utils.beginWith
import com.dailyquest.utils.then
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId

class SplashActivity : BaseActivity<SplashPresenterContract>(), SplashViewContract {
    private val notification= FirebaseInstanceId.getInstance().instanceId
        .addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("Hello", "getInstanceId failed", task.exception)
                return@OnCompleteListener
            }

            // Get new Instance ID token
            val token = task.result?.token
            Log.w("Hello", token)
        })

    override fun layoutId() = R.layout.activity_splash

    override fun setupView() {
        beginWith { setupPresenter() }
            .then { presenter.auth() }
            .then { notification }
    }

    override fun navigateToSelectRole() {
        Handler().postDelayed(
            {
                startActivity(Intent(this, RoleActivity::class.java))
                finish()
            }, 3000
        )
    }

    override fun navigateToHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun showFailedMessage(message: String) {
        showToast(message)
    }

    private fun setupPresenter() {
        presenter = SplashPresenter(this)
    }
}
