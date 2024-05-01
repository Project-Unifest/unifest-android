package com.unifest.android.core.data.repository

import com.unifest.android.core.data.mapper.toModel
import com.unifest.android.core.network.service.UnifestService
import com.unifest.android.core.data.util.runSuspendCatching
import javax.inject.Inject

internal class BoothRepositoryImpl @Inject constructor(
    private val service: UnifestService,
) : BoothRepository {
    override suspend fun getPopularBooths(festivalId: Long) = runSuspendCatching {
        service.getPopularBooths(festivalId).data.map { it.toModel() }
    }

    override suspend fun getBoothDetail(boothId: Long) = runSuspendCatching {
        service.getBoothDetail(boothId).data.toModel()
    }
}
