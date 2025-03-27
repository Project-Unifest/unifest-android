package com.unifest.android.core.data.repository

import com.unifest.android.core.data.mapper.toModel
import com.unifest.android.core.data.util.runSuspendCatching
import com.unifest.android.core.network.service.UnifestService
import javax.inject.Inject

internal class DefaultFestivalRepository @Inject constructor(
    private val service: UnifestService,
) : FestivalRepository {
    override suspend fun getAllFestivals() = runSuspendCatching {
        service.getAllFestivals().data.map { it.toModel() }
    }

    override suspend fun searchSchool(name: String) = runSuspendCatching {
        service.searchSchool(name).data.map { it.toModel() }
    }

    override suspend fun searchRegion(region: String) = runSuspendCatching {
        service.searchRegion(region).data.map { it.toModel() }
    }

    override suspend fun getIncomingFestivals() = runSuspendCatching {
        service.getIncomingFestivals().data.map { it.toModel() }
    }

    override suspend fun getTodayFestivals(date: String) = runSuspendCatching {
        service.getTodayFestivals(date).data.map { it.toModel() }
    }
}
