package com.unifest.android.feature.map.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.feature.map.model.BoothMapModel
import com.unifest.android.feature.map.viewmodel.MapUiState
import kotlinx.collections.immutable.persistentListOf

internal class MapPreviewParameterProvider : PreviewParameterProvider<MapUiState> {
    override val values = sequenceOf(
        MapUiState(
            festivalInfo = FestivalModel(
                schoolName = "건국대학교",
            ),
            boothList = persistentListOf(
                BoothMapModel(
                    id = 0L,
                    name = "컴공 주점",
                    category = "",
                    description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                    location = "청심대 앞",
                ),
                BoothMapModel(
                    id = 1L,
                    name = "컴공 주점",
                    category = "",
                    description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                    location = "청심대 앞",
                ),
                BoothMapModel(
                    id = 2L,
                    name = "컴공 주점",
                    category = "",
                    description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                    location = "청심대 앞",
                ),
                BoothMapModel(
                    id = 3L,
                    name = "컴공 주점",
                    category = "",
                    description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                    location = "청심대 앞",
                ),
                BoothMapModel(
                    id = 4L,
                    name = "컴공 주점",
                    category = "",
                    description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                    location = "청심대 앞",
                ),
            ),
        ),
    )
}
