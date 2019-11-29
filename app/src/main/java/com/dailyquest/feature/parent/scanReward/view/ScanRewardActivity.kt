package com.dailyquest.feature.parent.scanReward.view

import android.app.Activity
import com.dailyquest.R
import com.dailyquest.base.BaseActivity
import com.dailyquest.feature.parent.scanReward.ScanRewardPresenterContract
import com.dailyquest.feature.parent.scanReward.ScanRewardViewContract
import com.dailyquest.feature.parent.scanReward.presenter.ScanRewardPresenter
import com.dailyquest.utils.Constants
import com.dailyquest.utils.beginWith
import com.dailyquest.utils.then
import com.google.zxing.Result
import kotlinx.android.synthetic.main.activity_scan_reward.*
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScanRewardActivity : BaseActivity<ScanRewardPresenterContract>(), ScanRewardViewContract,
    ZXingScannerView.ResultHandler {
    private lateinit var scanner: ZXingScannerView

    override fun layoutId() = R.layout.activity_scan_reward

    override fun setupView() {
        beginWith { setupActionBar() }
            .then { setupPresenter() }
            .then { setupScanner() }
    }

    override fun navigateToMain(value: String) {
        dismissLoadingDialog()
        setResult(Activity.RESULT_OK, intent.putExtra(Constants.DATABASE_REWARD, value))
        finish()
    }

    override fun showFailedMessage(message: String) {
        showToast(message)
        dismissLoadingDialog()
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
        rawResult?.text?.let { value ->
            presenter.updateReward(value)
        }
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
        presenter = ScanRewardPresenter(this)
    }

    private fun setupScanner() {
        scanner = ZXingScannerView(this)
        scanner.resumeCameraPreview(this)
        scanner.setAutoFocus(true)
        frame_scanner.addView(scanner)
    }
}
