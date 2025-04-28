package com.unifest.android.feature.stamp.viewmodel.stamp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifest.android.core.common.ErrorHandlerActions
import com.unifest.android.core.common.PermissionDialogButtonType
import com.unifest.android.core.common.handleException
import com.unifest.android.core.data.api.repository.LikedFestivalRepository
import com.unifest.android.core.data.api.repository.StampRepository
import com.unifest.android.core.model.StampFestivalModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
        observeSelectedFestival()
    }

    fun onAction(action: StampUiAction) {
        when (action) {
            is StampUiAction.OnReceiveStampClick -> requestCameraPermission()
            is StampUiAction.OnRefreshClick -> refreshCollectedStamps()
            is StampUiAction.OnFindStampBoothClick -> setStampBoothDialogVisible(true)
            is StampUiAction.OnPermissionDialogButtonClick -> handlePermissionDialogButtonClick(action.buttonType)
            is StampUiAction.OnDismiss -> setStampBoothDialogVisible(false)
            is StampUiAction.OnStampBoothItemClick -> navigateToBoothDetail(action.boothId)
            is StampUiAction.OnDropDownMenuClick -> {
                if (_uiState.value.isDropDownMenuOpened) hideDropDownMenu() else showDropDownMenu()
            }

            is StampUiAction.OnDropDownMenuDismiss -> hideDropDownMenu()
            is StampUiAction.OnFestivalSelect -> updateSelectedFestival(action.festival)
            is StampUiAction.OnRetryClick -> refresh(action.error)
        }
    }

    fun getCollectedStamps(festivalId: Long, isRefresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            if (isRefresh) delay(1000)
            stampRepository.getCollectedStamps(festivalId)
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

    private fun observeSelectedFestival() {
        viewModelScope.launch {
            val stampEnabledFestivalsFlow = _uiState
                .map { it.stampEnabledFestivalList }
                .distinctUntilChanged()

            combine(
                likedFestivalRepository.getRecentLikedFestivalStream(),
                stampEnabledFestivalsFlow,
            ) { recentLikedFestival, stampEnabledFestivals ->
                val matchingFestival = stampEnabledFestivals.find { it.festivalId == recentLikedFestival.festivalId }
                Pair(recentLikedFestival, matchingFestival)
            }.collect { (recentLikedFestival, matchingFestival) ->
                _uiState.update { currentState ->
                    currentState.copy(
                        selectedFestival = matchingFestival ?: StampFestivalModel(
                            festivalId = recentLikedFestival.festivalId,
                            schoolName = recentLikedFestival.schoolName,
                            defaultImgUrl = "",
                            usedImgUrl = "",
                        ),
                    )
                }
            }
        }
    }

    private fun setStampBoothDialogVisible(flag: Boolean) {
        _uiState.update {
            it.copy(isStampBoothDialogVisible = flag)
        }
    }

    private fun refreshCollectedStamps() {
        getCollectedStamps(festivalId = _uiState.value.selectedFestival.festivalId, isRefresh = true)
    }

    private fun requestCameraPermission() {
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

    private fun refresh(error: ErrorType) {
        getStampEnabledFestivals()
        getCollectedStamps(_uiState.value.selectedFestival.festivalId)
        when (error) {
            ErrorType.NETWORK -> setNetworkErrorDialogVisible(false)
            ErrorType.SERVER -> setServerErrorDialogVisible(false)
        }
    }
}
