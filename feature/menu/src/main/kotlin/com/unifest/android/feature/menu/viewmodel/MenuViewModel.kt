package com.unifest.android.feature.menu.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.unifest.android.core.domain.entity.BoothDetailEntity
import com.unifest.android.core.domain.entity.Festival
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(MenuUiState())
    val uiState: StateFlow<MenuUiState> = _uiState.asStateFlow()
    init {
        _uiState.update { currentState ->
            currentState.copy(
                likedFestivals = mutableListOf(
                    Festival("https://picsum.photos/36", "서울대학교", "설대축제", "05.06-05.08"),
                    Festival("https://picsum.photos/36", "연세대학교", "연대축제", "05.06-05.08"),
                    Festival("https://picsum.photos/36", "고려대학교", "고대축제", "05.06-05.08"),
                    Festival("https://picsum.photos/36", "건국대학교", "녹색지대", "05.06-05.08"),
                    Festival("https://picsum.photos/36", "성균관대학교", "성대축제", "05.06-05.08"),
                ),
                festivalSearchResults = persistentListOf(
                    Festival("https://picsum.photos/36", "서울대학교", "설대축제", "05.06-05.08"),
                    Festival("https://picsum.photos/36", "연세대학교", "연대축제", "05.06-05.08"),
                    Festival("https://picsum.photos/36", "고려대학교", "고대축제", "05.06-05.08"),
                    Festival("https://picsum.photos/36", "건국대학교", "녹색지대", "05.06-05.08"),
                    Festival("https://picsum.photos/36", "성균관대학교", "성대축제", "05.06-05.08"),
                ),
                // 임시 데이터
                festivals = persistentListOf(
                    Festival("school_image_url_1", "서울대학교", "설대축제", "05.06-05.08"),
                    Festival("school_image_url_2", "연세대학교", "연대축제", "05.06-05.08"),
                    Festival("school_image_url_3", "고려대학교", "고대축제", "05.06-05.08"),
                    Festival("school_image_url_4", "건국대학교", "녹색지대", "05.06-05.08"),
                    Festival("school_image_url_5", "성균관대", "성대축제", "05.06-05.08"),
                ),
                likedBoothList = persistentListOf(
                    BoothDetailEntity(
                        id = 1,
                        name = "부스1",
                        category = "카페",
                        description = "부스1 설명",
                        warning = "부스1 주의사항",
                        location = "부스1 위치",
                        latitude = 37.5665f,
                        longitude = 126.9780f,
                        menus = listOf(),
                    ),
                    BoothDetailEntity(
                        id = 2,
                        name = "부스2",
                        category = "카페",
                        description = "부스2 설명",
                        warning = "부스2 주의사항",
                        location = "부스2 위치",
                        latitude = 37.5665f,
                        longitude = 126.9780f,
                        menus = listOf(),
                    ),
                    BoothDetailEntity(
                        id = 3,
                        name = "부스3",
                        category = "카페",
                        description = "부스3 설명",
                        warning = "부스3 주의사항",
                        location = "부스3 위치",
                        latitude = 37.5665f,
                        longitude = 126.9780f,
                        menus = listOf(),
                    ),
                ),
            )
        }
    }

    fun updateFestivalSearchText(text: TextFieldValue) {
        _uiState.update {
            it.copy(festivalSearchText = text)
        }
    }

    fun initSearchText() {
        _uiState.update {
            it.copy(festivalSearchText = TextFieldValue())
        }
    }

    fun setFestivalSearchBottomSheetVisible(flag: Boolean) {
        _uiState.update {
            it.copy(isFestivalSearchBottomSheetVisible = flag)
        }
    }

    fun setEnableSearchMode(flag: Boolean) {
        _uiState.update {
            it.copy(isSearchMode = flag)
        }
    }

    fun setEnableEditMode() {
        _uiState.update {
            it.copy(isEditMode = !_uiState.value.isEditMode)
        }
    }

    fun setLikedFestivalDeleteDialogVisible(flag: Boolean) {
        _uiState.update {
            it.copy(isLikedFestivalDeleteDialogVisible = flag)
        }
    }
}
