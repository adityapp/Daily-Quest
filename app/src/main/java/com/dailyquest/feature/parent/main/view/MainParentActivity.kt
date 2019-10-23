package com.dailyquest.feature.parent.mainParent.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.dailyquest.R
import com.dailyquest.feature.parent.homeParent.view.HomeParentFragment
import com.dailyquest.feature.parent.mainParent.MainParentPresenterContract
import com.dailyquest.feature.parent.mainParent.MainParentViewContract
import com.dailyquest.feature.parent.mainParent.presenter.MainParentPresenter
import com.dailyquest.feature.common.role.view.RoleActivity
import com.dailyquest.utils.SessionManager
import kotlinx.android.synthetic.main.activity_main_parent.*

class MainParentActivity : AppCompatActivity(), MainParentViewContract {
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var presenter: MainParentPresenterContract

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_parent)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        presenter = MainParentPresenter(this, SessionManager(this))

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
}
