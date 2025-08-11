package com.unifest.android.feature.home.viewmodel

import androidx.compose.foundation.text.input.TextFieldState
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.core.model.FestivalTodayModel
import com.unifest.android.core.model.HomeCardModel
import com.unifest.android.core.model.HomeTipModel
import com.unifest.android.core.model.StarInfoModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate

data class HomeUiState(
    val incomingFestivals: ImmutableList<FestivalModel> = persistentListOf(),
    val todayFestivals: ImmutableList<FestivalTodayModel> = persistentListOf(),
    val allFestivals: ImmutableList<FestivalModel> = persistentListOf(),
    val festivalSearchText: TextFieldState = TextFieldState(),
    val likedFestivals: ImmutableList<FestivalModel> = persistentListOf(),
    val deleteSelectedFestival: FestivalModel? = null,
    val selectedDate: LocalDate = LocalDate.now(),
    val isServerErrorDialogVisible: Boolean = false,
    val isNetworkErrorDialogVisible: Boolean = false,
    val isStarImageClicked: ImmutableList<ImmutableList<Boolean>> = persistentListOf(persistentListOf()),
    val isWeekMode: Boolean = true,
    val isDataReady: Boolean = true,
    val isStarImageDialogVisible: Boolean = false,
    val selectedStar: StarInfoModel? = null,
    val cardNews: ImmutableList<HomeCardModel> = persistentListOf(
        // 더미데이터
        HomeCardModel(
            id = 1L,
            thumbnailImgUrl = "https://cdn.pixabay.com/photo/2025/07/22/22/21/iceberg-9729316_1280.jpg",
            detailImgUrl = "https://cdn.pixabay.com/photo/2025/07/22/22/21/iceberg-9729316_1280.jpg",
        ),
    ),
    val tips: ImmutableList<HomeTipModel> = persistentListOf(),
)
