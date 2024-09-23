package com.unifest.android.feature.festival.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifest.android.core.common.ErrorHandlerActions
import com.unifest.android.core.common.UiText
import com.unifest.android.core.common.handleException
import com.unifest.android.core.common.utils.matchesSearchText
import com.unifest.android.core.data.repository.FestivalRepository
import com.unifest.android.core.data.repository.LikedFestivalRepository
import com.unifest.android.core.data.repository.OnboardingRepository
import com.unifest.android.core.designsystem.R as designR
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.feature.festival.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.channels.Channel
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
class FestivalViewModel @Inject constructor(
    private val onboardingRepository: OnboardingRepository,
    private val festivalRepository: FestivalRepository,
    private val likedFestivalRepository: LikedFestivalRepository,
) : ViewModel(), ErrorHandlerActions {
    private val _uiState = MutableStateFlow(FestivalUiState())
    val uiState: StateFlow<FestivalUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<FestivalUiEvent>()
    val uiEvent: Flow<FestivalUiEvent> = _uiEvent.receiveAsFlow()

    init {
        getAllFestivals()
        checkFestivalOnboardingCompletion()
        observeLikedFestivals()
    }

    fun onFestivalUiAction(action: FestivalUiAction) {
        when (action) {
            is FestivalUiAction.OnAddLikedFestivalClick -> setFestivalSearchBottomSheetVisible(true)
            is FestivalUiAction.OnDismiss -> setFestivalSearchBottomSheetVisible(false)
            is FestivalUiAction.OnSearchTextUpdated -> updateFestivalSearchText(action.searchText)
            is FestivalUiAction.OnSearchTextCleared -> clearFestivalSearchText()
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
                    it.copy(likedFestivals = likedFestivalList.toPersistentList())
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
                    handleException(exception, this@FestivalViewModel)
                }
        }
    }

    private fun updateFestivalSearchText(searchText: TextFieldValue) {
        _uiState.update {
            it.copy(
                festivalSearchText = searchText,
                festivalSearchResults = it.festivals.filter { festival ->
                    matchesSearchText(festival, searchText)
                }.toImmutableList(),
            )
        }
    }

    private fun clearFestivalSearchText() {
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

    private fun setRecentLikedFestival(schoolName: String) {
        viewModelScope.launch {
            if (schoolName == likedFestivalRepository.getRecentLikedFestival()) {
                // likedFestivalRepository.setRecentLikedFestival(schoolName)
                setFestivalSearchBottomSheetVisible(false)
                _uiEvent.send(FestivalUiEvent.NavigateBack)
            } else {
                _uiEvent.send(FestivalUiEvent.ShowToast(UiText.StringResource(designR.string.interest_festival_snack_bar)))
            }
        }
    }

    private fun addLikeFestival(festival: FestivalModel) {
        viewModelScope.launch {
            likedFestivalRepository.registerLikedFestival()
                .onSuccess {
                    likedFestivalRepository.insertLikedFestivalAtSearch(festival)
                    _uiEvent.send(FestivalUiEvent.ShowToast(UiText.StringResource(R.string.liked_festival_saved_message)))
                }
                .onFailure { exception ->
                    _uiEvent.send(FestivalUiEvent.ShowToast(UiText.StringResource(R.string.liked_festival_saved_failed_message)))
                }
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

    private fun deleteLikedFestival(festival: FestivalModel) {
        viewModelScope.launch {
            likedFestivalRepository.unregisterLikedFestival()
                .onSuccess {
                    likedFestivalRepository.deleteLikedFestival(festival)
                    _uiEvent.send(FestivalUiEvent.ShowToast(UiText.StringResource(R.string.liked_festival_removed_message)))
                }
                .onFailure { exception ->
                    _uiEvent.send(FestivalUiEvent.ShowToast(UiText.StringResource(R.string.liked_festival_removed_failed_message)))
                    Timber.e(exception)
                }
        }
    }

    private fun checkFestivalOnboardingCompletion() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isFestivalOnboardingCompleted = onboardingRepository.checkFestivalOnboardingCompletion())
            }
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
}
