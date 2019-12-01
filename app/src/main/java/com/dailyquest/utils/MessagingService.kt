package com.dailyquest.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.dailyquest.R
import com.dailyquest.utils.Constants.Companion.DEFAULT_CHANNEL_ID
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        p0.data?.let { it ->
            val notification = NotificationCompat
                .Builder(this, DEFAULT_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentTitle(it["title"])
                .setContentText(it["text"])
                .setAutoCancel(true)

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel(
                    DEFAULT_CHANNEL_ID,
                    "Default Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).also {
                    manager.createNotificationChannel(it)
                    notification.setChannelId(DEFAULT_CHANNEL_ID)
                }
            }

            manager.notify(0, notification.build())
        }
    }
}