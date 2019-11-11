package com.dailyquest.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.dailyquest.utils.remove
import com.dailyquest.utils.show
import kotlinx.android.synthetic.main.dialog_loading.*

abstract class BaseActivity<T : BasePresenter> : AppCompatActivity(), BasePresenter {

    protected lateinit var presenter: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        setupView()
    }

    @LayoutRes
    abstract fun layoutId(): Int

    abstract fun setupView()

    fun showLoadingDialog() = loading_dialog.show()

    fun dismissLoadingDialog() = loading_dialog.remove()

    fun showToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

