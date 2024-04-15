package com.unifest.android.feature.home.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.model.FestivalEventModel
import com.unifest.android.core.model.IncomingFestivalEventModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                incomingEvents = persistentListOf(
                    IncomingFestivalEventModel(
                        imageRes = R.drawable.ic_waiting,
                        name = "녹색지대",
                        dates = "05/21(화) - 05/23(목)",
                        location = "건국대학교 서울캠퍼스",
                    ),
                    IncomingFestivalEventModel(
                        imageRes = R.drawable.ic_waiting,
                        name = "녹색지대",
                        dates = "05/21(화) - 05/23(목)",
                        location = "건국대학교 서울캠퍼스",
                    ),
                ),
                festivalEvents = persistentListOf(
                    FestivalEventModel(
                        id = 1,
                        date = "5/21(화)",
                        name = "녹색지대 DAY 1",
                        location = "건국대학교 서울캠퍼스",
                        celebrityImages = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15),
                    ),
                    FestivalEventModel(
                        id = 2,
                        date = "5/21(화)",
                        name = "녹색지대 DAY 1",
                        location = "건국대학교 서울캠퍼스",
                        celebrityImages = listOf(0, 1, 2),
                    ),
                    FestivalEventModel(
                        id = 3,
                        date = "5/21(화)",
                        name = "녹색지대 DAY 1",
                        location = "건국대학교 서울캠퍼스",
                        celebrityImages = listOf(0, 1, 2),
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

    fun setSelectedDate(date: LocalDate) {
        _uiState.update {
            it.copy(selectedDate = date)
        }
    }
}
