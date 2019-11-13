package com.dailyquest.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.LayoutRes

abstract class BaseDialog(context: Context): Dialog(context){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        setupWindows()
        setupView()
    }

    @LayoutRes abstract fun layoutId(): Int

    abstract fun setupView()

    private fun setupWindows(){
        window?.let {
            it.setBackgroundDrawableResource(android.R.color.transparent)
            it.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )

        }
    }
}