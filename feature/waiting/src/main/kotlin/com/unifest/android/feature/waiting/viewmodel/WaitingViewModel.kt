package com.unifest.android.feature.waiting.viewmodel

import androidx.lifecycle.ViewModel
import com.unifest.android.core.common.ErrorHandlerActions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WaitingViewModel @Inject constructor() : ViewModel(), ErrorHandlerActions {
    private val _uiState = MutableStateFlow(WaitingUiState())
    val uiState: StateFlow<WaitingUiState> = _uiState.asStateFlow()
    private val _uiEvent = Channel<WaitingUiEvent>()
    val uiEvent: Flow<WaitingUiEvent> = _uiEvent.receiveAsFlow()

    fun onWaitingUiAction(action: WaitingUiAction) {
        when (action) {
            is WaitingUiAction.OnDismiss -> setServerErrorDialogVisible(false)
            else -> {}
        }
    }

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
}
