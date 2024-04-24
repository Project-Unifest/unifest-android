package com.unifest.android.core.datastore

interface OnboardingDataSource {
    suspend fun checkIntroCompletion(): Boolean
    suspend fun completeIntro(flag: Boolean)
    suspend fun checkOnboardingCompletion(): Boolean
    suspend fun completeOnboarding(flag: Boolean)
}
