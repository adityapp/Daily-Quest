package com.dailyquest.feature.children.scanUid.view

import android.app.Activity
import android.content.Intent
import com.dailyquest.R
import com.dailyquest.base.BaseActivity
import com.dailyquest.feature.common.login.view.LoginActivity
import com.dailyquest.feature.children.scanUid.ScanUidPresenterContract
import com.dailyquest.feature.children.scanUid.ScanUidViewContract
import com.dailyquest.feature.children.scanUid.presenter.ScanUidPresenter
import com.dailyquest.utils.Constants
import com.dailyquest.utils.beginWith
import com.dailyquest.utils.then
import com.google.zxing.Result
import kotlinx.android.synthetic.main.activity_scan_uid.*
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScanUidActivity : BaseActivity<ScanUidPresenterContract>(), ZXingScannerView.ResultHandler,
    ScanUidViewContract {

    private lateinit var scanner: ZXingScannerView
    private lateinit var role: String
    private lateinit var sourceActivity: String

    override fun layoutId() = R.layout.activity_scan_uid

    override fun setupView() {
        beginWith { setupActionBar() }
            .then { setupPresenter() }
            .then { getExtra() }
            .then { setupScanner() }
    }

    override fun onResume() {
        super.onResume()
        scanner.startCamera()
    }

    override fun onPause() {
        super.onPause()
        scanner.stopCamera()
    }

    override fun handleResult(rawResult: Result?) {
        rawResult?.text?.let { uid ->
            presenter.validateParentUid(uid, sourceActivity)
        }
    }

    override fun navigateToLogin(parentUid: String) {
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra(Constants.ROLE, role)
        intent.putExtra(Constants.PARENT_UID, parentUid)
        startActivity(intent)
        finish()
    }

    override fun navigateToRegister(parentUid: String) {
        val intent = Intent()
        intent.putExtra(Constants.PARENT_UID, parentUid)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun showFailedMessage(message: String) {
        showToast(message)
        scanner.resumeCameraPreview(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupPresenter() {
        presenter =
            ScanUidPresenter(
                this
            )
    }

    private fun getExtra() {
        role = intent.getStringExtra(Constants.ROLE)
        sourceActivity = intent.getStringExtra(Constants.SOURCE_ACTIVITY)
    }

    private fun setupScanner() {
        scanner = ZXingScannerView(this)
        scanner.resumeCameraPreview(this)
        scanner.setAutoFocus(true)
        frame_scanner.addView(scanner)
    }
}
