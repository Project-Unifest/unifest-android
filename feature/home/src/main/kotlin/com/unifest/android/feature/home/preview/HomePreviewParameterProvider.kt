package com.unifest.android.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.core.model.FestivalTodayModel
import com.unifest.android.core.model.HomeCardModel
import com.unifest.android.feature.home.viewmodel.HomeUiState
import kotlinx.collections.immutable.persistentListOf

internal class HomePreviewParameterProvider : PreviewParameterProvider<HomeUiState> {
    override val values = sequenceOf(
        HomeUiState(
            todayFestivals = persistentListOf(
                FestivalTodayModel(
                    festivalId = 1,
                    beginDate = "2024-04-05",
                    endDate = "2024-04-07",
                    festivalName = "녹색지대 DAY 1",
                    schoolName = "건국대학교 서울캠퍼스",
                    starInfo = listOf(),
                    thumbnail = "https://picsum.photos/36",
                    schoolId = 1,
                ),
                FestivalTodayModel(
                    festivalId = 2,
                    beginDate = "2024-04-05",
                    endDate = "2024-04-07",
                    festivalName = "녹색지대 DAY 1",
                    schoolName = "건국대학교 서울캠퍼스",
                    starInfo = listOf(),
                    thumbnail = "https://picsum.photos/36",
                    schoolId = 2,
                ),
                FestivalTodayModel(
                    festivalId = 3,
                    beginDate = "2024-04-05",
                    endDate = "2024-04-07",
                    festivalName = "녹색지대 DAY 1",
                    schoolName = "건국대학교 서울캠퍼스",
                    starInfo = listOf(),
                    thumbnail = "https://picsum.photos/36",
                    schoolId = 3,
                ),
            ),
            incomingFestivals = persistentListOf(
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
            ),
            cardNews = persistentListOf(
                HomeCardModel(
                    thumbnailImgUrl = "https://example.com/image1.jpg",
                ),
                HomeCardModel(
                    thumbnailImgUrl = "https://example.com/image2.jpg",
                ),
            ),
        ),
    )
}
