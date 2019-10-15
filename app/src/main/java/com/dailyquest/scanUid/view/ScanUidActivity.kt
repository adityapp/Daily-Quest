package com.dailyquest.scanUid.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dailyquest.R
import com.dailyquest.login.view.LoginActivity
import com.dailyquest.scanUid.ScanUidPresenterContract
import com.dailyquest.scanUid.ScanUidViewContract
import com.dailyquest.scanUid.presenter.ScanUidPresenter
import com.dailyquest.utils.Constants
import com.google.zxing.Result
import kotlinx.android.synthetic.main.activity_scan_uid.*
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScanUidActivity : AppCompatActivity(), ZXingScannerView.ResultHandler, ScanUidViewContract {
    private lateinit var scanner: ZXingScannerView
    private lateinit var presenter: ScanUidPresenterContract
    private lateinit var role: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_uid)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = ScanUidPresenter(this)
        role = intent.getStringExtra(Constants.ROLE)

        scanner = ZXingScannerView(this)
        scanner.resumeCameraPreview(this)
        scanner.setAutoFocus(true)
        frame_scanner.addView(scanner)
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
}
