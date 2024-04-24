package com.unifest.android.feature.home.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifest.android.core.common.ButtonType
import com.unifest.android.core.common.ErrorHandlerActions
import com.unifest.android.core.common.FestivalUiAction
import com.unifest.android.core.common.UiText
import com.unifest.android.core.common.handleException
import com.unifest.android.core.data.repository.FestivalRepository
import com.unifest.android.core.data.repository.LikedFestivalRepository
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.model.FestivalTodayModel
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.core.model.StarInfoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val festivalRepository: FestivalRepository,
    private val likedFestivalRepository: LikedFestivalRepository,
) : ViewModel(), ErrorHandlerActions {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<HomeUiEvent>()
    val uiEvent: Flow<HomeUiEvent> = _uiEvent.receiveAsFlow()

    init {
        observeLikedFestivals()

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
                        festivalId = 5,
                        beginDate = "5/20(화)",
                        endDate = "5/22(목)",
                        festivalName = "녹색지대 DAY 1",
                        schoolName = "건국대학교",
                        starInfo = listOf(
                            StarInfoModel(
                                name = "비",
                                img = "https://picsum.photos/36",
                            ),
                            StarInfoModel(
                                name = "싸이",
                                img = "https://picsum.photos/37",
                            ),
                            StarInfoModel(
                                name = "아이유",
                                img = "https://picsum.photos/38",
                            ),
                        ),
                        schoolId = 5,
                        thumbnail = "https://picsum.photos/36",
                    ),
                    FestivalTodayModel(
                        festivalId = 1,
                        beginDate = "5/20(화)",
                        endDate = "5/22(목)",
                        festivalName = "녹색지대 DAY 2",
                        schoolName = "서울대학교",
                        starInfo = listOf(
                            StarInfoModel(
                                name = "비",
                                img = "https://picsum.photos/36",
                            ),
                            StarInfoModel(
                                name = "싸이",
                                img = "https://picsum.photos/37",
                            ),
                            StarInfoModel(
                                name = "아이유",
                                img = "https://picsum.photos/38",
                            ),
                        ),
                        schoolId = 1,
                        thumbnail = "https://picsum.photos/36",
                    ),
                    FestivalTodayModel(
                        festivalId = 2,
                        beginDate = "5/20(화)",
                        endDate = "5/22(목)",
                        festivalName = "녹색지대 DAY 3",
                        schoolName = "연세대학교",
                        starInfo = listOf(
                            StarInfoModel(
                                name = "비",
                                img = "https://picsum.photos/36",
                            ),
                            StarInfoModel(
                                name = "싸이",
                                img = "https://picsum.photos/37",
                            ),
                            StarInfoModel(
                                name = "아이유",
                                img = "https://picsum.photos/38",
                            ),
                        ),
                        schoolId = 2,
                        thumbnail = "https://picsum.photos/36",
                    ),
                ),
            )
        }
    }

    fun onHomeUiAction(action: HomeUiAction) {
        when (action) {
            is HomeUiAction.OnDateSelected -> setSelectedDate(action.date)
            is HomeUiAction.OnAddAsLikedFestivalClick -> addLikeFestival(action.festivalTodayModel)
            is HomeUiAction.OnAddLikedFestivalClick -> setFestivalSearchBottomSheetVisible(true)
        }
    }

    fun onFestivalUiAction(action: FestivalUiAction) {
        when (action) {
            is FestivalUiAction.OnDismiss -> setFestivalSearchBottomSheetVisible(false)
            is FestivalUiAction.OnSearchTextUpdated -> updateSearchText(action.text)
            is FestivalUiAction.OnSearchTextCleared -> clearSearchText()
            is FestivalUiAction.OnEnableSearchMode -> setEnableSearchMode(action.flag)
            is FestivalUiAction.OnEnableEditMode -> setEnableEditMode()
            is FestivalUiAction.OnAddClick -> addLikeFestivalAtBottomSheet(action.festival)
            is FestivalUiAction.OnDeleteIconClick -> setLikedFestivalDeleteDialogVisible(true)
            is FestivalUiAction.OnDialogButtonClick -> {
                when (action.type) {
                    ButtonType.CONFIRM -> {
                        setLikedFestivalDeleteDialogVisible(false)
                        action.festival?.let { deleteLikedFestival(it) }
                    }

                    ButtonType.CANCEL -> setLikedFestivalDeleteDialogVisible(false)
                }
            }
        }
    }

    private fun observeLikedFestivals() {
        viewModelScope.launch {
            likedFestivalRepository.getLikedFestivals().collect { likedFestivalList ->
                _uiState.update {
                    it.copy(
                        likedFestivals = likedFestivalList.toMutableList(),
                    )
                }
            }
        }
    }

    private fun addLikeFestival(festival: FestivalTodayModel) {
        viewModelScope.launch {
            likedFestivalRepository.insertLikedFestivalAtHome(festival)
            _uiEvent.send(HomeUiEvent.ShowSnackBar(UiText.StringResource(R.string.home_add_interest_festival_snack_bar)))
        }
    }

    private fun addLikeFestivalAtBottomSheet(festival: FestivalModel) {
        viewModelScope.launch {
            likedFestivalRepository.insertLikedFestivalAtSearch(festival)
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

    private fun updateSearchText(text: TextFieldValue) {
        _uiState.update {
            it.copy(festivalSearchText = text)
        }
    }

    private fun clearSearchText() {
        _uiState.update {
            it.copy(festivalSearchText = TextFieldValue())
        }
    }

    private fun setFestivalSearchBottomSheetVisible(flag: Boolean) {
        _uiState.update {
            it.copy(isFestivalSearchBottomSheetVisible = flag)
        }
    }

    private fun setEnableSearchMode(flag: Boolean) {
        _uiState.update {
            it.copy(isSearchMode = flag)
        }
    }

    private fun setEnableEditMode() {
        _uiState.update {
            it.copy(isEditMode = !_uiState.value.isEditMode)
        }
    }

    private fun setLikedFestivalDeleteDialogVisible(flag: Boolean) {
        _uiState.update {
            it.copy(isLikedFestivalDeleteDialogVisible = flag)
        }
    }

    private fun setSelectedDate(date: LocalDate) {
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

    private fun deleteLikedFestival(festival: FestivalModel) {
        viewModelScope.launch {
            likedFestivalRepository.deleteLikedFestival(festival)
        }
    }
}
