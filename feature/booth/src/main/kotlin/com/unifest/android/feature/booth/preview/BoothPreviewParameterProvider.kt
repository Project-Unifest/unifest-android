package com.unifest.android.feature.booth.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.unifest.android.core.model.BoothTabModel
import com.unifest.android.feature.booth.viewmodel.BoothUiState
import kotlinx.collections.immutable.persistentListOf

internal class BoothPreviewParameterProvider : PreviewParameterProvider<BoothUiState> {
    override val values: Sequence<BoothUiState> = sequenceOf(
        BoothUiState(
            campusName = "가천대학교 글로컬 캠퍼스",
            totalBoothCount = 3,
            waitingAvailabilityChecked = false,
            boothList = persistentListOf(
                BoothTabModel(
                    id = 1,
                    name = "컴공 주점 부스",
                    location = "학생회관 앞",
                    description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다",
                    waitingEnabled = false,
                    thumbnail = "https://cdn.pixabay.com/photo/2020/06/07/13/33/fireworks-5270439_1280.jpg",
                ),
                BoothTabModel(
                    id = 2,
                    name = "컴공 주점 부스",
                    location = "학생회관 앞",
                    description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다",
                    waitingEnabled = false,
                    thumbnail = "https://cdn.pixabay.com/photo/2020/06/07/13/33/fireworks-5270439_1280.jpg",
                ),
                BoothTabModel(
                    id = 3,
                    name = "컴공 주점 부스",
                    location = "학생회관 앞",
                    description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다",
                    waitingEnabled = false,
                    thumbnail = "https://cdn.pixabay.com/photo/2020/06/07/13/33/fireworks-5270439_1280.jpg",
                ),
            ),
            isServerErrorDialogVisible = false,
            isNetworkErrorDialogVisible = false,
        ),
    )
}
