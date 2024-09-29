package com.unifest.android.initializer

import android.content.Context
import androidx.startup.Initializer
import com.google.firebase.messaging.FirebaseMessaging
import com.unifest.android.BuildConfig
import timber.log.Timber

class FirebaseMessagingInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        FirebaseMessaging.getInstance().subscribeToTopic("2")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Timber.d("Subscribed to topic successfully")
                } else {
                    Timber.e("Failed to subscribe to topic")
                }
            }

        if (BuildConfig.DEBUG) {
            FirebaseMessaging.getInstance().subscribeToTopic("test")
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Timber.d("Subscribed to topic successfully")
                    } else {
                        Timber.e("Failed to subscribe to topic")
                    }
                }
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
