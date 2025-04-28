package com.unifest.android.feature.map.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.unifest.android.core.common.PermissionDialogButtonType
import com.unifest.android.feature.map.model.BoothMapModel

sealed interface MapUiAction {
    data object OnTooltipClick : MapUiAction
    data class OnSearchTextUpdated(val searchText: TextFieldValue) : MapUiAction
    data object OnSearchTextCleared : MapUiAction
    data class OnSearch(val searchText: TextFieldValue) : MapUiAction
    data class OnBoothMarkerClick(val booths: List<BoothMapModel>) : MapUiAction

    data class OnSingleBoothMarkerClick(val booth: BoothMapModel) : MapUiAction
    data object OnTogglePopularBooth : MapUiAction
    data class OnBoothItemClick(val boothId: Long) : MapUiAction
    data class OnRetryClick(val error: ErrorType) : MapUiAction
    data class OnPermissionDialogButtonClick(
        val buttonType: PermissionDialogButtonType,
        val permission: String,
    ) : MapUiAction

    data class OnBoothTypeChipClick(val chipName: String) : MapUiAction
}

enum class ErrorType {
    NETWORK,
    SERVER,
}
