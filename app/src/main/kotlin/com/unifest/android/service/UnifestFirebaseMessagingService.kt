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
import com.unifest.android.core.designsystem.R
import com.unifest.android.feature.main.MainActivity
import timber.log.Timber

class UnifestFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if (remoteMessage.notification != null) {
            sendNotification(remoteMessage)
        }
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        val requestCode = System.currentTimeMillis().toInt()

        val intent = Intent(this, MainActivity::class.java)

        for (key in remoteMessage.data.keys) {
            intent.putExtra(key, remoteMessage.data.getValue(key))
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntentFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        val pendingIntent = PendingIntent.getActivity(this, requestCode, intent, pendingIntentFlags)

        val channelId = CHANNEL_ID

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
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
