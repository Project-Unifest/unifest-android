package com.unifest.android.core.data.datasource

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import com.unifest.android.core.data.api.datasource.DeviceIdDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DefaultDeviceIdDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
) : DeviceIdDataSource {
    @SuppressLint("HardwareIds")
    override fun getDeviceId(): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }
}
