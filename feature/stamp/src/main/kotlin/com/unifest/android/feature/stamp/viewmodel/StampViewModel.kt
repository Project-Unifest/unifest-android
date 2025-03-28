package com.unifest.android.feature.stamp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifest.android.core.common.ErrorHandlerActions
import com.unifest.android.core.common.PermissionDialogButtonType
import com.unifest.android.core.common.handleException
import com.unifest.android.core.data.repository.LikedFestivalRepository
import com.unifest.android.core.data.repository.StampRepository
import com.unifest.android.core.model.StampFestivalModel
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
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StampViewModel @Inject constructor(
    private val stampRepository: StampRepository,
    private val likedFestivalRepository: LikedFestivalRepository,
) : ViewModel(), ErrorHandlerActions {
    private val _uiState = MutableStateFlow(StampUiState())
    val uiState: StateFlow<StampUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<StampUiEvent>()
    val uiEvent: Flow<StampUiEvent> = _uiEvent.receiveAsFlow()

    init {
        getStampEnabledFestivals()
        getRecentLikedFestival()
    }

    fun onAction(action: StampUiAction) {
        when (action) {
            is StampUiAction.OnReceiveStampClick -> requestLocationPermission()
            is StampUiAction.OnRefreshClick -> refresh()
            is StampUiAction.OnFindStampBoothClick -> setStampBoothDialogVisible(true)
            is StampUiAction.OnPermissionDialogButtonClick -> handlePermissionDialogButtonClick(action.buttonType)
            is StampUiAction.OnDismiss -> setStampBoothDialogVisible(false)
            is StampUiAction.OnStampBoothItemClick -> navigateToBoothDetail(action.boothId)
            is StampUiAction.OnDropDownMenuClick -> {
                Timber.d("DropDown clicked. current state: ${_uiState.value.isDropDownMenuOpened}")
                if (_uiState.value.isDropDownMenuOpened) {
                    hideDropDownMenu()
                    Timber.d("After hiding: ${_uiState.value.isDropDownMenuOpened}")
                } else {
                    showDropDownMenu()
                    Timber.d("After showing: ${_uiState.value.isDropDownMenuOpened}")
                }
            }

            is StampUiAction.OnDropDownMenuDismiss -> hideDropDownMenu()
            is StampUiAction.OnFestivalSelect -> updateSelectedFestival(action.festival)
        }
    }

    fun getCollectedStamps(isRefresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            if (isRefresh) delay(1000)
            stampRepository.getCollectedStamps()
                .onSuccess { stampRecordList ->
                    _uiState.update {
                        it.copy(collectedStampCount = stampRecordList.size)
                    }
                }.onFailure { exception ->
                    handleException(exception, this@StampViewModel)
                }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private fun getStampEnabledFestivals() {
        viewModelScope.launch {
            stampRepository.getStampEnabledFestivals()
                .onSuccess { stampEnabledFestivalList ->
                    _uiState.update {
                        it.copy(stampEnabledFestivalList = stampEnabledFestivalList.toImmutableList())
                    }
                }.onFailure { exception ->
                    handleException(exception, this@StampViewModel)
                }
        }
    }

    fun getStampEnabledBooths(festivalId: Long) {
        viewModelScope.launch {
            stampRepository.getStampEnabledBooths(festivalId)
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

    fun getRecentLikedFestival() {
        viewModelScope.launch {
            val likedFestival = likedFestivalRepository.getRecentLikedFestival()
            _uiState.update {
                it.copy(
                    selectedFestival = StampFestivalModel(festivalId = likedFestival.festivalId, name = likedFestival.schoolName),
                )
            }
        }
    }

    private fun setStampBoothDialogVisible(flag: Boolean) {
        _uiState.update {
            it.copy(isStampBoothDialogVisible = flag)
        }
    }

    private fun refresh() {
        getCollectedStamps(isRefresh = true)
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

    private fun showDropDownMenu() {
        _uiState.update {
            it.copy(isDropDownMenuOpened = true)
        }
    }

    private fun hideDropDownMenu() {
        _uiState.update {
            it.copy(isDropDownMenuOpened = false)
        }
    }

    private fun updateSelectedFestival(festival: StampFestivalModel) {
        if (_uiState.value.selectedFestival == festival) return

        _uiState.update {
            it.copy(
                selectedFestival = festival,
                isDropDownMenuOpened = false,
            )
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
