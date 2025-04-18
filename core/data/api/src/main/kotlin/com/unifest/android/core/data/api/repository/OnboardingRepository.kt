package com.unifest.android.core.data.api.repository

interface OnboardingRepository {
    suspend fun checkIntroCompletion(): Boolean
    suspend fun completeIntro(flag: Boolean)
    suspend fun checkMapOnboardingCompletion(): Boolean
    suspend fun completeMapOnboarding(flag: Boolean)
    suspend fun checkFestivalOnboardingCompletion(): Boolean
    suspend fun completeFestivalOnboarding(flag: Boolean)
}
