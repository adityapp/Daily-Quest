package com.dailyquest.dialog

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.EditText
import com.dailyquest.R
import com.dailyquest.base.BaseDialog
import com.dailyquest.model.QuestModel
import com.dailyquest.utils.beginWith
import com.dailyquest.utils.dateToTimestamp
import com.dailyquest.utils.value
import kotlinx.android.synthetic.main.dialog_new_quest.*
import java.util.*

class NewQuestDialog(context: Context, private val addListener: (QuestModel) -> Unit) :
    BaseDialog(context) {
    private val calendar = Calendar.getInstance()

    override fun layoutId() = R.layout.dialog_new_quest

    override fun setupView() {
        super.setupView()

        beginWith { setupOnClick() }
    }

    private fun setupOnClick() {
        b_add.setOnClickListener {
            when {
                et_title.value().isBlank() -> showToast("Judul tidak boleh kosong!")
                et_description.value().isBlank() -> showToast("Deskripsi tidak boleh kosong!")
                et_reward.value().isBlank() -> showToast("Hadiah tidak boleh kosong")
                et_start_time.value().isBlank() -> showToast("Waktu pengerjaan tidak boleh kosong!")
                et_end_time.value().isBlank() -> showToast("Waktu selesai tidak boleh kosong!")
                else -> {
                    addListener.invoke(
                        QuestModel(
                            title = et_title.value(),
                            description = et_description.value(),
                            startTime = et_start_time.value().dateToTimestamp(),
                            endTime = et_end_time.value().dateToTimestamp(),
                            createdAt = calendar.timeInMillis,
                            reward = et_reward.value().toInt(),
                            hideReward = cb_hide_reward.isChecked
                        )
                    )
                    dismiss()
                }
            }
        }

        iv_close.setOnClickListener {
            dismiss()
        }

        et_start_time.setOnClickListener {
            showDatePickerDialog(et_start_time)
        }

        et_end_time.setOnClickListener {
            showDatePickerDialog(et_end_time)
        }
    }

    private fun showDatePickerDialog(editText: EditText) {
        val listener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            showTimePickerDialog(editText, "$dayOfMonth/${month + 1}/$year")
        }

        DatePickerDialog(
            context, listener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun showTimePickerDialog(editText: EditText, date: String) {
        val listener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            editText.setText("$date  $hourOfDay:$minute")
        }

        TimePickerDialog(
            context, listener,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }
}