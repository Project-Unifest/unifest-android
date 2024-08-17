package com.unifest.android.feature.waiting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifest.android.core.common.ErrorHandlerActions
import com.unifest.android.core.common.handleException
import com.unifest.android.core.data.repository.WaitingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WaitingViewModel @Inject constructor(
    private val waitingRepository: WaitingRepository,
) : ViewModel(), ErrorHandlerActions {
    private val _uiState = MutableStateFlow(WaitingUiState())
    val uiState: StateFlow<WaitingUiState> = _uiState.asStateFlow()
    private val _uiEvent = Channel<WaitingUiEvent>()
    val uiEvent: Flow<WaitingUiEvent> = _uiEvent.receiveAsFlow()

    init {
        getMyWaitingList()
    }

    fun onWaitingUiAction(action: WaitingUiAction) {
        when (action) {
            is WaitingUiAction.OnCancelWaitingClick -> setWaitingCancelDialogVisible(true)
            is WaitingUiAction.OnCheckBoothDetailClick -> setNetworkErrorDialogVisible(true)
            is WaitingUiAction.OnPullToRefresh -> setNetworkErrorDialogVisible(false)
            is WaitingUiAction.OnWaitingCancelDialogCancelClick -> setWaitingCancelDialogVisible(false)
            is WaitingUiAction.OnWaitingCancelDialogConfirmClick -> setWaitingCancelDialogVisible(false)
        }
    }

    private fun getMyWaitingList() {
        viewModelScope.launch {
            waitingRepository.getMyWaitingList()
                .onSuccess { waitingLists ->
                    _uiState.update {
                        it.copy(myWaitingList = waitingLists.toImmutableList())
                    }
                }
                .onFailure { exception ->
                    handleException(exception, this@WaitingViewModel)
                }
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
