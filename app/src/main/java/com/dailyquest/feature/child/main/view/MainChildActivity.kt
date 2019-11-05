package com.dailyquest.feature.child.main.view

import com.dailyquest.R
import com.dailyquest.base.BaseActivity
import com.dailyquest.feature.child.main.HomeChildPresenterContract
import com.dailyquest.feature.child.main.HomeChildViewContract
import com.dailyquest.feature.child.main.presenter.MainChildPresenter
import com.dailyquest.utils.beginWith

class MainChildActivity : BaseActivity<HomeChildPresenterContract>(), HomeChildViewContract {
    override fun layoutId() = R.layout.activity_main_child

    override fun setupView() {
        beginWith { setupPresenter() }
    }

    private fun setupPresenter() {
        presenter = MainChildPresenter(this)
    }
}
