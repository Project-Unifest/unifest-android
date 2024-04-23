package com.unifest.android.feature.liked_booth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifest.android.core.common.UiText
import com.unifest.android.core.data.repository.LikedBoothRepository
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.model.BoothDetailModel
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
    private val likedBoothRepository: LikedBoothRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(LikedBoothUiState())
    val uiState: StateFlow<LikedBoothUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<LikedBoothUiEvent>()
    val uiEvent: Flow<LikedBoothUiEvent> = _uiEvent.receiveAsFlow()

    init {
        observeLikedBooth()
    }

    fun onAction(action: LikedBoothUiAction) {
        when (action) {
            is LikedBoothUiAction.OnBackClick -> navigateBack()
            is LikedBoothUiAction.OnLikedBoothItemClick -> navigateToBoothDetail(action.boothId)
            is LikedBoothUiAction.OnToggleBookmark -> deleteLikedBooth(action.booth)
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

    private fun deleteLikedBooth(booth: BoothDetailModel) {
        viewModelScope.launch {
            updateLikedBooth(booth)
            delay(500)
            likedBoothRepository.deleteLikedBooth(booth)
            _uiEvent.send(LikedBoothUiEvent.ShowSnackBar(UiText.StringResource(R.string.booth_bookmark_removed_message)))
        }
    }

    private suspend fun updateLikedBooth(booth: BoothDetailModel) {
        likedBoothRepository.updateLikedBooth(booth.copy(isLiked = false))
    }

//    init {
//        _uiState.update {
//            it.copy(
//                likedBoothList = persistentListOf(
//                    BoothDetailEntity(
//                        id = 1,
//                        name = "부스 이름",
//                        category = "음식",
//                        description = "부스 설명",
//                        warning = "주의사항",
//                        location = "부스 위치",
//                        latitude = 0.0f,
//                        longitude = 0.0f,
//                        menus = listOf(
//                            MenuEntity(
//                                id = 1,
//                                name = "메뉴 이름",
//                                price = 1000,
//                                imgUrl = "",
//                            ),
//                        ),
//                    ),
//                    BoothDetailEntity(
//                        id = 2,
//                        name = "부스 이름",
//                        category = "음식",
//                        description = "부스 설명",
//                        warning = "주의사항",
//                        location = "부스 위치",
//                        latitude = 0.0f,
//                        longitude = 0.0f,
//                        menus = listOf(
//                            MenuEntity(
//                                id = 1,
//                                name = "메뉴 이름",
//                                price = 1000,
//                                imgUrl = "",
//                            ),
//                        ),
//                    ),
//                    BoothDetailEntity(
//                        id = 3,
//                        name = "부스 이름",
//                        category = "음식",
//                        description = "부스 설명",
//                        warning = "주의사항",
//                        location = "부스 위치",
//                        latitude = 0.0f,
//                        longitude = 0.0f,
//                        menus = listOf(
//                            MenuEntity(
//                                id = 1,
//                                name = "메뉴 이름",
//                                price = 1000,
//                                imgUrl = "",
//                            ),
//                        ),
//                    ),
//                    BoothDetailEntity(
//                        id = 4,
//                        name = "부스 이름",
//                        category = "음식",
//                        description = "부스 설명",
//                        warning = "주의사항",
//                        location = "부스 위치",
//                        latitude = 0.0f,
//                        longitude = 0.0f,
//                        menus = listOf(
//                            MenuEntity(
//                                id = 1,
//                                name = "메뉴 이름",
//                                price = 1000,
//                                imgUrl = "",
//                            ),
//                        ),
//                    ),
//                    BoothDetailEntity(
//                        id = 5,
//                        name = "부스 이름",
//                        category = "음식",
//                        description = "부스 설명",
//                        warning = "주의사항",
//                        location = "부스 위치",
//                        latitude = 0.0f,
//                        longitude = 0.0f,
//                        menus = listOf(
//                            MenuEntity(
//                                id = 1,
//                                name = "메뉴 이름",
//                                price = 1000,
//                                imgUrl = "",
//                            ),
//                        ),
//                    ),
//                    BoothDetailEntity(
//                        id = 6,
//                        name = "부스 이름",
//                        category = "음식",
//                        description = "부스 설명",
//                        warning = "주의사항",
//                        location = "부스 위치",
//                        latitude = 0.0f,
//                        longitude = 0.0f,
//                        menus = listOf(
//                            MenuEntity(
//                                id = 1,
//                                name = "메뉴 이름",
//                                price = 1000,
//                                imgUrl = "",
//                            ),
//                        ),
//                    ),
//                ),
//            )
//        }
}
