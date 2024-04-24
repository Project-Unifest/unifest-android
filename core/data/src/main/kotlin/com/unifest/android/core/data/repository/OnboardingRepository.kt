package com.unifest.android.core.data.repository

interface OnboardingRepository {
    suspend fun checkIntroCompletion(): Boolean
    suspend fun completeIntro(flag: Boolean)
    suspend fun checkOnboardingCompletion(): Boolean
    suspend fun completeOnboarding(flag: Boolean)
}
