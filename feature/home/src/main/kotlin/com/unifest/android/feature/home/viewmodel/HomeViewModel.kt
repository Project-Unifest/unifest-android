package com.unifest.android.feature.home.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifest.android.core.common.ButtonType
import com.unifest.android.core.common.ErrorHandlerActions
import com.unifest.android.core.common.FestivalUiAction
import com.unifest.android.core.common.UiText
import com.unifest.android.core.common.handleException
import com.unifest.android.core.common.utils.matchesSearchText
import com.unifest.android.core.data.repository.FestivalRepository
import com.unifest.android.core.data.repository.LikedFestivalRepository
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.core.model.FestivalTodayModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
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
        getIncomingFestivals()
        getAllFestivals()
        _uiState.update {
            it.copy(
                incomingFestivals = persistentListOf(
                    FestivalModel(
                        1,
                        1,
                        "https://picsum.photos/36",
                        "서울대학교",
                        "서울",
                        "설대축제",
                        "2024-04-21",
                        "2024-04-23",
                        126.957f,
                        37.460f,
                    ),
                    FestivalModel(
                        2,
                        2,
                        "https://picsum.photos/36",
                        "연세대학교",
                        "서울",
                        "연대축제",
                        "2024-04-21",
                        "2024-04-23",
                        126.957f,
                        37.460f,
                    ),
                ),
            )
        }
        _uiState.update {
            it.copy(
                isStarImageClicked = it.todayFestivals.map { festival ->
                    persistentListOf<Boolean>().addAll(List(festival.starInfo.size) { false }).toImmutableList()
                }.toImmutableList(),
            )
        }
    }

    fun onHomeUiAction(action: HomeUiAction) {
        when (action) {
            is HomeUiAction.OnDateSelected -> {
                setSelectedDate(action.date)
                getTodayFestivals(action.date.toString())
            }

            is HomeUiAction.OnAddAsLikedFestivalClick -> addLikeFestival(action.festivalTodayModel)
            is HomeUiAction.OnAddLikedFestivalClick -> setFestivalSearchBottomSheetVisible(true)
            is HomeUiAction.OnToggleStarImageClick -> toggleStarImageClicked(action.scheduleIndex, action.starIndex, action.flag)
            is HomeUiAction.OnClickWeekMode -> setWeekMode(!_uiState.value.isWeekMode)
        }
    }

    fun onFestivalUiAction(action: FestivalUiAction) {
        when (action) {
            is FestivalUiAction.OnDismiss -> setFestivalSearchBottomSheetVisible(false)
            is FestivalUiAction.OnSearchTextUpdated -> updateSearchText(action.searchText)
            is FestivalUiAction.OnSearchTextCleared -> clearSearchText()
            is FestivalUiAction.OnEnableSearchMode -> setEnableSearchMode(action.flag)
            is FestivalUiAction.OnEnableEditMode -> setEnableEditMode()
            is FestivalUiAction.OnLikedFestivalSelected -> setRecentLikedFestival(action.festival.schoolName)
            is FestivalUiAction.OnAddClick -> addLikeFestivalAtBottomSheet(action.festival)
            is FestivalUiAction.OnDeleteIconClick -> {
                _uiState.update {
                    it.copy(deleteSelectedFestival = action.deleteSelectedFestival)
                }
                setLikedFestivalDeleteDialogVisible(true)
            }

            is FestivalUiAction.OnDeleteDialogButtonClick -> handleDeleteDialogButtonClick(action.buttonType)
            else -> {}
        }
    }

    private fun observeLikedFestivals() {
        viewModelScope.launch {
            likedFestivalRepository.getLikedFestivals().collect { likedFestivalList ->
                _uiState.update {
                    it.copy(
                        likedFestivals = likedFestivalList.toPersistentList(),
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

    private fun handleDeleteDialogButtonClick(buttonType: ButtonType) {
        when (buttonType) {
            ButtonType.CONFIRM -> {
                setLikedFestivalDeleteDialogVisible(false)
                _uiState.value.deleteSelectedFestival?.let { deleteLikedFestival(it) }
            }

            ButtonType.CANCEL -> setLikedFestivalDeleteDialogVisible(false)
        }
    }

    private fun getIncomingFestivals() {
        viewModelScope.launch {
            festivalRepository.getIncomingFestivals()
                .onSuccess { festivals ->
                    _uiState.update {
                        it.copy(incomingFestivals = festivals.toImmutableList())
                    }
                }
                .onFailure { exception ->
                    handleException(exception, this@HomeViewModel)
                }
        }
    }

    private fun getTodayFestivals(date: String) {
        viewModelScope.launch {
            festivalRepository.getTodayFestivals(date)
                .onSuccess { festivals ->
                    _uiState.update {
                        it.copy(
                            todayFestivals = festivals.toImmutableList(),
                            isStarImageClicked = festivals.map { festival ->
                                List(festival.starInfo.size) { false }.toImmutableList()
                            }.toImmutableList(),
                        )
                    }
                }
                .onFailure { exception ->
                    handleException(exception, this@HomeViewModel)
                }
        }
    }

    private fun getAllFestivals() {
        viewModelScope.launch {
            festivalRepository.getAllFestivals()
                .onSuccess { festivals ->
                    _uiState.update {
                        it.copy(
                            allFestivals = festivals.toImmutableList(),
                        )
                    }
                }
                .onFailure { exception ->
                    handleException(exception, this@HomeViewModel)
                }
        }
    }

    private fun updateSearchText(searchText: TextFieldValue) {
        _uiState.update {
            it.copy(
                festivalSearchText = searchText,
                festivalSearchResults = it.allFestivals.filter { festival ->
                    matchesSearchText(festival, searchText)
                }.toImmutableList(),
            )
        }
    }

    private fun clearSearchText() {
        _uiState.update {
            it.copy(
                festivalSearchText = TextFieldValue(),
                festivalSearchResults = persistentListOf(),
            )
        }
    }

    private fun setFestivalSearchBottomSheetVisible(flag: Boolean) {
        _uiState.update {
            it.copy(isFestivalSearchBottomSheetVisible = flag)
        }
    }

    private fun setRecentLikedFestival(schoolName: String) {
        viewModelScope.launch {
            if (schoolName == likedFestivalRepository.getRecentLikedFestival()) {
                // likedFestivalRepository.setRecentLikedFestival(schoolName)
                setFestivalSearchBottomSheetVisible(false)
                _uiEvent.send(HomeUiEvent.NavigateBack)
            } else {
                _uiEvent.send(HomeUiEvent.ShowToast(UiText.StringResource(R.string.menu_interest_festival_snack_bar)))
            }
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

    private fun toggleStarImageClicked(scheduleIndex: Int, starIndex: Int, flag: Boolean) {
        _uiState.update { currentState ->
            val updatedList = currentState.isStarImageClicked.mapIndexed { index, list ->
                if (index == scheduleIndex) {
                    list.toMutableList().apply {
                        if (starIndex < this.size) {
                            this[starIndex] = flag
                        }
                    }.toImmutableList()
                } else {
                    list
                }
            }.toImmutableList()
            currentState.copy(isStarImageClicked = updatedList)
        }
    }

    private fun setWeekMode(flag: Boolean) {
        _uiState.update {
            it.copy(isWeekMode = flag)
        }
    }

//    private fun isInDateRange(selectedDate: LocalDate, beginDate: String, endDate: String): Boolean {
//        return !selectedDate.isBefore(beginDate.toLocalDate()) && !selectedDate.isAfter(endDate.toLocalDate())
//    }
//
//    fun updateTodayFestivals(selectedDate: LocalDate) {
//        val todayFestivals = _uiState.value.allFestivals.filter {
//            isInDateRange(selectedDate, it.beginDate, it.endDate)
//        }.map {
//            FestivalTodayModel(
//                festivalId = it.festivalId,
//                schoolId = it.schoolId,
//                thumbnail = it.thumbnail,
//                schoolName = it.schoolName,
//                festivalName = it.festivalName,
//                beginDate = it.beginDate,
//                endDate = it.endDate,
//                starInfo = listOf(),
//            )
//        }.toImmutableList()
//
//        _uiState.update { currentState ->
//            currentState.copy(todayFestivals = todayFestivals)
//        }
//    }
}
