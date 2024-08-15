package com.unifest.android.feature.waiting.viewmodel

data class WaitingUiState(
    val isServerErrorDialogVisible: Boolean = false,
    val isNetworkErrorDialogVisible: Boolean = false,
//    val waitingLists: ImmutableList<WaitingModel> = persistentListOf(),
)
