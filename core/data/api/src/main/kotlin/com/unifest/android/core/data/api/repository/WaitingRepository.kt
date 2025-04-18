package com.unifest.android.core.data.api.repository

import com.unifest.android.core.model.MyWaitingModel

interface WaitingRepository {
    suspend fun getMyWaitingList(): Result<List<MyWaitingModel>>
    suspend fun cancelBoothWaiting(waitingId: Long): Result<Unit>
    suspend fun registerFCMTopic(waitingId: String)
}
