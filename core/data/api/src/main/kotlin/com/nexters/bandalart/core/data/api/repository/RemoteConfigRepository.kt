package com.nexters.bandalart.core.data.api.repository

import kotlinx.coroutines.flow.Flow

interface RemoteConfigRepository {
    fun shouldUpdate(): Flow<Boolean>
}
