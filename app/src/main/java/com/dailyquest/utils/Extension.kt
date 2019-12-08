package com.dailyquest.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import com.dailyquest.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
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

fun FloatingActionButton.setStatusIndicator(context: Context, status: String) {
    val color = when (status) {
        Constants.STATUS_OPEN -> R.color.colorPrimary
        Constants.STATUS_ONGOING -> R.color.green
        Constants.STATUS_FINISH -> R.color.red
        else -> R.color.gray
    }

    backgroundTintList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        resources.getColorStateList(color, context.theme)
    } else {
        resources.getColorStateList(color)
    }
}

fun Bitmap.getImageUri(context: Context): Uri? {
    Log.d("Hello", this.height.toString())
    val outImage = Bitmap.createScaledBitmap(this, this.width * 4, this.height * 4, true)
    val path =
        MediaStore.Images.Media.insertImage(context.contentResolver, outImage, "DailyQuest", null)
    return Uri.parse(path)
}