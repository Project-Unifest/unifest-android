package com.unifest.android.feature.menu.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.core.model.LikedBoothModel
import com.unifest.android.feature.menu.viewmodel.MenuUiState
import kotlinx.collections.immutable.persistentListOf

internal class MenuPreviewParameterProvider : PreviewParameterProvider<MenuUiState> {
    override val values = sequenceOf(
        MenuUiState(
            likedFestivals = persistentListOf(
                FestivalModel(
                    1,
                    1,
                    "https://picsum.photos/36",
                    "서울대학교",
                    "서울",
                    "설대축제 설대축제 설대축제",
                    "2024-04-21",
                    "2024-04-23",
                    126.957f,
                    37.460f,
                ),
                FestivalModel(
                    2,
                    2,
                    "https://picsum.photos/36",
                    "연세대학교",
                    "서울",
                    "연대축제",
                    "2024-04-21",
                    "2024-04-23",
                    126.957f,
                    37.460f,
                ),
            ),
            likedBooths = persistentListOf(
                LikedBoothModel(
                    id = 1,
                    name = "부스 이름",
                    category = "부스 카테고리",
                    description = "부스 설명",
                    location = "부스 위치",
                    warning = "학과 전용 부스",
                ),
                LikedBoothModel(
                    id = 2,
                    name = "부스 이름",
                    category = "부스 카테고리",
                    description = "부스 설명",
                    location = "부스 위치",
                    warning = "학과 전용 부스",
                ),
            ),
        ),
    )
}
