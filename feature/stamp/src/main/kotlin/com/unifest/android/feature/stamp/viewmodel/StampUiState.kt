package com.unifest.android.feature.stamp.viewmodel

import com.unifest.android.core.model.StampBoothModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class StampUiState(
    val isLoading: Boolean = false,
    val schoolName: String = "한국교통대학교",
    val collectedStampCount: Int = 0,
    val enabledStampCount: Int = 0,
    val stampBoothList: ImmutableList<StampBoothModel> = persistentListOf(),
    val isStampBoothDialogVisible: Boolean = false,
    val isPermissionDialogVisible: Boolean = false,
    val isServerErrorDialogVisible: Boolean = false,
    val isNetworkErrorDialogVisible: Boolean = false,
)
