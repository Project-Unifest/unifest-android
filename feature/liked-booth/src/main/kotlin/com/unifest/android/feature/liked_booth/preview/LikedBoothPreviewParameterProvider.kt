package com.unifest.android.feature.liked_booth.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.unifest.android.core.model.LikedBoothModel
import com.unifest.android.feature.liked_booth.viewmodel.LikedBoothUiState
import kotlinx.collections.immutable.persistentListOf

internal class LikedBoothPreviewParameterProvider : PreviewParameterProvider<LikedBoothUiState> {
    override val values = sequenceOf(
        LikedBoothUiState(
            likedBooths = persistentListOf(
                LikedBoothModel(
                    id = 1L,
                    name = "부스 이름",
                    category = "음식",
                    description = "부스 설명",
                    location = "부스 위치",
                    warning = "학과 전용 부스",
                ),
                LikedBoothModel(
                    id = 2L,
                    name = "부스 이름",
                    category = "음식",
                    description = "부스 설명",
                    location = "부스 위치",
                    warning = "학과 전용 부스",
                ),
                LikedBoothModel(
                    id = 3L,
                    name = "부스 이름",
                    category = "음식",
                    description = "부스 설명",
                    location = "부스 위치",
                    warning = "학과 전용 부스",
                ),
                LikedBoothModel(
                    id = 4L,
                    name = "부스 이름",
                    category = "음식",
                    description = "부스 설명",
                    location = "부스 위치",
                    warning = "학과 전용 부스",
                ),
                LikedBoothModel(
                    id = 5L,
                    name = "부스 이름",
                    category = "음식",
                    description = "부스 설명",
                    location = "부스 위치",
                    warning = "학과 전용 부스",
                ),
                LikedBoothModel(
                    id = 6L,
                    name = "부스 이름",
                    category = "음식",
                    description = "부스 설명",
                    location = "부스 위치",
                    warning = "학과 전용 부스",
                ),
            ),
        ),
    )
}
