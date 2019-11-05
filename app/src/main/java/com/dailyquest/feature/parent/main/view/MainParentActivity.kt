package com.dailyquest.feature.parent.main.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.dailyquest.R
import com.dailyquest.base.BaseActivity
import com.dailyquest.feature.parent.home.view.HomeParentFragment
import com.dailyquest.feature.parent.main.MainParentPresenterContract
import com.dailyquest.feature.parent.main.MainParentViewContract
import com.dailyquest.feature.parent.main.presenter.MainParentPresenter
import com.dailyquest.feature.common.role.view.RoleActivity
import com.dailyquest.utils.SessionManager
import com.dailyquest.utils.beginWith
import com.dailyquest.utils.then
import kotlinx.android.synthetic.main.activity_main_parent.*

class MainParentActivity : BaseActivity<MainParentPresenterContract>(), MainParentViewContract {

    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun layoutId(): Int = R.layout.activity_main_parent

    override fun setupView() {
        beginWith { setupActionBar() }
            .then { setupPresenter() }
            .then { setupDrawer() }
    }

    private fun pageConfig() {
        drawer.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.quest -> supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_frame,
                    HomeParentFragment()
                ).commit()
                R.id.profile -> Toast.makeText(this, "Belum Tersedia", Toast.LENGTH_SHORT).show()
                R.id.logout -> {
                    presenter.logout()
                }
            }
            drawer_layout.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun navigateToRole() {
        val intent = Intent(this, RoleActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupActionBar(){
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupPresenter(){
        presenter = MainParentPresenter(this, SessionManager(this))
    }

    private fun setupDrawer(){
        drawerToggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawer_layout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_frame,
            HomeParentFragment()
        ).commit()
        drawer.setCheckedItem(R.id.quest)

        pageConfig()
    }
}
