package com.dailyquest.feature.common.main.view

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.dailyquest.R
import com.dailyquest.base.BaseActivity
import com.dailyquest.dialog.ExchangeSuccessDialog
import com.dailyquest.dialog.RewardDialog
import com.dailyquest.feature.common.home.view.HomeFragment
import com.dailyquest.feature.common.main.MainPresenterContract
import com.dailyquest.feature.common.main.MainViewContract
import com.dailyquest.feature.common.main.presenter.MainPresenter
import com.dailyquest.feature.common.role.view.RoleActivity
import com.dailyquest.feature.parent.children.view.ChildrenFragment
import com.dailyquest.feature.parent.scanReward.view.ScanRewardActivity
import com.dailyquest.utils.*
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainPresenterContract>(), MainViewContract {

    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var pref: SessionManager

    override fun layoutId(): Int = R.layout.activity_main

    override fun setupView() {
        beginWith { setupActionBar() }
            .then { setupPresenter() }
            .then { setupSession() }
            .then { setupDrawer() }
            .then { createService() }
            .then { showScanner() }
            .then { setupOnClick() }
    }

    override fun navigateToRole() {
        val intent = Intent(this, RoleActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startAndFinish(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showReward(reward: Int) {
        tv_reward.text = reward.toString()
        cv_reward.show()
        dismissLoadingDialog()
    }

    override fun onResume() {
        super.onResume()
        presenter.getReward()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            Constants.QR_SCAN_REQ_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.let {
                        ExchangeSuccessDialog(
                            this,
                            it.getStringExtra(Constants.DATABASE_REWARD)
                        ).show()
                    }
                }
            }
        }
    }

    override fun showFailedMessage(message: String) {
        showToast(message)
        dismissLoadingDialog()
    }

    private fun showScanner() {
        if (Constants.ORANG_TUA == pref.getRole()) {
            iv_qr_scan.show()
        }
    }

    private fun setupOnClick() {
        cv_reward.setOnClickListener {
            showRewardDialog()
        }

        iv_qr_scan.setOnClickListener {
            startActivityForResult(
                Intent(this, ScanRewardActivity::class.java),
                Constants.QR_SCAN_REQ_CODE
            )
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupPresenter() {
        presenter = MainPresenter(this, SessionManager(this))
    }

    private fun setupSession() {
        pref = SessionManager(this)
    }

    private fun setupDrawer() {
        drawerToggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        if (pref.getRole() == Constants.ANAK) {
            drawer.menu.getItem(1).isVisible = false
        }
        drawer_layout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        fragmentTransaction(HomeFragment())
        drawer.setCheckedItem(R.id.home)

        pageConfig()
    }

    private fun pageConfig() {
        drawer.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> fragmentTransaction(HomeFragment())

                R.id.quest -> fragmentTransaction(ChildrenFragment())

                R.id.profile -> Toast.makeText(this, "Belum Tersedia", Toast.LENGTH_SHORT).show()

                R.id.logout -> {
                    destroyService()
                    presenter.logout()
                }
            }
            drawer_layout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun fragmentTransaction(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_frame,
            fragment
        ).commit()
    }

    private fun createService() {
        startService(Intent(this, LocationTrackingService::class.java))
    }

    private fun destroyService() {
        stopService(Intent(this, LocationTrackingService::class.java))
    }

    private fun showRewardDialog() {
        pref.getParentUid()?.let {
            RewardDialog(this, it).show()
        }
    }
}
