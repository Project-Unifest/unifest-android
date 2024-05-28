package com.unifest.android.feature.waiting.viewmodel

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class WaitingUiState (
    val isServerErrorDialogVisible: Boolean = false,
    val isNetworkErrorDialogVisible: Boolean = false,
//    val waitingLists: ImmutableList<WaitingModel> = persistentListOf(),
)
