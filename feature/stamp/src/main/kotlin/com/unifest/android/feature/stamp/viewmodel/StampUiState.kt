package com.unifest.android.feature.stamp.viewmodel

import com.unifest.android.core.model.StampBoothModel
import com.unifest.android.core.model.StampFestivalModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class StampUiState(
    val isLoading: Boolean = false,
    val stampEnabledFestivalList: ImmutableList<StampFestivalModel> = persistentListOf(
        StampFestivalModel(1, "한국교통대학교"),
        StampFestivalModel(2, "서울시립대학교"),
        StampFestivalModel(3, "한양대학교"),
        StampFestivalModel(4, "고려대학교"),
        StampFestivalModel(5, "홍익대학교"),
    ),
    val selectedFestival: StampFestivalModel = StampFestivalModel(1, "한국교통대학교"),
    val collectedStampCount: Int = 0,
    val enabledStampCount: Int = 0,
    val stampBoothList: ImmutableList<StampBoothModel> = persistentListOf(),
    val isStampBoothDialogVisible: Boolean = false,
    val isPermissionDialogVisible: Boolean = false,
    val isServerErrorDialogVisible: Boolean = false,
    val isNetworkErrorDialogVisible: Boolean = false,
    val isDropDownMenuOpened: Boolean = false,
)
