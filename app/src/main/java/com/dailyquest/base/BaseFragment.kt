package com.dailyquest.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment<T : BasePresenter> : Fragment(), BasePresenter {
    protected lateinit var view: ViewGroup
    protected lateinit var presenter: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(layoutId(), container, false) as ViewGroup

        setupView()

        return view
    }

    @LayoutRes
    abstract fun layoutId(): Int

    abstract fun setupView()
}