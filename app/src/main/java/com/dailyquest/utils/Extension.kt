package com.dailyquest.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.dailyquest.R
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

fun Any.beginWith(begin: () -> Unit) = begin.invoke()

fun Any.then(then: () -> Unit) = then.invoke()

fun Activity.startAndFinish(intent: Intent) = beginWith { startActivity(intent) }.then { finish() }

fun View.show() {
    visibility = View.VISIBLE
}

fun View.remove() {
    visibility = View.GONE
}

fun String.isEmailValid(): Boolean {
    return Pattern.compile(Constants.EMAIL_PATTERN).matcher(this).matches() && !this.isBlank()
}

fun String.isPasswordValid() = this.trim().length >= 6 && !this.contains(" ")

fun EditText.value(): String {
    return this.text.toString()
}

fun Spinner.value(): String {
    return this.selectedItem.toString()
}

fun String.dateToTimestamp(): Long {
    val formatter = SimpleDateFormat(Constants.DATE_FORMAT)
    return Timestamp(formatter.parse(this).time).time
}

fun Long.timestampToDate(): String {
    val formatter = SimpleDateFormat(Constants.DATE_FORMAT)
    return formatter.format(Date(this))
}

fun CardView.setStatusIndicator(context: Context, status: String){
    setCardBackgroundColor(
        ContextCompat.getColor(
            context,
            when (status) {
                Constants.STATUS_OPEN -> R.color.colorPrimary
                Constants.STATUS_ONGOING -> R.color.green
                Constants.STATUS_CLOSE -> R.color.red
                else -> R.color.gray
            }
        )
    )
}