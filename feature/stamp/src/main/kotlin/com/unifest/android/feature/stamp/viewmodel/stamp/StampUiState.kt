package com.unifest.android.feature.stamp.viewmodel.stamp

import com.unifest.android.core.model.StampBoothModel
import com.unifest.android.core.model.StampFestivalModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class StampUiState(
    val isLoading: Boolean = false,
    val stampEnabledFestivalList: ImmutableList<StampFestivalModel> = persistentListOf(),
    val selectedFestival: StampFestivalModel = StampFestivalModel(0, "", "", ""),
    val collectedStampCount: Int = 0,
    val stampBoothList: ImmutableList<StampBoothModel> = persistentListOf(),
    val stampEnabledBoothList: ImmutableList<StampBoothModel> = persistentListOf(),
    val isStampBoothDialogVisible: Boolean = false,
    val isPermissionDialogVisible: Boolean = false,
    val isServerErrorDialogVisible: Boolean = false,
    val isNetworkErrorDialogVisible: Boolean = false,
    val isDropDownMenuOpened: Boolean = false,
)

val dummyStampEnabledFestivalList = persistentListOf(
    StampFestivalModel(1, "서울시립대", "", ""),
    StampFestivalModel(2, "한국교통대학교", "", ""),
    StampFestivalModel(3, "한양대학교", "", ""),
    StampFestivalModel(4, "고려대학교", "", ""),
    StampFestivalModel(5, "홍익대학교", "", ""),
)

val dummySelectedFestival = StampFestivalModel(2, "한국교통대학교", "", "")
