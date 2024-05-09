package com.unifest.android.feature.liked_booth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifest.android.core.common.ErrorHandlerActions
import com.unifest.android.core.common.UiText
import com.unifest.android.core.common.handleException
import com.unifest.android.core.data.repository.BoothRepository
import com.unifest.android.core.data.repository.LikedBoothRepository
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.model.LikedBoothModel
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
class LikedBoothViewModel @Inject constructor(
    private val boothRepository: BoothRepository,
    private val likedBoothRepository: LikedBoothRepository,
) : ViewModel(), ErrorHandlerActions {
    private val _uiState = MutableStateFlow(LikedBoothUiState())
    val uiState: StateFlow<LikedBoothUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<LikedBoothUiEvent>()
    val uiEvent: Flow<LikedBoothUiEvent> = _uiEvent.receiveAsFlow()

    init {
        // observeLikedBooth()
        getLikedBooths()
    }

    fun onAction(action: LikedBoothUiAction) {
        when (action) {
            is LikedBoothUiAction.OnBackClick -> navigateBack()
            is LikedBoothUiAction.OnLikedBoothItemClick -> navigateToBoothDetail(action.boothId)
            is LikedBoothUiAction.OnToggleBookmark -> deleteLikedBooth(action.booth)
            is LikedBoothUiAction.OnRetryClick -> refresh(action.error)
        }
    }

    private fun getLikedBooths() {
        viewModelScope.launch {
            likedBoothRepository.getLikedBooths()
                .onSuccess { likedBooths ->
                    _uiState.update {
                        it.copy(likedBooths = likedBooths.toImmutableList())
                    }
                }.onFailure { exception ->
                    handleException(exception, this@LikedBoothViewModel)
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

    private fun navigateBack() {
        viewModelScope.launch {
            _uiEvent.send(LikedBoothUiEvent.NavigateBack)
        }
    }

    private fun navigateToBoothDetail(boothId: Long) {
        viewModelScope.launch {
            _uiEvent.send(LikedBoothUiEvent.NavigateToBoothDetail(boothId))
        }
    }

    private fun deleteLikedBooth(booth: LikedBoothModel) {
        viewModelScope.launch {
            boothRepository.likeBooth(booth.id)
                .onSuccess {
                    updateLikedBooth(booth)
                    delay(500)
                    getLikedBooths()
                    _uiEvent.send(LikedBoothUiEvent.ShowSnackBar(UiText.StringResource(R.string.liked_booth_removed_message)))
                }.onFailure {
                    _uiEvent.send(LikedBoothUiEvent.ShowSnackBar(UiText.StringResource(R.string.liked_booth_removed_failed_message)))
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
//                    _uiEvent.send(LikedBoothUiEvent.ShowSnackBar(UiText.StringResource(R.string.liked_booth_removed_message)))
//                }.onFailure {
//                    _uiEvent.send(LikedBoothUiEvent.ShowSnackBar(UiText.StringResource(R.string.liked_booth_removed_failed_message)))
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
        getLikedBooths()
        when (error) {
            ErrorType.NETWORK -> setNetworkErrorDialogVisible(false)
            ErrorType.SERVER -> setServerErrorDialogVisible(false)
        }
    }
}
