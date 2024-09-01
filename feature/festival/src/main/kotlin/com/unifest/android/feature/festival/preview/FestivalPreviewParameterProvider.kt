package com.unifest.android.feature.festival.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.feature.festival.viewmodel.FestivalUiState
import kotlinx.collections.immutable.persistentListOf

class FestivalPreviewParameterProvider : PreviewParameterProvider<FestivalUiState> {
    override val values = sequenceOf(
        FestivalUiState(
            festivals = persistentListOf(
                FestivalModel(
                    1,
                    1,
                    "https://picsum.photos/36",
                    "서울대학교",
                    "서울",
                    "설대축제",
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
                FestivalModel(
                    3,
                    3,
                    "https://picsum.photos/36",
                    "고려대학교",
                    "서울",
                    "고대축제",
                    "2024-04-21",
                    "2024-04-23",
                    126.957f,
                    37.460f,
                ),
                FestivalModel(
                    4,
                    4,
                    "https://picsum.photos/36",
                    "성균관대학교",
                    "서울",
                    "성대축제",
                    "2024-04-21",
                    "2024-04-23",
                    126.957f,
                    37.460f,
                ),
                FestivalModel(
                    5,
                    5,
                    "https://picsum.photos/36",
                    "건국대학교",
                    "서울",
                    "건대축제",
                    "2024-04-21",
                    "2024-04-23",
                    126.957f,
                    37.460f,
                ),
            ),
            likedFestivals = persistentListOf(
                FestivalModel(
                    1,
                    1,
                    "https://picsum.photos/36",
                    "서울대학교",
                    "서울",
                    "설대축제",
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
                FestivalModel(
                    3,
                    3,
                    "https://picsum.photos/36",
                    "고려대학교",
                    "서울",
                    "고대축제",
                    "2024-04-21",
                    "2024-04-23",
                    126.957f,
                    37.460f,
                ),
                FestivalModel(
                    4,
                    4,
                    "https://picsum.photos/36",
                    "성균관대학교",
                    "서울",
                    "성대축제",
                    "2024-04-21",
                    "2024-04-23",
                    126.957f,
                    37.460f,
                ),
                FestivalModel(
                    5,
                    5,
                    "https://picsum.photos/36",
                    "건국대학교",
                    "서울",
                    "건대축제",
                    "2024-04-21",
                    "2024-04-23",
                    126.957f,
                    37.460f,
                ),
            ),
        ),
    )
}