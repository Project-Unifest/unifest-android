package com.nexters.bandalart.core.data.impl.repository

import com.nexters.bandalart.core.data.api.repository.OnboardingRepository
import com.unifest.android.core.datastore.OnboardingDataSource
import javax.inject.Inject

internal class DefaultOnboardingRepository @Inject constructor(
    private val onboardingDataSource: OnboardingDataSource,
) : OnboardingRepository {
    override suspend fun checkIntroCompletion(): Boolean {
        return onboardingDataSource.checkIntroCompletion()
    }

    override suspend fun completeIntro(flag: Boolean) {
        onboardingDataSource.completeIntro(flag)
    }

    override suspend fun checkMapOnboardingCompletion(): Boolean {
        return onboardingDataSource.checkMapOnboardingCompletion()
    }

    override suspend fun completeMapOnboarding(flag: Boolean) {
        onboardingDataSource.completeMapOnboarding(flag)
    }

    override suspend fun checkFestivalOnboardingCompletion(): Boolean {
        return onboardingDataSource.checkFestivalOnboardingCompletion()
    }

    override suspend fun completeFestivalOnboarding(flag: Boolean) {
        onboardingDataSource.completeFestivalOnboarding(flag)
    }
}
