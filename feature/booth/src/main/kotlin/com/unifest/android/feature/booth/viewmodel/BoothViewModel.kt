package com.unifest.android.feature.booth.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifest.android.core.common.ErrorHandlerActions
import com.unifest.android.core.common.UiText
import com.unifest.android.core.common.handleException
import com.unifest.android.core.data.repository.BoothRepository
import com.unifest.android.core.data.repository.LikedBoothRepository
import com.unifest.android.core.designsystem.R
import com.unifest.android.feature.booth.navigation.BOOTH_ID
import dagger.hilt.android.lifecycle.HiltViewModel
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
class BoothViewModel @Inject constructor(
    private val boothRepository: BoothRepository,
    private val likedBoothRepository: LikedBoothRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel(), ErrorHandlerActions {
    private val boothId: Long = requireNotNull(savedStateHandle.get<Long>(BOOTH_ID)) { "boothId is required." }

    private val _uiState = MutableStateFlow(BoothUiState())
    val uiState: StateFlow<BoothUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<BoothUiEvent>()
    val uiEvent: Flow<BoothUiEvent> = _uiEvent.receiveAsFlow()

    init {
        getBoothDetail()
    }

    fun onAction(action: BoothUiAction) {
        when (action) {
            is BoothUiAction.OnBackClick -> navigateBack()
            is BoothUiAction.OnCheckLocationClick -> navigateToBoothLocation()
            is BoothUiAction.OnToggleBookmark -> toggleBookmark()
            is BoothUiAction.OnRetryClick -> refresh(action.error)
        }
    }

    private fun getBoothDetail() {
        viewModelScope.launch {
            boothRepository.getBoothDetail(boothId)
                .onSuccess { booth ->
                    _uiState.update {
                        it.copy(
                            boothDetailInfo = booth,
                        )
                    }
                }
                .onFailure { exception ->
                    handleException(exception, this@BoothViewModel)
                }
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _uiEvent.send(BoothUiEvent.NavigateBack)
        }
    }

    private fun navigateToBoothLocation() {
        viewModelScope.launch {
            _uiEvent.send(BoothUiEvent.NavigateToBoothLocation)
        }
    }

    private fun toggleBookmark() {
        val currentBookmarkFlag = _uiState.value.boothDetailInfo.isLiked
        val newBookmarkFlag = !currentBookmarkFlag
        viewModelScope.launch {
            boothRepository.likeBooth(boothId)
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            boothDetailInfo = it.boothDetailInfo.copy(
                                isLiked = newBookmarkFlag,
                                likes = it.boothDetailInfo.likes + if (newBookmarkFlag) 1 else -1,
                            ),
                        )
                    }
                    if (currentBookmarkFlag) {
                        likedBoothRepository.deleteLikedBooth(_uiState.value.boothDetailInfo)
                        _uiEvent.send(BoothUiEvent.ShowSnackBar(UiText.StringResource(R.string.booth_bookmark_removed_message),))
                    } else {
                        likedBoothRepository.insertLikedBooth(_uiState.value.boothDetailInfo)
                        _uiEvent.send(BoothUiEvent.ShowSnackBar(UiText.StringResource(R.string.booth_bookmarked_message)))
                    }
                }
                .onFailure { exception ->
                    handleException(exception, this@BoothViewModel)
                }
        }
    }

    private fun refresh(error: ErrorType) {
        getBoothDetail()
        when (error) {
            ErrorType.NETWORK -> setNetworkErrorDialogVisible(false)
            ErrorType.SERVER -> setServerErrorDialogVisible(false)
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
