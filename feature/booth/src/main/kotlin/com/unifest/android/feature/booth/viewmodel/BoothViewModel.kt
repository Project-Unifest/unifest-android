package com.unifest.android.feature.booth.viewmodel

import androidx.lifecycle.ViewModel
import com.unifest.android.core.common.ErrorHandlerActions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BoothViewModel @Inject constructor() : ViewModel(), ErrorHandlerActions {
    private val _uiState: MutableStateFlow<BoothUiState> = MutableStateFlow(BoothUiState())
    val uiState: StateFlow<BoothUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<BoothUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAction(action: BoothUiAction) {
        when (action) {
            is BoothUiAction.OnBoothItemClick -> navigateToBoothDetail(action.boothId)
            is BoothUiAction.OnWaitingCheckBoxClick -> setWaitingAvailabilityChecked()
            is BoothUiAction.OnRetryClick -> refresh(action.error)
        }
    }

    private fun setWaitingAvailabilityChecked() {}

    private fun navigateToBoothDetail(boothId: Long) {}

    override fun setServerErrorDialogVisible(flag: Boolean) {
        _uiState.update {
            it.copy(isServerErrorDialogVisible = flag)
        }
    }

    override fun setNetworkErrorDialogVisible(flag: Boolean) {
        _uiState.update {
            it.copy(isNetworkErrorDialogVisible = flag)
        }
    }

    private fun refresh(error: ErrorType) {
        // TODO: API 호출 로직 추가
        when (error) {
            ErrorType.NETWORK -> setNetworkErrorDialogVisible(false)
            ErrorType.SERVER -> setServerErrorDialogVisible(false)
        }
    }
}
