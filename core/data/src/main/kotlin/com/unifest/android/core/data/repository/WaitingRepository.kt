package com.unifest.android.core.data.repository

import com.unifest.android.core.model.MyWaitingModel

interface WaitingRepository {
    suspend fun getMyWaitingList(): Result<List<MyWaitingModel>>
}
