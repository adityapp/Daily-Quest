package com.dailyquest.role.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dailyquest.utils.Constants
import com.dailyquest.R
import com.dailyquest.login.view.LoginActivity
import com.dailyquest.register.view.RegisterActivity
import kotlinx.android.synthetic.main.activity_role.*

class RoleActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_role)

        b_anak.setOnClickListener(this)
        b_orang_tua.setOnClickListener(this)
        tv_daftar.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            b_anak -> {
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra(Constants.ROLE, Constants.ANAK)
                startActivity(intent)
            }
            b_orang_tua -> {
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra(Constants.ROLE, Constants.ORANG_TUA)
                startActivity(intent)
            }
            tv_daftar -> startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
