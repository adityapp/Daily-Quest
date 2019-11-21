package com.dailyquest.feature.common.main.view

import android.content.Intent
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.dailyquest.R
import com.dailyquest.base.BaseActivity
import com.dailyquest.feature.common.home.view.HomeFragment
import com.dailyquest.feature.common.main.MainPresenterContract
import com.dailyquest.feature.common.main.MainViewContract
import com.dailyquest.feature.common.main.presenter.MainPresenter
import com.dailyquest.feature.common.role.view.RoleActivity
import com.dailyquest.feature.parent.children.view.ChildrenFragment
import com.dailyquest.utils.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainPresenterContract>(), MainViewContract {

    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var pref: SessionManager

    override fun layoutId(): Int = R.layout.activity_main

    override fun setupView() {
        beginWith { setupActionBar() }
            .then { setupPresenter() }
            .then { setupToken() }
            .then { setupSession() }
            .then { setupDrawer() }
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

    private fun setupActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupPresenter() {
        presenter = MainPresenter(this, SessionManager(this))
    }

    private fun setupToken() {
        presenter.sendTokenToDatabase()
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
}
