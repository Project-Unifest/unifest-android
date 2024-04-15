package com.unifest.android.feature.booth.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifest.android.core.data.repository.LikedBoothRepository
import com.unifest.android.core.model.BoothDetail
import com.unifest.android.core.model.Menu
import com.unifest.android.feature.booth.navigation.BOOTH_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoothViewModel @Inject constructor(
    private val likedBoothRepository: LikedBoothRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    @Suppress("unused")
    private val boothId: Long = requireNotNull(savedStateHandle.get<Long>(BOOTH_ID)) { "boothId is required." }

    private val _uiState = MutableStateFlow(BoothUiState())
    val uiState: StateFlow<BoothUiState> = this._uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                boothDetailInfo = BoothDetail(
                    id = 0L,
                    name = "컴공 주점",
                    category = "컴퓨터공학부 전용 부스",
                    description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                    location = "청심대 앞",
                    latitude = 37.54224856023523f,
                    longitude = 127.07605430700158f,
                    menus = listOf(
                        Menu(1L, "모둠 사시미", 45000, ""),
                        Menu(2L, "모둠 사시미", 45000, ""),
                        Menu(3L, "모둠 사시미", 45000, ""),
                        Menu(4L, "모둠 사시미", 45000, ""),
                    ),
                ),
            )
        }
    }

    fun toggleBookmark() {
        viewModelScope.launch {
            if (_uiState.value.isBookmarked) {
                likedBoothRepository.deleteLikedBooth(_uiState.value.boothDetailInfo)
            } else {
                likedBoothRepository.insertLikedBooth(_uiState.value.boothDetailInfo)
            }
            _uiState.update { currentState ->
                val newBookmarkState = !currentState.isBookmarked
                currentState.copy(
                    isBookmarked = newBookmarkState,
                    bookmarkCount = currentState.bookmarkCount + if (newBookmarkState) 1 else -1,
                )
            }
        }
    }
}
