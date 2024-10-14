package com.unifest.android.feature.stamp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifest.android.core.common.ErrorHandlerActions
import com.unifest.android.core.common.PermissionDialogButtonType
import com.unifest.android.core.common.handleException
import com.unifest.android.core.data.repository.StampRepository
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
class StampViewModel @Inject constructor(
    private val stampRepository: StampRepository,
) : ViewModel(), ErrorHandlerActions {
    private val _uiState = MutableStateFlow(StampUiState())
    val uiState: StateFlow<StampUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<StampUiEvent>()
    val uiEvent: Flow<StampUiEvent> = _uiEvent.receiveAsFlow()

    init {
        getStampEnabledBoothList()
    }

    fun onAction(action: StampUiAction) {
        when (action) {
            is StampUiAction.OnReceiveStampClick -> requestLocationPermission()
            is StampUiAction.OnRefreshClick -> refresh()
            is StampUiAction.OnFindStampBoothClick -> setStampBoothDialogVisible(true)
            is StampUiAction.OnPermissionDialogButtonClick -> handlePermissionDialogButtonClick(action.buttonType)
            is StampUiAction.OnDismiss -> setStampBoothDialogVisible(false)
            is StampUiAction.OnStampBoothItemClick -> navigateToBoothDetail(action.boothId)
        }
    }

    fun getCollectedStampCount() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            stampRepository.getCollectedStampCount()
                .onSuccess { collectedStampCount ->
                    _uiState.update {
                        it.copy(collectedStampCount = collectedStampCount)
                    }
                }.onFailure { exception ->
                    handleException(exception, this@StampViewModel)
                }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private fun getStampEnabledBoothList() {
        viewModelScope.launch {
            stampRepository.getStampEnabledBoothList()
                .onSuccess { stampEnabledBoothList ->
                    _uiState.update {
                        it.copy(
                            enabledStampCount = stampEnabledBoothList.size,
                            stampBoothList = stampEnabledBoothList.toImmutableList(),
                        )
                    }
                }.onFailure { exception ->
                    handleException(exception, this@StampViewModel)
                }
        }
    }

    private fun setStampBoothDialogVisible(flag: Boolean) {
        _uiState.update {
            it.copy(isStampBoothDialogVisible = flag)
        }
    }

    private fun refresh() {
        getCollectedStampCount()
    }

    private fun requestLocationPermission() {
        viewModelScope.launch {
            _uiEvent.send(StampUiEvent.RequestCameraPermission)
        }
    }

    private fun navigateToQRScan() {
        viewModelScope.launch {
            _uiEvent.send(StampUiEvent.NavigateToQRScan)
        }
    }

    fun onPermissionResult(isGranted: Boolean) {
        if (isGranted) {
            setPermissionDialogVisible(false)
            navigateToQRScan()
        } else {
            setPermissionDialogVisible(true)
        }
    }

    private fun handlePermissionDialogButtonClick(buttonType: PermissionDialogButtonType) {
        when (buttonType) {
            PermissionDialogButtonType.DISMISS -> {
                setPermissionDialogVisible(false)
            }

            PermissionDialogButtonType.NAVIGATE_TO_APP_SETTING -> {
                viewModelScope.launch {
                    _uiEvent.send(StampUiEvent.NavigateToAppSetting)
                }
            }

            PermissionDialogButtonType.CONFIRM -> {
                setPermissionDialogVisible(false)
                viewModelScope.launch {
                    _uiEvent.send(StampUiEvent.RequestCameraPermission)
                }
            }
        }
    }

    private fun setPermissionDialogVisible(flag: Boolean) {
        _uiState.update {
            it.copy(isPermissionDialogVisible = flag)
        }
    }

    private fun navigateToBoothDetail(boothId: Long) {
        viewModelScope.launch {
            _uiEvent.send(StampUiEvent.NavigateToBoothDetail(boothId))
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
