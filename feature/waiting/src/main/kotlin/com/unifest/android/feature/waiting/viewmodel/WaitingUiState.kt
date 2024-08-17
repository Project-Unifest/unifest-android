package com.unifest.android.feature.waiting.viewmodel

import com.unifest.android.core.model.LikedBoothModel
import com.unifest.android.core.model.MyWaitingModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class WaitingUiState(
    val isLoading: Boolean = false,
    val myWaitingList: ImmutableList<MyWaitingModel> = persistentListOf(),
    val isServerErrorDialogVisible: Boolean = false,
    val isNetworkErrorDialogVisible: Boolean = false,
    val isWaitingCancelDialogVisible: Boolean = false,
)
