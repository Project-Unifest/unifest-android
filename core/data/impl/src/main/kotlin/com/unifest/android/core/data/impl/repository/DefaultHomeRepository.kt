package com.unifest.android.core.data.impl.repository

import com.unifest.android.core.data.api.repository.HomeRepository
import com.unifest.android.core.data.mapper.toModel
import com.unifest.android.core.data.util.runSuspendCatching
import com.unifest.android.core.model.HomeInfoModel
import com.unifest.android.core.network.service.UnifestService
import javax.inject.Inject

class DefaultHomeRepository @Inject constructor(
    private val service: UnifestService,
) : HomeRepository {
    override suspend fun getHomeInfo(): Result<HomeInfoModel> = runSuspendCatching {
        service.getHomeInfo().data.toModel()
    }

}
