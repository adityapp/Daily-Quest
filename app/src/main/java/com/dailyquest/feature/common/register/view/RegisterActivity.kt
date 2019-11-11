package com.dailyquest.feature.common.register.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.view.View
import android.widget.AdapterView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dailyquest.R
import com.dailyquest.base.BaseActivity
import com.dailyquest.feature.child.main.view.MainChildActivity
import com.dailyquest.feature.common.scanUid.view.ScanUidActivity
import com.dailyquest.feature.common.register.RegisterPresenterContract
import com.dailyquest.feature.common.register.RegisterViewContract
import com.dailyquest.feature.common.register.presenter.RegisterPresenter
import com.dailyquest.feature.parent.main.view.MainParentActivity
import com.dailyquest.utils.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity<RegisterPresenterContract>(), RegisterViewContract {
    private var parentUid = ""

    override fun layoutId() = R.layout.activity_register

    override fun setupView() {
        beginWith { setupActionBarAndToolbar() }
            .then { setupPresenter() }
            .then { setupOnSelected() }
            .then { setupOnClick() }
    }

    override fun navigateToHomeParent() {
        val intent = Intent(this@RegisterActivity, MainParentActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startAndFinish(intent)
    }

    override fun navigateToHomeChild() {
        val intent = Intent(this@RegisterActivity, MainChildActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startAndFinish(intent)
    }

    override fun showFailedMessage(message: String) {
        dismissLoadingDialog()
        showToast(message)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            Constants.CAMERA_REQ_CODE -> {
                if (resultCode == Activity.RESULT_OK){
                    data?.let {
                        parentUid = it.getStringExtra(Constants.PARENT_UID)
                    }
                }else{
                    showToast("Batal!")
                }
            }
        }
    }

    private fun setupActionBarAndToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            title = "Register"
        }
    }

    private fun setupOnClick() {
        b_daftar.setOnClickListener { doRegistration() }
        b_scan_uid.setOnClickListener { doScanning() }
    }

    private fun setupOnSelected() {
        s_role.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //Not implement needed
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 0) b_scan_uid.show() else b_scan_uid.remove()
            }

        }
    }

    private fun setupPresenter() {
        presenter = RegisterPresenter(this, SessionManager(this))
    }

    private fun doScanning() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_DENIED
        ) {
            val intent = Intent(this, ScanUidActivity::class.java)
            intent.putExtra(Constants.ROLE, Constants.ANAK)
            intent.putExtra(Constants.SOURCE_ACTIVITY, this::class.java.simpleName)
            startActivityForResult(intent, Constants.CAMERA_REQ_CODE)
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                Constants.CAMERA_REQ_CODE
            )
        }
    }

    private fun doRegistration() {
        presenter.register(
            et_nama_lengkap.value(),
            et_email.value(),
            et_kata_sandi.value(),
            et_konfirmasi_kata_sandi.value(),
            s_role.value(),
            parentUid
        )
    }
}
