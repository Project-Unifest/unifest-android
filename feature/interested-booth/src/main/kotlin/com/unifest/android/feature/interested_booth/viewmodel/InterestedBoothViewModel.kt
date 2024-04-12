package com.unifest.android.feature.interested_booth.viewmodel

import androidx.lifecycle.ViewModel
import com.unifest.android.core.domain.entity.BoothDetailEntity
import com.unifest.android.core.domain.entity.MenuEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class InterestedBoothViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(InterestedBoothUiState())
    val uiState: StateFlow<InterestedBoothUiState> = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                interestedBooths = persistentListOf(
                    BoothDetailEntity(
                        id = 1,
                        name = "부스 이름",
                        category = "음식",
                        description = "부스 설명",
                        warning = "주의사항",
                        location = "부스 위치",
                        latitude = 0.0f,
                        longitude = 0.0f,
                        menus = listOf(
                            MenuEntity(
                                id = 1,
                                name = "메뉴 이름",
                                price = 1000,
                                imgUrl = "",
                            ),
                        ),
                    ),
                    BoothDetailEntity(
                        id = 2,
                        name = "부스 이름",
                        category = "음식",
                        description = "부스 설명",
                        warning = "주의사항",
                        location = "부스 위치",
                        latitude = 0.0f,
                        longitude = 0.0f,
                        menus = listOf(
                            MenuEntity(
                                id = 1,
                                name = "메뉴 이름",
                                price = 1000,
                                imgUrl = "",
                            ),
                        ),
                    ),
                    BoothDetailEntity(
                        id = 3,
                        name = "부스 이름",
                        category = "음식",
                        description = "부스 설명",
                        warning = "주의사항",
                        location = "부스 위치",
                        latitude = 0.0f,
                        longitude = 0.0f,
                        menus = listOf(
                            MenuEntity(
                                id = 1,
                                name = "메뉴 이름",
                                price = 1000,
                                imgUrl = "",
                            ),
                        ),
                    ),
                    BoothDetailEntity(
                        id = 4,
                        name = "부스 이름",
                        category = "음식",
                        description = "부스 설명",
                        warning = "주의사항",
                        location = "부스 위치",
                        latitude = 0.0f,
                        longitude = 0.0f,
                        menus = listOf(
                            MenuEntity(
                                id = 1,
                                name = "메뉴 이름",
                                price = 1000,
                                imgUrl = "",
                            ),
                        ),
                    ),
                    BoothDetailEntity(
                        id = 5,
                        name = "부스 이름",
                        category = "음식",
                        description = "부스 설명",
                        warning = "주의사항",
                        location = "부스 위치",
                        latitude = 0.0f,
                        longitude = 0.0f,
                        menus = listOf(
                            MenuEntity(
                                id = 1,
                                name = "메뉴 이름",
                                price = 1000,
                                imgUrl = "",
                            ),
                        ),
                    ),
                    BoothDetailEntity(
                        id = 6,
                        name = "부스 이름",
                        category = "음식",
                        description = "부스 설명",
                        warning = "주의사항",
                        location = "부스 위치",
                        latitude = 0.0f,
                        longitude = 0.0f,
                        menus = listOf(
                            MenuEntity(
                                id = 1,
                                name = "메뉴 이름",
                                price = 1000,
                                imgUrl = "",
                            ),
                        ),
                    ),
                ),
            )
        }
    }
}
