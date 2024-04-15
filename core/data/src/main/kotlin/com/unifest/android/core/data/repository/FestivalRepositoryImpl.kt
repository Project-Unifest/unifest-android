package com.unifest.android.core.data.repository

import com.unifest.android.core.network.service.UnifestService
import com.unifest.android.core.data.util.runSuspendCatching
import com.unifest.android.core.network.response.FestivalSearchResponse
import javax.inject.Inject

class FestivalRepositoryImpl @Inject constructor(
    private val service: UnifestService,
) : FestivalRepository {
    override suspend fun getAllFestivals() = runSuspendCatching {
        service.getAllFestivals()
    }

    override suspend fun searchFestival(name: String) = runSuspendCatching {
        service.searchFestival(name)
    }
}
