package com.dailyquest

import android.view.View
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