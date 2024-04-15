package com.unifest.android.core.data.repository

import com.unifest.android.core.network.service.UnifestService
import com.unifest.android.core.data.util.runSuspendCatching
import javax.inject.Inject

class BoothRepositoryImpl @Inject constructor(
    private val service: UnifestService,
) : BoothRepository {
    override suspend fun getPopularBooths(festivalId: Long) = runSuspendCatching {
        service.getPopularBooths(festivalId)
    }

    override suspend fun getBoothDetail(boothId: Long) = runSuspendCatching {
        service.getBoothDetail(boothId)
    }
}
