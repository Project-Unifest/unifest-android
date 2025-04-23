package com.unifest.android.core.common.extension

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build

inline fun <reified T : Activity> Activity.startActivityWithAnimation(
    withFinish: Boolean,
    intentBuilder: Intent.() -> Intent = { this },
) {
    startActivity(Intent(this, T::class.java).intentBuilder())
    if (Build.VERSION.SDK_INT >= 34) {
        overrideActivityTransition(
            Activity.OVERRIDE_TRANSITION_OPEN,
            android.R.anim.fade_in,
            android.R.anim.fade_out,
        )
    } else {
        @Suppress("DEPRECATION")
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
    if (withFinish) finish()
}

fun Activity.checkLocationPermission(): Boolean {
    return checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
        checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
}

fun Activity.checkNotificationPermission(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
    } else {
        true
    }
}
