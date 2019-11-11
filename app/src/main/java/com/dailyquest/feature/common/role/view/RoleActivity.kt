package com.dailyquest.feature.common.role.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dailyquest.R
import com.dailyquest.base.BaseActivity
import com.dailyquest.feature.common.scanUid.view.ScanUidActivity
import com.dailyquest.feature.common.login.view.LoginActivity
import com.dailyquest.feature.common.register.view.RegisterActivity
import com.dailyquest.feature.common.role.RolePresenterContract
import com.dailyquest.feature.common.role.RoleViewContract
import com.dailyquest.feature.common.role.presenter.RolePresenter
import com.dailyquest.utils.Constants
import com.dailyquest.utils.beginWith
import com.dailyquest.utils.then
import kotlinx.android.synthetic.main.activity_role.*

class RoleActivity : BaseActivity<RolePresenterContract>(), RoleViewContract {
    override fun layoutId() = R.layout.activity_role

    override fun setupView() {
        beginWith { setupPresenter() }
            .then { setupPermission() }
            .then { setupOnClick() }
    }

    private fun setupOnClick() {
        b_anak.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_DENIED
            ) {
                val intent = Intent(this, ScanUidActivity::class.java)
                intent.putExtra(Constants.ROLE, Constants.ANAK)
                intent.putExtra(Constants.SOURCE_ACTIVITY, this::class.java.simpleName)
                startActivity(intent)
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    Constants.CAMERA_REQ_CODE
                )
            }
        }
        b_orang_tua.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra(Constants.ROLE, Constants.ORANG_TUA)
            startActivity(intent)
        }
        tv_daftar.setOnClickListener { startActivity(Intent(this, RegisterActivity::class.java)) }
    }

    private fun setupPresenter() {
        presenter = RolePresenter(this)
    }

    private fun setupPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                Constants.CAMERA_REQ_CODE
            )
        }
    }
}
