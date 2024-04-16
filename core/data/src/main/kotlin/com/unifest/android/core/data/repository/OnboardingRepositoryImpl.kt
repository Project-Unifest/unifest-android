package com.unifest.android.core.data.repository

import com.unifest.android.core.datastore.OnboardingDataSource
import javax.inject.Inject

class OnboardingRepositoryImpl @Inject constructor(
    private val onboardingDataSource: OnboardingDataSource,
) : OnboardingRepository {

    override suspend fun checkOnboardingCompletion(): Boolean {
        return onboardingDataSource.checkOnboardingCompletion()
    }

    override suspend fun completeOnboarding(flag: Boolean) {
        onboardingDataSource.completeOnboarding(flag)
    }
}
