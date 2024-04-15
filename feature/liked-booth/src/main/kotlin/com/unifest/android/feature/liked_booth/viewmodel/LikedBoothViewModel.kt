package com.unifest.android.feature.liked_booth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifest.android.core.data.repository.LikedBoothRepository
import com.unifest.android.core.model.BoothDetailModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LikedBoothViewModel @Inject constructor(
    private val likedBoothRepository: LikedBoothRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(LikedBoothUiState())
    val uiState: StateFlow<LikedBoothUiState> = _uiState.asStateFlow()

    init {
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

    fun deleteLikedBooth(booth: BoothDetailModel) {
        viewModelScope.launch {
            updateLikedBooth(booth)
            delay(500)
            likedBoothRepository.deleteLikedBooth(booth)
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
