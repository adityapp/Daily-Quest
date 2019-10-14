package com.dailyquest.mainChild.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dailyquest.R
import com.dailyquest.mainChild.HomeChildPresenterContract
import com.dailyquest.mainChild.HomeChildViewContract
import com.dailyquest.mainChild.presenter.MainChildPresenter

class MainChildActivity : AppCompatActivity(), HomeChildViewContract {
    private lateinit var presenter: HomeChildPresenterContract

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_child)

        presenter = MainChildPresenter(this)
    }
}
