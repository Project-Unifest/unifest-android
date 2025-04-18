package com.unifest.android.core.datastore.api

interface OnboardingDataSource {
    suspend fun checkIntroCompletion(): Boolean
    suspend fun completeIntro(flag: Boolean)
    suspend fun checkMapOnboardingCompletion(): Boolean
    suspend fun completeMapOnboarding(flag: Boolean)
    suspend fun checkFestivalOnboardingCompletion(): Boolean
    suspend fun completeFestivalOnboarding(flag: Boolean)
}
