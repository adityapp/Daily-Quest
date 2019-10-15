package com.dailyquest.utils

import android.view.View
import android.widget.EditText
import android.widget.Spinner
import java.util.regex.Pattern

fun View.show() {
    visibility = View.VISIBLE
}

fun View.remove() {
    visibility = View.GONE
}

fun String.isEmailValid(): Boolean {
    return Pattern.compile(Constants.EMAIL_PATTERN).matcher(this).matches() && !this.isNullOrBlank()
}

fun String.isPasswordValid(): Boolean {
    return this.length >= 6 && !this.isNullOrBlank()
}

fun EditText.value(): String{
    return this.text.toString()
}

fun Spinner.value(): String{
    return this.selectedItem.toString()
}