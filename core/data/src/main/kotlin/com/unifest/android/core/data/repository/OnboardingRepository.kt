package com.unifest.android.core.data.repository

interface OnboardingRepository {
    suspend fun checkOnboardingCompletion(): Boolean
    suspend fun completeOnboarding(flag: Boolean)
}
