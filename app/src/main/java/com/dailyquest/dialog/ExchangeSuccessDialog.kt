package com.dailyquest.dialog

import android.content.Context
import com.dailyquest.R
import com.dailyquest.base.BaseDialog
import com.dailyquest.utils.beginWith
import com.dailyquest.utils.then
import kotlinx.android.synthetic.main.dialog_reward_exchange_success.*

class ExchangeSuccessDialog(context: Context, private val reward: String) : BaseDialog(context) {
    override fun layoutId() = R.layout.dialog_reward_exchange_success

    override fun setupView() {
        super.setupView()

            beginWith { setupContent() }
                .then { setupOnClick() }
    }

    private fun setupContent(){
       tv_reward.text = "Rp. $reward"
    }

    private fun setupOnClick(){
        b_ok.setOnClickListener { dismiss() }
    }
}