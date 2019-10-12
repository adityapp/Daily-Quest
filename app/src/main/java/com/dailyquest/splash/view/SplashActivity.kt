package com.dailyquest.splash.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import com.dailyquest.R
import com.dailyquest.homeChild.view.HomeChildActivity
import com.dailyquest.homeParent.view.HomeParentActivity
import com.dailyquest.role.view.RoleActivity
import com.dailyquest.splash.SplashViewContract
import com.dailyquest.splash.presenter.SplashPresenter

class SplashActivity : AppCompatActivity(), SplashViewContract {
    private lateinit var presenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        presenter = SplashPresenter(this)
        presenter.auth()
    }

    override fun navigateToSelectRole() {
        Handler().postDelayed(3000) {
            startActivity(Intent(this, RoleActivity::class.java))
            finish()
        }
    }

    override fun navigateToHomeParent() {
        Handler().postDelayed(3000) {
            startActivity(Intent(this, HomeParentActivity::class.java))
            finish()
        }
    }

    override fun navigateToHomeChild() {
        Handler().postDelayed(3000) {
            startActivity(Intent(this, HomeChildActivity::class.java))
            finish()
        }
    }
}
