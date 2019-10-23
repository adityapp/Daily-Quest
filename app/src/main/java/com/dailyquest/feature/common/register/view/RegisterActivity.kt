package com.dailyquest.feature.register.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dailyquest.*
import com.dailyquest.feature.child.mainChild.view.MainChildActivity
import com.dailyquest.feature.parent.mainParent.view.MainParentActivity
import com.dailyquest.feature.register.RegisterPresenterContract
import com.dailyquest.feature.register.RegisterViewContract
import com.dailyquest.feature.register.presenter.RegisterPresenter
import com.dailyquest.utils.remove
import com.dailyquest.utils.show
import com.dailyquest.utils.value
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.dialog_loading.*

class RegisterActivity : AppCompatActivity(), RegisterViewContract, View.OnClickListener {
    private lateinit var presenter: RegisterPresenterContract

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Register"

        presenter = RegisterPresenter(this)

        b_daftar.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            b_daftar -> presenter.register(
                et_nama_lengkap.value(),
                et_email.value(),
                et_kata_sandi.value(),
                et_konfirmasi_kata_sandi.value(),
                s_role.value()
            )
        }
    }

    override fun navigateToHomeParent() {
        val intent = Intent(this, MainParentActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    override fun navigateToHomeChild() {
        val intent = Intent(this, MainChildActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    override fun showFailedMessage(message: String) {
        dismissLoadingDialog()
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoadingDialog() {
        loading_dialog.show()
    }

    override fun dismissLoadingDialog() {
        loading_dialog.remove()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
