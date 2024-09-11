package com.unifest.android.feature.stamp.viewmodel

sealed interface StampUiEvent {
    data object NavigateBack : StampUiEvent
    data object NavigateToQRScan : StampUiEvent
}
