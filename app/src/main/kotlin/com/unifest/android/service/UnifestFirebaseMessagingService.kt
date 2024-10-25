package com.unifest.android.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.unifest.android.core.designsystem.R as designR
import com.unifest.android.feature.main.MainActivity
import timber.log.Timber

class UnifestFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Timber.d("$remoteMessage")
        Timber.d("${remoteMessage.notification?.title}")
        Timber.d("${remoteMessage.notification?.body}")
        Timber.d("${remoteMessage.data.size}")
        Timber.d("${remoteMessage.data.keys}")
        Timber.d("${remoteMessage.data.entries}")

        if (remoteMessage.notification != null) {
            sendNotification(remoteMessage)
        }
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        val requestCode = System.currentTimeMillis().toInt()

        // Activity 의 onNewIntent 가 호출되기 위해선 해당 flag 들이 필요함
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra("notification", true)
        }

        for (key in remoteMessage.data.keys) {
            val value = remoteMessage.data[key]
            when {
                value == null -> Timber.d("Null value for key: $key")
                else -> {
                    intent.putExtra(key, value)
                    Timber.d("data -> key $key -> $value")
                }
            }
        }

        val pendingIntentFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        val pendingIntent = PendingIntent.getActivity(this, requestCode, intent, pendingIntentFlags)

        val channelId = CHANNEL_ID

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(designR.mipmap.ic_launcher)
            .setContentTitle(remoteMessage.notification?.title.toString())
            .setContentText(remoteMessage.notification?.body.toString())
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(channelId, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(channel)

        notificationManager.notify(requestCode, notificationBuilder.build())
    }

    override fun onNewToken(token: String) {
        Timber.d("Refreshed token: $token")
    }

    companion object {
        private const val CHANNEL_ID = "unifest"
        private const val CHANNEL_NAME = "unifest_notification"
    }
}
