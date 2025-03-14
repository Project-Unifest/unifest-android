package com.unifest.android.feature.stamp.viewmodel

import com.unifest.android.core.model.StampBoothModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class StampUiState(
    val isLoading: Boolean = false,
    val stampAvailableSchools: ImmutableList<School> = persistentListOf(
        School(1, "한국교통대학교"),
        School(2, "서울시립대학교"),
        School(3, "한양대학교"),
        School(4, "고려대학교"),
        School(5, "홍익대학교"),
    ),
    val selectedSchool: School = School(1, "한국교통대학교"),
    val collectedStampCount: Int = 0,
    val enabledStampCount: Int = 0,
    val stampBoothList: ImmutableList<StampBoothModel> = persistentListOf(),
    val isStampBoothDialogVisible: Boolean = false,
    val isPermissionDialogVisible: Boolean = false,
    val isServerErrorDialogVisible: Boolean = false,
    val isNetworkErrorDialogVisible: Boolean = false,
    val isDropDownMenuOpened: Boolean = false,
)

data class School(
    val id: Int,
    val name: String,
)
