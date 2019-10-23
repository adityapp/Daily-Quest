package com.dailyquest.feature.splash.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dailyquest.R
import com.dailyquest.feature.child.mainChild.view.MainChildActivity
import com.dailyquest.feature.parent.mainParent.view.MainParentActivity
import com.dailyquest.feature.role.view.RoleActivity
import com.dailyquest.feature.splash.SplashPresenterContract
import com.dailyquest.feature.splash.SplashViewContract
import com.dailyquest.feature.splash.presenter.SplashPresenter

class SplashActivity : AppCompatActivity(), SplashViewContract {
    private lateinit var presenter: SplashPresenterContract

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        presenter = SplashPresenter(this)
        presenter.auth()
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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        finish()
    }
}
