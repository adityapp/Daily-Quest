package com.dailyquest.feature.child.scanUid.view

import android.content.Intent
import android.widget.Toast
import com.dailyquest.R
import com.dailyquest.base.BaseActivity
import com.dailyquest.feature.child.scanUid.ScanUidPresenterContract
import com.dailyquest.feature.child.scanUid.ScanUidViewContract
import com.dailyquest.feature.child.scanUid.presenter.ScanUidPresenter
import com.dailyquest.feature.common.login.view.LoginActivity
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
            presenter.validateParentUid(uid)
        }
    }

    override fun navigateToLogin(parentUid: String) {
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra(Constants.ROLE, role)
        intent.putExtra(Constants.PARENT_UID, parentUid)
        startActivity(intent)
    }

    override fun showFailedMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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
        presenter = ScanUidPresenter(this)
    }

    private fun getExtra() {
        role = intent.getStringExtra(Constants.ROLE)
    }

    private fun setupScanner() {
        scanner = ZXingScannerView(this)
        scanner.resumeCameraPreview(this)
        scanner.setAutoFocus(true)
        frame_scanner.addView(scanner)
    }
}
