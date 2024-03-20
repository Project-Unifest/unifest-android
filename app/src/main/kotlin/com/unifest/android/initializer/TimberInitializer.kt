package com.unifest.android.initializer

import android.content.Context
import androidx.startup.Initializer
import com.unifest.android.BuildConfig
import timber.log.Timber

class TimberInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement) =
                    "${BuildConfig.APPLICATION_ID}://${element.fileName}:${element.lineNumber}#${element.methodName}"
            })
        }
    }
    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
