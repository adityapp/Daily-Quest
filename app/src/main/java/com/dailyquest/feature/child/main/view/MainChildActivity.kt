package com.dailyquest.feature.child.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dailyquest.R
import com.dailyquest.feature.child.main.HomeChildPresenterContract
import com.dailyquest.feature.child.main.HomeChildViewContract
import com.dailyquest.feature.child.main.presenter.MainChildPresenter

class MainChildActivity : AppCompatActivity(), HomeChildViewContract {
    private lateinit var presenter: HomeChildPresenterContract

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_child)

        presenter = MainChildPresenter(this)
    }
}
