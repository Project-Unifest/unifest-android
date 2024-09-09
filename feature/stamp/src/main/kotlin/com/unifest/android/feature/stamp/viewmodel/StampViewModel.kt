package com.unifest.android.feature.waiting.viewmodel

import androidx.lifecycle.ViewModel
import com.unifest.android.core.common.ErrorHandlerActions
import com.unifest.android.core.data.repository.WaitingRepository
import com.unifest.android.feature.stamp.viewmodel.StampUiEvent
import com.unifest.android.feature.stamp.viewmodel.StampUiState
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
class StampViewModel @Inject constructor(
    private val waitingRepository: WaitingRepository,
) : ViewModel(), ErrorHandlerActions {
    private val _uiState = MutableStateFlow(StampUiState())
    val uiState: StateFlow<StampUiState> = _uiState.asStateFlow()
    private val _uiEvent = Channel<StampUiEvent>()
    val uiEvent: Flow<StampUiEvent> = _uiEvent.receiveAsFlow()

    fun onAction(action: StampUiAction) {
        when (action) {
            is StampUiAction.OnPullToRefresh -> setNetworkErrorDialogVisible(false)
            is StampUiAction.OnStampCancelDialogCancelClick -> setWaitingCancelDialogVisible(false)
            else -> {}
        }
    }

    private fun setWaitingCancelDialogVisible(flag: Boolean) {
        _uiState.update {
            it.copy(isWaitingCancelDialogVisible = flag)
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
