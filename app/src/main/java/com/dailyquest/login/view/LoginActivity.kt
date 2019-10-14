package com.dailyquest.login.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dailyquest.*
import com.dailyquest.mainChild.view.MainChildActivity
import com.dailyquest.mainParent.view.MainParentActivity
import com.dailyquest.login.LoginPresenterContract
import com.dailyquest.login.LoginViewContract
import com.dailyquest.login.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.dialog_loading.*

class LoginActivity : AppCompatActivity(), LoginViewContract, View.OnClickListener {
    private lateinit var role: String
    private lateinit var presenter: LoginPresenterContract

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
            b_masuk -> presenter.login(et_email.value(), et_kata_sandi.value(), role)
        }
    }

    override fun showLoadingDialog() {
        loading_dialog.show()
    }

    override fun dismissLoadingDialog() {
        loading_dialog.remove()
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
}
