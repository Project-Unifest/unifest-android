package com.unifest.android.feature.stamp.viewmodel

import com.unifest.android.core.model.MyWaitingModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class StampUiState(
    val isLoading: Boolean = false,
    val stampList: ImmutableList<MyWaitingModel> = persistentListOf(),
    val isWaitingCancelDialogVisible: Boolean = false,
    val isServerErrorDialogVisible: Boolean = false,
    val isNetworkErrorDialogVisible: Boolean = false,
)
