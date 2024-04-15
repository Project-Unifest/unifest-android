package com.unifest.android.core.data.repository

import com.unifest.android.core.data.mapper.toModel
import com.unifest.android.core.data.util.runSuspendCatching
import com.unifest.android.core.network.service.UnifestService
import javax.inject.Inject

class FestivalRepositoryImpl @Inject constructor(
    private val service: UnifestService,
) : FestivalRepository {
    override suspend fun getAllFestivals() = runSuspendCatching {
        service.getAllFestivals().data.map { it.toModel() }
    }

    override suspend fun searchFestival(name: String) = runSuspendCatching {
        service.searchFestival(name).data.map { it.toModel() }
    }
}
