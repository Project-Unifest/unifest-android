package com.unifest.android.core.data.mapper

import com.unifest.android.core.model.MyWaitingModel
import com.unifest.android.core.network.response.MyWaiting

internal fun MyWaiting.toModel(): MyWaitingModel {
    return MyWaitingModel(
        boothId = boothId,
        waitingId = waitingId,
        partySize = partySize,
        tel = tel,
        deviceId = deviceId,
        createdAt = createdAt,
        updatedAt = updatedAt,
        status = status,
        waitingOrder = waitingOrder ?: 0L,
        boothName = boothName,
    )
}
