package com.dailyquest.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.LayoutRes

abstract class BaseDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layoutId())
        setupWindows()
        setupView()
    }

    @LayoutRes
    protected abstract fun layoutId(): Int

    protected open fun setupView() {
        setCancelable(false)
    }

    private fun setupWindows() {
        window?.let {
            it.setBackgroundDrawableResource(android.R.color.transparent)
            it.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )

        }
    }

    fun showToast(message: String) = Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}