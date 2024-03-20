package com.unifest.android.core.common.extension

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

// https://stackoverflow.com/questions/64675386/how-to-get-activity-in-compose
fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    error("Permissions should be called in the context of an Activity")
}
