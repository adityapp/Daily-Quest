package com.dailyquest.utils

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.EditText
import android.widget.Spinner
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

fun String.dateToTimestamp(): Long{
    val formatter = SimpleDateFormat("dd/MM/yyyy  HH:mm")
    return (formatter.parse(this) as Date).time/1000
}