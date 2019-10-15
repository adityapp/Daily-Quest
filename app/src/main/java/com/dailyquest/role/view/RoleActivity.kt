package com.dailyquest.role.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dailyquest.R
import com.dailyquest.login.view.LoginActivity
import com.dailyquest.register.view.RegisterActivity
import com.dailyquest.scanUid.view.ScanUidActivity
import com.dailyquest.utils.Constants
import kotlinx.android.synthetic.main.activity_role.*

class RoleActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_role)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            Constants.CAMERA_REQ_CODE
        )

        b_anak.setOnClickListener(this)
        b_orang_tua.setOnClickListener(this)
        tv_daftar.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            b_anak -> {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_DENIED
                ) {
                    val intent = Intent(this, ScanUidActivity::class.java)
                    intent.putExtra(Constants.ROLE, Constants.ANAK)
                    startActivity(intent)
                } else {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.CAMERA),
                        Constants.CAMERA_REQ_CODE
                    )
                }
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
