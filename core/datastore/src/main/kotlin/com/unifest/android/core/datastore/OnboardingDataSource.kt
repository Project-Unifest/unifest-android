package com.unifest.android.core.datastore

interface OnboardingDataSource {
    suspend fun checkOnboardingCompletion(): Boolean
    suspend fun completeOnboarding(flag: Boolean)
}
