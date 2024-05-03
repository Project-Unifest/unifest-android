package com.unifest.android.core.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import com.unifest.android.core.data.mapper.toModel
import com.unifest.android.core.data.util.runSuspendCatching
import com.unifest.android.core.network.request.LikeBoothRequest
import com.unifest.android.core.network.service.UnifestService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BoothRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val service: UnifestService,
) : BoothRepository {
    override suspend fun getPopularBooths(festivalId: Long) = runSuspendCatching {
        service.getPopularBooths(festivalId).data.map { it.toModel() }
    }

    override suspend fun getAllBooths(festivalId: Long) = runSuspendCatching {
        service.getAllBooths(festivalId).data.map { it.toModel() }
    }

    override suspend fun getBoothDetail(boothId: Long) = runSuspendCatching {
        service.getBoothDetail(boothId).data.toModel()
    }

    override suspend fun likeBooth(boothId: Long): Result<Unit> = runSuspendCatching {
        service.likeBooth(
            LikeBoothRequest(
                boothId = boothId,
                token = getDeviceId(context),
            ),
        )
    }

    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }
}
