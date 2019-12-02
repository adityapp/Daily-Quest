package com.dailyquest.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.dailyquest.feature.common.main.view.MainActivity
import com.dailyquest.utils.remove
import com.dailyquest.utils.show
import kotlinx.android.synthetic.main.dialog_loading.*

abstract class BaseFragment<T : BasePresenter> : Fragment() {
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

    fun showLoadingDialog() = (activity as MainActivity?)?.loading_dialog?.show()

    fun dismissLoadingDialog() = (activity as MainActivity?)?.loading_dialog?.remove()

    fun showToast(message: String) = Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}