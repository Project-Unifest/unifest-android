package com.unifest.android.core.data.impl.repository

import android.content.Context
import com.unifest.android.core.data.api.repository.BoothRepository
import com.unifest.android.core.data.mapper.toModel
import com.unifest.android.core.data.util.runSuspendCatching
import com.unifest.android.core.common.getDeviceId
import com.unifest.android.core.data.mapper.toBoothTabModel
import com.unifest.android.core.datastore.api.TokenDataSource
import com.unifest.android.core.network.request.BoothWaitingRequest
import com.unifest.android.core.network.request.CheckPinValidationRequest
import com.unifest.android.core.network.request.LikeBoothRequest
import com.unifest.android.core.network.service.UnifestService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DefaultBoothRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val service: UnifestService,
    private val tokenDataSource: TokenDataSource,
) : BoothRepository {
    override suspend fun getPopularBooths(festivalId: Long) = runSuspendCatching {
        service.getPopularBooths(festivalId).data.map { it.toModel() }
    }

    override suspend fun getAllBooths(festivalId: Long) = runSuspendCatching {
        service.getAllBooths(festivalId).data.toModel()
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

    override suspend fun getBoothLikes(boothId: Long) = runSuspendCatching {
        service.getBoothLikes(boothId).data
    }

    override suspend fun checkPinValidation(boothId: Long, pinNumber: String): Result<Long> = runSuspendCatching {
        service.checkPinValidation(
            CheckPinValidationRequest(
                boothId = boothId,
                pinNumber = pinNumber,
            ),
        ).data
    }

    override suspend fun requestBoothWaiting(boothId: Long, tel: String, partySize: Long, pinNumber: String) = runSuspendCatching {
        val fcmToken = tokenDataSource.getFCMToken()
        service.requestBoothWaiting(
            BoothWaitingRequest(
                boothId = boothId,
                tel = tel,
                deviceId = getDeviceId(context),
                partySize = partySize,
                pinNumber = pinNumber,
                fcmToken = fcmToken,
            ),
        ).data.toModel()
    }

    override suspend fun getTabBooths(festivalId: Long) = runSuspendCatching {
        service.getAllBooths(
            festivalId = festivalId,
        ).data.booths.map { it.toBoothTabModel() }
    }
}
