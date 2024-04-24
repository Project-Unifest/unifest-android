package com.unifest.android.core.data.repository

import com.unifest.android.core.datastore.OnboardingDataSource
import javax.inject.Inject

class OnboardingRepositoryImpl @Inject constructor(
    private val onboardingDataSource: OnboardingDataSource,
) : OnboardingRepository {
    override suspend fun checkIntroCompletion(): Boolean {
        return onboardingDataSource.checkIntroCompletion()
    }

    override suspend fun completeIntro(flag: Boolean) {
        onboardingDataSource.completeIntro(flag)
    }

    override suspend fun checkOnboardingCompletion(): Boolean {
        return onboardingDataSource.checkOnboardingCompletion()
    }

    override suspend fun completeOnboarding(flag: Boolean) {
        onboardingDataSource.completeOnboarding(flag)
    }
}
