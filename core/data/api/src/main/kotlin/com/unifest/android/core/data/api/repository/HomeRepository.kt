package com.unifest.android.core.data.api.repository

import com.unifest.android.core.model.HomeInfoModel

interface HomeRepository {
    suspend fun getHomeInfo(): Result<HomeInfoModel>
}
