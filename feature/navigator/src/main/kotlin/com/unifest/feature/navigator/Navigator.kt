package com.unifest.feature.navigator

import android.app.Activity
import android.content.Intent

// https://speakerdeck.com/fornewid/android-modularization-recipe?slide=45
interface Navigator {
    fun navigateFrom(
        activity: Activity,
        withFinish: Boolean,
        intentBuilder: Intent.() -> Intent = { this },
    )
}
