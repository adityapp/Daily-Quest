package com.dailyquest.utils

import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        p0.notification?.let { it ->
            val notification = NotificationCompat.Builder(this, it.channelId ?: "")
                .setContentTitle(it.title)
                .setContentText(it.body)
                .build()
            val manager = NotificationManagerCompat.from(applicationContext)
            manager.notify(123, notification)
        }
    }

    override fun onNewToken(token: String) {
        Log.d("Hello", "Refreshed token: $token")
    }
}