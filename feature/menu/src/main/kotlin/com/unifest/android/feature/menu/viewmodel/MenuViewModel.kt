package com.unifest.android.feature.menu.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifest.android.core.common.ButtonType
import com.unifest.android.core.common.FestivalUiAction
import com.unifest.android.core.common.UiText
import com.unifest.android.core.data.repository.BoothRepository
import com.unifest.android.core.data.repository.LikedBoothRepository
import com.unifest.android.core.data.repository.LikedFestivalRepository
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.model.BoothDetailModel
import com.unifest.android.core.model.FestivalModel
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

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val likedFestivalRepository: LikedFestivalRepository,
    private val boothRepository: BoothRepository,
    private val likedBoothRepository: LikedBoothRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MenuUiState())
    val uiState: StateFlow<MenuUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<MenuUiEvent>()
    val uiEvent: Flow<MenuUiEvent> = _uiEvent.receiveAsFlow()

    init {
        observeLikedFestivals()
        observeLikedBooth()
    }

    fun onMenuUiAction(action: MenuUiAction) {
        when (action) {
            is MenuUiAction.OnLikedFestivalItemClick -> navigateToMap(action.schoolName)
            is MenuUiAction.OnAddClick -> setFestivalSearchBottomSheetVisible(true)
            is MenuUiAction.OnLikedBoothItemClick -> navigateToBoothDetail(action.boothId)
            is MenuUiAction.OnToggleBookmark -> deleteLikedBooth(action.booth)
            is MenuUiAction.OnShowMoreClick -> navigateToLikedBooth()
            is MenuUiAction.OnContactClick -> navigateToContact()
            is MenuUiAction.OnAdministratorModeClick -> navigateToAdministratorMode()
        }
    }

    fun onFestivalUiAction(action: FestivalUiAction) {
        when (action) {
            is FestivalUiAction.OnDismiss -> setFestivalSearchBottomSheetVisible(false)
            is FestivalUiAction.OnSearchTextUpdated -> updateSearchText(action.text)
            is FestivalUiAction.OnSearchTextCleared -> clearSearchText()
            is FestivalUiAction.OnEnableSearchMode -> setEnableSearchMode(action.flag)
            is FestivalUiAction.OnEnableEditMode -> setEnableEditMode()
            is FestivalUiAction.OnLikedFestivalSelected -> {
                setLikedFestivalDeleteDialogVisible(false)
                navigateToMap(action.festival.schoolName)
            }
            is FestivalUiAction.OnAddClick -> addLikeFestival(action.festival)
            is FestivalUiAction.OnDeleteIconClick -> {
                _uiState.update {
                    it.copy(deleteSelectedFestival = action.deleteSelectedFestival)
                }
                setLikedFestivalDeleteDialogVisible(true)
            }
            is FestivalUiAction.OnDialogButtonClick -> {
                when (action.type) {
                    ButtonType.CONFIRM -> {
                        setLikedFestivalDeleteDialogVisible(false)
                        _uiState.value.deleteSelectedFestival?.let { deleteLikedFestival(it) }
                    }

                    ButtonType.CANCEL -> setLikedFestivalDeleteDialogVisible(false)
                }
            }

            else -> {}
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

    private fun observeLikedBooth() {
        viewModelScope.launch {
            likedBoothRepository.getLikedBoothList().collect { likedBoothList ->
                _uiState.update {
                    it.copy(
                        likedBoothList = likedBoothList.toImmutableList(),
                    )
                }
            }
        }
    }

    private fun navigateToMap(schoolName: String) {
        viewModelScope.launch {
            if (schoolName == likedFestivalRepository.getRecentLikedFestival()) {
                // 현재는 필요 없는 로직
                // likedFestivalRepository.setRecentLikedFestival(schoolName)
                _uiEvent.send(MenuUiEvent.NavigateToMap)
            } else {
                _uiEvent.send(MenuUiEvent.ShowSnackBar(UiText.StringResource(R.string.menu_interest_festival_snack_bar)))
            }
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

    private fun addLikeFestival(festival: FestivalModel) {
        viewModelScope.launch {
            likedFestivalRepository.insertLikedFestivalAtSearch(festival)
        }
    }

    private fun navigateToBoothDetail(boothId: Long) {
        viewModelScope.launch {
            _uiEvent.send(MenuUiEvent.NavigateToBoothDetail(boothId))
        }
    }

    private fun deleteLikedBooth(booth: BoothDetailModel) {
        viewModelScope.launch {
            boothRepository.likeBooth(booth.id)
                .onSuccess {
                    updateLikedBooth(booth)
                    delay(500)
                    likedBoothRepository.deleteLikedBooth(booth)
                    _uiEvent.send(MenuUiEvent.ShowSnackBar(UiText.StringResource(R.string.liked_booth_removed_message)))
                }.onFailure {
                    _uiEvent.send(MenuUiEvent.ShowSnackBar(UiText.StringResource(R.string.liked_booth_removed_failed_message)))
                }
        }
    }

    private suspend fun updateLikedBooth(booth: BoothDetailModel) {
        likedBoothRepository.updateLikedBooth(booth.copy(isLiked = false))
    }

    private fun updateSearchText(text: TextFieldValue) {
        _uiState.update {
            it.copy(festivalSearchText = text)
        }
    }

    private fun clearSearchText() {
        _uiState.update {
            it.copy(festivalSearchText = TextFieldValue())
        }
    }

    private fun setFestivalSearchBottomSheetVisible(flag: Boolean) {
        _uiState.update {
            it.copy(isFestivalSearchBottomSheetVisible = flag)
        }
    }

    private fun setEnableSearchMode(flag: Boolean) {
        _uiState.update {
            it.copy(isSearchMode = flag)
        }
    }

    private fun setEnableEditMode() {
        _uiState.update {
            it.copy(isEditMode = !_uiState.value.isEditMode)
        }
    }

    private fun setLikedFestivalDeleteDialogVisible(flag: Boolean) {
        _uiState.update {
            it.copy(isLikedFestivalDeleteDialogVisible = flag)
        }
    }

    private fun deleteLikedFestival(festival: FestivalModel) {
        viewModelScope.launch {
            likedFestivalRepository.deleteLikedFestival(festival)
        }
    }
}
