package com.unifest.android.feature.waiting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifest.android.core.data.api.repository.WaitingRepository
import com.unifest.android.core.common.ErrorHandlerActions
import com.unifest.android.core.common.handleException
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
        getMyWaitingList(isRefresh = false)
    }

    fun onWaitingUiAction(action: WaitingUiAction) {
        when (action) {
            is WaitingUiAction.OnCancelWaitingClick -> setWaitingCancelDialogWaitingId(action.waitingId)
            is WaitingUiAction.OnCancelNoShowWaitingClick -> setNoShowWaitingCancelDialogWaitingId(action.waitingId)
            is WaitingUiAction.OnCheckBoothDetailClick -> navigateToBoothDetail(action.boothId)
            is WaitingUiAction.OnWaitingCancelDialogCancelClick -> setWaitingCancelDialogVisible(false)
            is WaitingUiAction.OnWaitingCancelDialogConfirmClick -> cancelBoothWaiting()
            is WaitingUiAction.OnNoShowWaitingCancelDialogCancelClick -> setNoShowWaitingCancelDialogVisible(false)
            is WaitingUiAction.OnNoShowWaitingCancelDialogConfirmClick -> cancelBoothNoShowWaiting()
            is WaitingUiAction.OnLookForBoothClick -> navigateToMap()
            is WaitingUiAction.OnRefresh -> getMyWaitingList(true)
            is WaitingUiAction.OnRetryClick -> refresh(action.error)
        }
    }

    fun getMyWaitingList(isRefresh: Boolean) {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            if (isRefresh) delay(1000)
            waitingRepository.getMyWaitingList()
                .onSuccess { waitingLists ->
                    _uiState.update {
                        it.copy(myWaitingList = waitingLists.toImmutableList())
                    }
                }
                .onFailure { exception ->
                    handleException(exception, this@WaitingViewModel)
                }
            _uiState.update {
                it.copy(isLoading = false)
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
                    getMyWaitingList(false)
                    setWaitingCancelDialogVisible(false)
                }
                .onFailure { exception ->
                    setWaitingCancelDialogVisible(false)
                    handleException(exception, this@WaitingViewModel)
                }
        }
    }

    private fun cancelBoothNoShowWaiting() {
        viewModelScope.launch {
            waitingRepository.cancelBoothWaiting(_uiState.value.waitingCancelDialogWaitingId)
                .onSuccess {
                    getMyWaitingList(false)
                    setNoShowWaitingCancelDialogVisible(false)
                }
                .onFailure { exception ->
                    setNoShowWaitingCancelDialogVisible(false)
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

    private fun refresh(error: ErrorType) {
        getMyWaitingList(false)
        when (error) {
            ErrorType.NETWORK -> setNetworkErrorDialogVisible(false)
            ErrorType.SERVER -> setServerErrorDialogVisible(false)
        }
    }
}
