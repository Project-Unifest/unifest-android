package com.unifest.android.feature.booth.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.unifest.android.core.model.BoothDetailModel
import com.unifest.android.core.model.MenuModel

internal class BoothDetailUiStatePreviewParameterProvider: PreviewParameterProvider<BoothDetailModel> {
    override val values = sequenceOf(
        BoothDetailModel(
            id = 0L,
            name = "컴공 주점",
            category = "컴퓨터공학부 전용 부스",
            description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
            warning = "",
            location = "청심대 앞",
            latitude = 37.54224856023523f,
            longitude = 127.07605430700158f,
            menus = listOf(
                MenuModel(1L, "모둠 사시미", 45000, ""),
                MenuModel(2L, "모둠 사시미", 45000, ""),
                MenuModel(3L, "모둠 사시미", 45000, ""),
                MenuModel(4L, "모둠 사시미", 45000, ""),
            ),
        )
    )
}
