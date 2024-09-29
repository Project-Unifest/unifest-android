package com.unifest.android.feature.waiting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifest.android.core.common.ErrorHandlerActions
import com.unifest.android.core.common.handleException
import com.unifest.android.core.data.repository.WaitingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
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
            is WaitingUiAction.OnCancelWaitingClick -> setWaitingCancelDialogWaitingId(action.waitingId)
            is WaitingUiAction.OnCancelNoShowWaitingClick -> setNoShowWaitingCancelDialogWaitingId(action.waitingId)
            is WaitingUiAction.OnCheckBoothDetailClick -> navigateToBoothDetail(action.boothId)
            is WaitingUiAction.OnPullToRefresh -> setNetworkErrorDialogVisible(false)
            is WaitingUiAction.OnWaitingCancelDialogCancelClick -> setWaitingCancelDialogVisible(false)
            is WaitingUiAction.OnWaitingCancelDialogConfirmClick -> cancelBoothWaiting()
            is WaitingUiAction.OnNoShowWaitingCancelDialogCancelClick -> setNoShowWaitingCancelDialogVisible(false)
            is WaitingUiAction.OnNoShowWaitingCancelDialogConfirmClick -> cancelBoothWaiting()
            is WaitingUiAction.OnLookForBoothClick -> navigateToMap()
            is WaitingUiAction.OnRefresh -> getMyWaitingList()
        }
    }

    fun getMyWaitingList() {
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

    private fun setWaitingCancelDialogWaitingId(waitingId: Long) {
        setWaitingCancelDialogVisible(true)
        _uiState.update {
            it.copy(waitingCancelDialogWaitingId = waitingId)
        }
    }

    private fun setNoShowWaitingCancelDialogWaitingId(waitingId: Long) {
        setNoShowWaitingCancelDialogVisible(true)
        _uiState.update {
            it.copy(waitingCancelDialogWaitingId = waitingId)
        }
    }

    private fun cancelBoothWaiting() {
        viewModelScope.launch {
            waitingRepository.cancelBoothWaiting(_uiState.value.waitingCancelDialogWaitingId)
                .onSuccess {
                    getMyWaitingList()
                    setWaitingCancelDialogVisible(false)
                }
                .onFailure { exception ->
                    setWaitingCancelDialogVisible(false)
                    handleException(exception, this@WaitingViewModel)
                }
        }
    }

    private fun navigateToBoothDetail(boothId: Long) {
        viewModelScope.launch {
            _uiEvent.send(WaitingUiEvent.NavigateToBoothDetail(boothId))
        }
    }

    private fun navigateToMap() {
        viewModelScope.launch {
            _uiEvent.send(WaitingUiEvent.NavigateToMap)
        }
    }

    private fun setWaitingCancelDialogVisible(flag: Boolean) {
        _uiState.update {
            it.copy(isWaitingCancelDialogVisible = flag)
        }
    }

    private fun setNoShowWaitingCancelDialogVisible(flag: Boolean) {
        _uiState.update {
            it.copy(isNoShowWaitingCancelDialogVisible = flag)
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
