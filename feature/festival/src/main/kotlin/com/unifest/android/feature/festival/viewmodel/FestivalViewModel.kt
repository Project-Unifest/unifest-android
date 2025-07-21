package com.unifest.android.feature.festival.viewmodel

import androidx.compose.foundation.text.input.clearText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifest.android.core.common.ErrorHandlerActions
import com.unifest.android.core.common.UiText
import com.unifest.android.core.common.handleException
import com.unifest.android.core.data.api.repository.FestivalRepository
import com.unifest.android.core.data.api.repository.LikedFestivalRepository
import com.unifest.android.core.data.api.repository.OnboardingRepository
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.feature.festival.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Job
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

    private var addLikedFestivalJob: Job? = null

    init {
        getAllFestivals()
        checkFestivalOnboardingCompletion()
        observeLikedFestivals()
    }

    fun onFestivalUiAction(action: FestivalUiAction) {
        when (action) {
            is FestivalUiAction.OnAddLikedFestivalClick -> setFestivalSearchBottomSheetVisible(true)
            is FestivalUiAction.OnDismiss -> setFestivalSearchBottomSheetVisible(false)
            is FestivalUiAction.OnSearchTextCleared -> clearFestivalSearchText()
            is FestivalUiAction.OnEnableSearchMode -> setEnableSearchMode(action.flag)
            is FestivalUiAction.OnEnableEditMode -> setEnableEditMode()
            is FestivalUiAction.OnLikedFestivalSelected -> {
                completeFestivalOnboarding()
                setRecentLikedFestival(action.festival)
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
                    Timber.e(exception)
                }
        }
    }

    private fun clearFestivalSearchText() {
        _uiState.value.festivalSearchText.clearText()
        _uiState.update { it.copy(festivalSearchResults = persistentListOf()) }
    }

    private fun setFestivalSearchBottomSheetVisible(flag: Boolean) {
        _uiState.update {
            it.copy(isFestivalSearchBottomSheetVisible = flag)
        }
    }

    private fun setRecentLikedFestival(festival: FestivalModel) {
        viewModelScope.launch {
            likedFestivalRepository.setRecentLikedFestival(festival)
            setFestivalSearchBottomSheetVisible(false)
            _uiEvent.send(FestivalUiEvent.NavigateBack)
        }
    }

    private fun addLikeFestival(festival: FestivalModel) {
        if (addLikedFestivalJob != null && addLikedFestivalJob?.isActive == true) {
            Timber.d("addLikedFestivalJob is Active")
            return
        }

        addLikedFestivalJob = viewModelScope.launch {
            try {
                likedFestivalRepository.registerLikedFestival(festival)
                    .onSuccess {
                        likedFestivalRepository.insertLikedFestivalAtSearch(festival)
                        _uiEvent.send(FestivalUiEvent.ShowToast(UiText.StringResource(R.string.liked_festival_saved_message)))
                    }
                    .onFailure { exception ->
                        _uiEvent.send(FestivalUiEvent.ShowToast(UiText.StringResource(R.string.liked_festival_saved_failed_message)))
                        Timber.e(exception)
                    }
            } finally {
                addLikedFestivalJob = null
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
            if (_uiState.value.likedFestivals.size <= 1) {
                _uiEvent.send(FestivalUiEvent.ShowToast(UiText.StringResource(R.string.not_found_liked_festival)))
                return@launch
            }

            likedFestivalRepository.unregisterLikedFestival(festival)
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
