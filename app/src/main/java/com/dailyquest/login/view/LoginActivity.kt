package com.dailyquest.login.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dailyquest.Constants
import com.dailyquest.R
import com.dailyquest.login.LoginViewContract
import com.dailyquest.login.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginViewContract, View.OnClickListener {
    private lateinit var role: String
    private lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Login"

        presenter = LoginPresenter(this)
        role = intent.getStringExtra(Constants.ROLE)

        b_masuk.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v) {
            b_masuk -> presenter.login(et_email.text.toString(), et_kata_sandi.text.toString())
        }
    }

    override fun showLoadingDialog() {
        Log.d("HELLO", "valid")
    }

    override fun dismissLoadingDialog() {
        Log.d("HELLO", "invalid")
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
