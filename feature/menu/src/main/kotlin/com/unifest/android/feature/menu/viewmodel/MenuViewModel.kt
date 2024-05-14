package com.unifest.android.feature.menu.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifest.android.core.common.ButtonType
import com.unifest.android.core.common.ErrorHandlerActions
import com.unifest.android.core.common.FestivalUiAction
import com.unifest.android.core.common.UiText
import com.unifest.android.core.common.handleException
import com.unifest.android.core.common.utils.matchesSearchText
import com.unifest.android.core.data.repository.BoothRepository
import com.unifest.android.core.data.repository.FestivalRepository
import com.unifest.android.core.data.repository.LikedBoothRepository
import com.unifest.android.core.data.repository.LikedFestivalRepository
import com.unifest.android.core.data.repository.OnboardingRepository
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.core.model.LikedBoothModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
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
    private val festivalRepository: FestivalRepository,
    private val likedFestivalRepository: LikedFestivalRepository,
    private val boothRepository: BoothRepository,
    private val likedBoothRepository: LikedBoothRepository,
    private val onboardingRepository: OnboardingRepository,
) : ViewModel(), ErrorHandlerActions {
    private val _uiState = MutableStateFlow(MenuUiState())
    val uiState: StateFlow<MenuUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<MenuUiEvent>()
    val uiEvent: Flow<MenuUiEvent> = _uiEvent.receiveAsFlow()

    init {
        observeLikedFestivals()
        // observeLikedBooth()
        getAllFestivals()
        checkFestivalOnboardingCompletion()
    }

    fun onMenuUiAction(action: MenuUiAction) {
        when (action) {
            is MenuUiAction.OnLikedFestivalItemClick -> setRecentLikedFestival(action.schoolName)
            is MenuUiAction.OnAddClick -> setFestivalSearchBottomSheetVisible(true)
            is MenuUiAction.OnLikedBoothItemClick -> navigateToBoothDetail(action.boothId)
            is MenuUiAction.OnToggleBookmark -> deleteLikedBooth(action.booth)
            is MenuUiAction.OnShowMoreClick -> navigateToLikedBooth()
            is MenuUiAction.OnContactClick -> navigateToContact()
            is MenuUiAction.OnAdministratorModeClick -> navigateToAdministratorMode()
            is MenuUiAction.OnRetryClick -> refresh(action.error)
        }
    }

    fun onFestivalUiAction(action: FestivalUiAction) {
        when (action) {
            is FestivalUiAction.OnDismiss -> setFestivalSearchBottomSheetVisible(false)
            is FestivalUiAction.OnSearchTextUpdated -> updateSearchText(action.searchText)
            is FestivalUiAction.OnSearchTextCleared -> clearSearchText()
            is FestivalUiAction.OnEnableSearchMode -> setEnableSearchMode(action.flag)
            is FestivalUiAction.OnEnableEditMode -> setEnableEditMode()
            is FestivalUiAction.OnLikedFestivalSelected -> {
                completeFestivalOnboarding()
                setRecentLikedFestival(action.festival.schoolName)
            }

            is FestivalUiAction.OnAddClick -> addLikeFestival(action.festival)
            is FestivalUiAction.OnDeleteIconClick -> {
                _uiState.update {
                    it.copy(deleteSelectedFestival = action.deleteSelectedFestival)
                }
                setLikedFestivalDeleteDialogVisible(true)
            }

            is FestivalUiAction.OnDeleteDialogButtonClick -> handleDeleteDialogButtonClick(action.buttonType)
            is FestivalUiAction.OnTooltipClick -> completeFestivalOnboarding()
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

    private fun getAllFestivals() {
        viewModelScope.launch {
            festivalRepository.getAllFestivals()
                .onSuccess { festivals ->
                    _uiState.update {
                        it.copy(festivals = festivals.toImmutableList())
                    }
                }
                .onFailure { exception ->
                    handleException(exception, this@MenuViewModel)
                }
        }
    }

    fun getLikedBooths() {
        viewModelScope.launch {
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
        }
    }

//    private fun observeLikedBooth() {
//        viewModelScope.launch {
//            likedBoothRepository.getLikedBoothList().collect { likedBoothList ->
//                _uiState.update {
//                    it.copy(
//                        likedBooths = likedBoothList.toImmutableList(),
//                    )
//                }
//            }
//        }
//    }

    private fun checkFestivalOnboardingCompletion() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isFestivalOnboardingCompleted = onboardingRepository.checkFestivalOnboardingCompletion())
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

    private fun handleDeleteDialogButtonClick(buttonType: ButtonType) {
        when (buttonType) {
            ButtonType.CONFIRM -> {
                setLikedFestivalDeleteDialogVisible(false)
                _uiState.value.deleteSelectedFestival?.let { deleteLikedFestival(it) }
            }

            ButtonType.CANCEL -> setLikedFestivalDeleteDialogVisible(false)
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
                    _uiEvent.send(MenuUiEvent.ShowSnackBar(UiText.StringResource(R.string.liked_booth_removed_message)))
                }.onFailure {
                    _uiEvent.send(MenuUiEvent.ShowSnackBar(UiText.StringResource(R.string.liked_booth_removed_failed_message)))
                }
        }
    }

//    private fun deleteLikedBooth(booth: BoothDetailModel) {
//        viewModelScope.launch {
//            boothRepository.likeBooth(booth.id)
//                .onSuccess {
//                    updateLikedBooth(booth)
//                    delay(500)
//                    likedBoothRepository.deleteLikedBooth(booth)
//                    _uiEvent.send(MenuUiEvent.ShowSnackBar(UiText.StringResource(R.string.liked_booth_removed_message)))
//                }.onFailure {
//                    _uiEvent.send(MenuUiEvent.ShowSnackBar(UiText.StringResource(R.string.liked_booth_removed_failed_message)))
//                }
//        }
//    }

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

//    private suspend fun updateLikedBooth(booth: BoothDetailModel) {
//        likedBoothRepository.updateLikedBooth(booth.copy(isLiked = false))
//    }

    private fun updateSearchText(searchText: TextFieldValue) {
        _uiState.update {
            it.copy(
                festivalSearchText = searchText,
                festivalSearchResults = it.festivals.filter { festival ->
                    matchesSearchText(festival, searchText)
                }.toImmutableList(),
            )
        }
    }

    private fun clearSearchText() {
        _uiState.update {
            it.copy(
                festivalSearchText = TextFieldValue(),
                festivalSearchResults = persistentListOf(),
            )
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

    private fun setRecentLikedFestival(schoolName: String) {
        viewModelScope.launch {
            if (schoolName == likedFestivalRepository.getRecentLikedFestival()) {
                // likedFestivalRepository.setRecentLikedFestival(schoolName)
                setFestivalSearchBottomSheetVisible(false)
                _uiEvent.send(MenuUiEvent.NavigateBack)
            } else {
                _uiEvent.send(MenuUiEvent.ShowToast(UiText.StringResource(R.string.menu_interest_festival_snack_bar)))
            }
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

    private fun completeFestivalOnboarding() {
        viewModelScope.launch {
            onboardingRepository.completeFestivalOnboarding(true)
            _uiState.update {
                it.copy(isFestivalOnboardingCompleted = true)
            }
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
