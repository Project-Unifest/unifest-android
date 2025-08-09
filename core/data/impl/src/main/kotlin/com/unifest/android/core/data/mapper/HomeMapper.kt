package com.unifest.android.core.data.mapper

import com.unifest.android.core.model.HomeCardModel
import com.unifest.android.core.model.HomeInfoModel
import com.unifest.android.core.model.HomeTipModel
import com.unifest.android.core.network.response.HomeInfoResponse

internal fun HomeInfoResponse.toModel(): HomeInfoModel {
    return HomeInfoModel(
        homeCardList = this.homeCardList.map { card ->
            HomeCardModel(
                id = card.id,
                thumbnailImgUrl = card.thumbnailImgUrl,
                detailImgUrl = card.detailImgUrl,
            )
        },
        homeTipList = this.homeTipList.map { tip ->
            HomeTipModel(
                id = tip.id,
                tipContent = tip.tipContent,
            )
        },
    )
}
