package com.unifest.android.feature.booth.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifest.android.core.common.UiText
import com.unifest.android.core.data.repository.BoothRepository
import com.unifest.android.core.data.repository.LikedBoothRepository
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.model.BoothDetailModel
import com.unifest.android.core.model.MenuModel
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
    @Suppress("unused")
    private val boothRepository: BoothRepository,
    private val likedBoothRepository: LikedBoothRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    @Suppress("unused")
    private val boothId: Long = requireNotNull(savedStateHandle.get<Long>(BOOTH_ID)) { "boothId is required." }

    private val _uiState = MutableStateFlow(BoothUiState())
    val uiState: StateFlow<BoothUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<BoothUiEvent>()
    val uiEvent: Flow<BoothUiEvent> = _uiEvent.receiveAsFlow()

    init {
        _uiState.update {
            it.copy(
                boothDetailInfo = BoothDetailModel(
                    id = 0L,
                    name = "컴공 주점",
                    category = "컴퓨터공학부 전용 부스",
                    description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                    location = "청심대 앞",
                    latitude = 37.54224856023523f,
                    longitude = 127.07605430700158f,
                    menus = listOf(
                        MenuModel(1L, "모둠 사시미", 45000, ""),
                        MenuModel(2L, "모둠 사시미", 45000, ""),
                        MenuModel(3L, "모둠 사시미", 45000, ""),
                        MenuModel(4L, "모둠 사시미", 45000, ""),
                    ),
                ),
            )
        }
    }

    fun onAction(action: BoothUiAction) {
        when (action) {
            is BoothUiAction.OnBackClick -> navigateBack()
            is BoothUiAction.OnCheckLocationClick -> navigateToBoothLocation()
            is BoothUiAction.OnToggleBookmark -> toggleBookmark()
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
        val currentBookmarkFlag = _uiState.value.isBookmarked
        val newBookmarkFlag = !currentBookmarkFlag
        viewModelScope.launch {
            if (currentBookmarkFlag) {
                likedBoothRepository.deleteLikedBooth(_uiState.value.boothDetailInfo)
            } else {
                likedBoothRepository.insertLikedBooth(_uiState.value.boothDetailInfo)
            }
            _uiState.update {
                it.copy(
                    isBookmarked = newBookmarkFlag,
                    bookmarkCount = it.bookmarkCount + if (newBookmarkFlag) 1 else -1,
                )
            }
            _uiEvent.send(
                BoothUiEvent.ShowSnackBar(
                    if (newBookmarkFlag) UiText.StringResource(R.string.booth_bookmarked_message)
                    else UiText.StringResource(R.string.booth_bookmark_removed_message),
                ),
            )
        }
    }
}
