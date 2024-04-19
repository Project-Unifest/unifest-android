package com.unifest.android.feature.home.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifest.android.core.common.ErrorHandlerActions
import com.unifest.android.core.common.handleException
import com.unifest.android.core.data.repository.FestivalRepository
import com.unifest.android.core.model.FestivalTodayModel
import com.unifest.android.core.model.FestivalModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val festivalRepository: FestivalRepository,
) : ViewModel(), ErrorHandlerActions {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()


    init {

        viewModelScope.launch {
            festivalRepository.getLikedFestivals().collect { likedFestivalList ->
                _uiState.update {
                    it.copy(
                        likedFestivals = likedFestivalList.toMutableList(),
                    )
                }
            }
        }

        _uiState.update {
            it.copy(
                incomingFestivals = persistentListOf(
                    FestivalModel(
                        1,
                        1,
                        "https://picsum.photos/36",
                        "서울대학교",
                        "설대축제",
                        "05.06",
                        "05.08",
                        126.957f,
                        37.460f,
                    ),
                    FestivalModel(
                        2,
                        2,
                        "https://picsum.photos/36",
                        "연세대학교",
                        "연대축제",
                        "05.06",
                        "05.08",
                        126.957f,
                        37.460f,
                    ),
                ),
                todayFestivals = persistentListOf(
                    FestivalTodayModel(
                        festivalId = 1,
                        date = "5/21(화)",
                        festivalName = "녹색지대 DAY 1",
                        schoolName = "건국대학교",
                        starList = listOf(),
                        schoolId = 1,
                    ),
                    FestivalTodayModel(
                        festivalId = 2,
                        date = "5/21(화)",
                        festivalName = "녹색지대 DAY 2",
                        schoolName = "서울대학교",
                        starList = listOf(),
                        schoolId = 2,
                    ),
                    FestivalTodayModel(
                        festivalId = 3,
                        date = "5/21(화)",
                        festivalName = "녹색지대 DAY 3",
                        schoolName = "연세대학교",
                        starList = listOf(),
                        schoolId = 3,
                    ),
                ),
            )
        }
    }


    fun addLikeFestival(festival: FestivalTodayModel) {
        viewModelScope.launch {
            if (!festivalRepository.isFestivalExists(festival.festivalId)) {
                festivalRepository.insertLikedFestivalAtHome(festival)
            }
        }
    }

    fun addLikeFestivalAtBottomSheetSearch(festival: FestivalModel) {
        viewModelScope.launch {
            if (!festivalRepository.isFestivalExists(festival.festivalId)) {
                festivalRepository.insertLikedFestivalAtSearch(festival)
            }
        }
    }

fun getIncomingFestivals() {
    viewModelScope.launch {
        festivalRepository.getIncomingFestivals()
            .onSuccess { festivals ->
                _uiState.update {
                    it.copy(
                        incomingFestivals = festivals.toImmutableList(),
                    )
                }
            }
            .onFailure { exception ->
                handleException(exception, this@HomeViewModel)
            }
    }
}

fun getTodayFestivals(date: String) {
    viewModelScope.launch {
        festivalRepository.getTodayFestivals(date)
            .onSuccess { festivals ->
                _uiState.update {
                    it.copy(
                        todayFestivals = festivals.toImmutableList(),
                    )
                }
            }
            .onFailure { exception ->
                handleException(exception, this@HomeViewModel)
            }
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

fun setSelectedDate(date: LocalDate) {
    _uiState.update {
        it.copy(selectedDate = date)
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
