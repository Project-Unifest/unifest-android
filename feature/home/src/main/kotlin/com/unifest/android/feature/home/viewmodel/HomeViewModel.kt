package com.unifest.android.feature.home.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifest.android.core.common.ErrorHandlerActions
import com.unifest.android.core.common.handleException
import com.unifest.android.core.data.repository.FestivalRepository
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.model.FestivalEventModel
import com.unifest.android.core.model.FestivalSearchModel
import com.unifest.android.core.model.IncomingFestivalEventModel
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
        _uiState.update {
            it.copy(
                incomingEvents = persistentListOf(
                    FestivalSearchModel(
                        thumbnail = "https://picsum.photos/36",
                        schoolName = "건국대학교",
                        festivalName = "녹색지대",
                        beginDate = "05/21(화)",
                        endDate = "05/23(목)",
                        latitude = 37.54f,
                        longitude = 127.07f,
                    ),
                    FestivalSearchModel(
                        thumbnail = "https://picsum.photos/36",
                        schoolName = "건국대학교",
                        festivalName = "녹색지대",
                        beginDate = "05/21(화)",
                        endDate = "05/23(목)",
                        latitude = 37.54f,
                        longitude = 127.07f,
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

    fun getIncomingFestivals() {
        viewModelScope.launch {
            festivalRepository.getIncomingFestivals()
                .onSuccess { festivals ->
                    _uiState.update {
                        it.copy(
                            incomingEvents = festivals.toImmutableList(),
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
