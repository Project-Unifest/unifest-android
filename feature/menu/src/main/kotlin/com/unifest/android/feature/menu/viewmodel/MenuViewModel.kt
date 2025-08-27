package com.unifest.android.feature.menu.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifest.android.core.data.api.repository.BoothRepository
import com.unifest.android.core.data.api.repository.LikedBoothRepository
import com.unifest.android.core.data.api.repository.LikedFestivalRepository
import com.unifest.android.core.data.api.repository.SettingRepository
import com.unifest.android.core.common.ErrorHandlerActions
import com.unifest.android.core.common.UiText
import com.unifest.android.core.common.handleException
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.core.model.LikedBoothModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
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
import com.unifest.android.core.designsystem.R as designR

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val likedFestivalRepository: LikedFestivalRepository,
    private val boothRepository: BoothRepository,
    private val likedBoothRepository: LikedBoothRepository,
    private val settingRepository: SettingRepository,
) : ViewModel(), ErrorHandlerActions {
    private val _uiState = MutableStateFlow(MenuUiState())
    val uiState: StateFlow<MenuUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<MenuUiEvent>()
    val uiEvent: Flow<MenuUiEvent> = _uiEvent.receiveAsFlow()

    val isClusteringEnabled = settingRepository.flowIsClusteringEnabled()

    init {
        observeLikedFestivals()
    }

    fun onMenuUiAction(action: MenuUiAction) {
        when (action) {
            is MenuUiAction.OnLikedFestivalItemClick -> setRecentLikedFestival(action.festival)
            is MenuUiAction.OnLikedBoothItemClick -> navigateToBoothDetail(action.boothId)
            is MenuUiAction.OnToggleBookmark -> deleteLikedBooth(action.booth)
            is MenuUiAction.OnShowMoreClick -> navigateToLikedBooth()
            is MenuUiAction.OnContactClick -> navigateToContact()
            is MenuUiAction.OnAdministratorModeClick -> navigateToAdministratorMode()
            is MenuUiAction.OnWhoAreUInferClick -> navigateToWhoAreUForm()
            is MenuUiAction.OnRetryClick -> refresh(action.error)
            is MenuUiAction.OnToggleClustering -> updateIsClusteringEnabled(action.isChecked)
        }
    }

    private fun navigateToWhoAreUForm() {
        viewModelScope.launch {
            _uiEvent.send(MenuUiEvent.NavigateToWhoAreUForm)
        }
    }

    private fun observeLikedFestivals() {
        viewModelScope.launch {
            likedFestivalRepository.getLikedFestivals().collect { likedFestivalList ->
                _uiState.update {
                    it.copy(
                        likedFestivals = likedFestivalList.toPersistentList(),
                    )
                }
            }
        }
    }

    fun getLikedBooths() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            likedBoothRepository.getLikedBooths()
                .onSuccess { likedBooths ->
                    _uiState.update {
                        it.copy(
                            likedBooths = likedBooths.toImmutableList(),
                        )
                    }
                }.onFailure { exception ->
                    handleException(exception, this@MenuViewModel)
                }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private fun updateIsClusteringEnabled(checked: Boolean) {
        viewModelScope.launch {
            settingRepository.updateIsClusteringEnabled(checked)
        }
    }

    private fun navigateToLikedBooth() {
        viewModelScope.launch {
            _uiEvent.send(MenuUiEvent.NavigateToLikedBooth)
        }
    }

    private fun navigateToContact() {
        viewModelScope.launch {
            _uiEvent.send(MenuUiEvent.NavigateToContact)
        }
    }

    private fun navigateToAdministratorMode() {
        viewModelScope.launch {
            _uiEvent.send(MenuUiEvent.NavigateToAdministratorMode)
        }
    }

    private fun navigateToBoothDetail(boothId: Long) {
        viewModelScope.launch {
            _uiEvent.send(MenuUiEvent.NavigateToBoothDetail(boothId))
        }
    }

    private fun deleteLikedBooth(booth: LikedBoothModel) {
        viewModelScope.launch {
            boothRepository.likeBooth(booth.id)
                .onSuccess {
                    updateLikedBooth(booth)
                    delay(500)
                    getLikedBooths()
                    _uiEvent.send(MenuUiEvent.ShowSnackBar(UiText.StringResource(designR.string.liked_booth_removed_message)))
                }.onFailure {
                    _uiEvent.send(MenuUiEvent.ShowSnackBar(UiText.StringResource(designR.string.liked_booth_removed_failed_message)))
                }
        }
    }

    private fun updateLikedBooth(booth: LikedBoothModel) {
        _uiState.update {
            it.copy(
                likedBooths = it.likedBooths
                    .map { likedBooth ->
                        if (likedBooth.id == booth.id) {
                            likedBooth.copy(isLiked = false)
                        } else {
                            likedBooth
                        }
                    }.toImmutableList(),
            )
        }
    }

    private fun setRecentLikedFestival(festival: FestivalModel) {
        viewModelScope.launch {
            likedFestivalRepository.setRecentLikedFestival(festival)
            _uiEvent.send(MenuUiEvent.NavigateBack)
        }
    }

    override fun setServerErrorDialogVisible(flag: Boolean) {
        _uiState.update {
            it.copy(isServerErrorDialogVisible = flag)
        }
    }

    override fun setNetworkErrorDialogVisible(flag: Boolean) {
        _uiState.update {
            it.copy(isServerErrorDialogVisible = flag)
        }
    }

    private fun refresh(error: ErrorType) {
        getLikedBooths()
        when (error) {
            ErrorType.NETWORK -> setNetworkErrorDialogVisible(false)
            ErrorType.SERVER -> setServerErrorDialogVisible(false)
        }
    }
}
