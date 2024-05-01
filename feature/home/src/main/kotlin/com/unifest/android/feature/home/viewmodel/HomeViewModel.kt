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
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.core.model.FestivalTodayModel
import com.unifest.android.core.model.StarInfoModel
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
//        getAllFestivals()
        _uiState.update {
            it.copy(
                incomingFestivals = persistentListOf(
                    FestivalModel(
                        1,
                        1,
                        "https://picsum.photos/36",
                        "서울대학교",
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
                        "연대축제",
                        "2024-04-21",
                        "2024-04-23",
                        126.957f,
                        37.460f,
                    ),
                ),
                todayFestivals = persistentListOf(
                    FestivalTodayModel(
                        festivalId = 5,
                        beginDate = "2024-04-05",
                        endDate = "2024-04-07",
                        festivalName = "녹색지대",
                        schoolName = "건국대학교",
                        starInfo = listOf(
                            StarInfoModel(
                                starId = 1,
                                name = "뉴진스",
                                imgUrl = "https://picsum.photos/36",
                            ),
                            StarInfoModel(
                                starId = 2,
                                name = "싸이",
                                imgUrl = "https://picsum.photos/37",
                            ),
                            StarInfoModel(
                                starId = 3,
                                name = "아이유",
                                imgUrl = "https://picsum.photos/38",
                            ),
                        ),
                        schoolId = 5,
                        thumbnail = "https://picsum.photos/36",
                    ),
                    FestivalTodayModel(
                        festivalId = 1,
                        beginDate = "2024-04-15",
                        endDate = "2024-04-17",
                        festivalName = "녹색지대",
                        schoolName = "서울대학교",
                        starInfo = listOf(
                            StarInfoModel(
                                starId = 4,
                                name = "아이브",
                                imgUrl = "https://picsum.photos/36",
                            ),
                            StarInfoModel(
                                starId = 2,
                                name = "싸이",
                                imgUrl = "https://picsum.photos/37",
                            ),
                            StarInfoModel(
                                starId = 5,
                                name = "르세라핌",
                                imgUrl = "https://picsum.photos/38",
                            ),
                            StarInfoModel(
                                starId = 6,
                                name = "박재범",
                                imgUrl = "https://picsum.photos/38",
                            ),
                        ),
                        schoolId = 1,
                        thumbnail = "https://picsum.photos/36",
                    ),
                    FestivalTodayModel(
                        festivalId = 2,
                        beginDate = "2024-04-22",
                        endDate = "2024-04-24",
                        festivalName = "녹색지대",
                        schoolName = "연세대학교",
                        starInfo = listOf(
                            StarInfoModel(
                                starId = 7,
                                name = "지코",
                                imgUrl = "https://picsum.photos/36",
                            ),
                            StarInfoModel(
                                starId = 2,
                                name = "싸이",
                                imgUrl = "https://picsum.photos/37",
                            ),
                            StarInfoModel(
                                starId = 8,
                                name = "아일릿",
                                imgUrl = "https://picsum.photos/38",
                            ),
                            StarInfoModel(
                                starId = 9,
                                name = "헤이즈",
                                imgUrl = "https://picsum.photos/38",
                            ),
                            StarInfoModel(
                                starId = 10,
                                name = "10cm",
                                imgUrl = "https://picsum.photos/38",
                            ),
                        ),
                        schoolId = 2,
                        thumbnail = "https://picsum.photos/36",
                    ),
                ),
            )
        }
        _uiState.update {
            it.copy(
                isStarImageClicked = it.todayFestivals.map { festival ->
                    persistentListOf<Boolean>().addAll(List(festival.starInfo.size) { false }).toImmutableList()
                }.toImmutableList()
            )
        }
    }

    fun onHomeUiAction(action: HomeUiAction) {
        when (action) {
            is HomeUiAction.OnDateSelected -> setSelectedDate(action.date)
            is HomeUiAction.OnAddAsLikedFestivalClick -> addLikeFestival(action.festivalTodayModel)
            is HomeUiAction.OnAddLikedFestivalClick -> setFestivalSearchBottomSheetVisible(true)
            is HomeUiAction.OnToggleStarImageClick -> toggleStarImageClicked(action.scheduleIndex, action.starIndex, action.flag)
        }
    }

    fun onFestivalUiAction(action: FestivalUiAction) {
        when (action) {
            is FestivalUiAction.OnDismiss -> setFestivalSearchBottomSheetVisible(false)
            is FestivalUiAction.OnSearchTextUpdated -> updateSearchText(action.text)
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

            is FestivalUiAction.OnDialogButtonClick -> {
                when (action.type) {
                    ButtonType.CONFIRM -> {
                        setLikedFestivalDeleteDialogVisible(false)
                        _uiState.value.deleteSelectedFestival?.let { deleteLikedFestival(it) }
                    }

                    ButtonType.CANCEL -> setLikedFestivalDeleteDialogVisible(false)
                }
            }

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

//    fun getAllFestivals() {
//        viewModelScope.launch {
//            festivalRepository.getAllFestivals()
//                .onSuccess { festivals ->
//                    _uiState.update {
//                        it.copy(
//                            allFestivals = festivals.toImmutableList(),
//                        )
//                    }
//                }
//                .onFailure { exception ->
//                    handleException(exception, this@HomeViewModel)
//                }
//        }
//    }

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

    private fun setRecentLikedFestival(schoolName: String) {
        viewModelScope.launch {
            likedFestivalRepository.setRecentLikedFestival(schoolName)
            _uiEvent.send(HomeUiEvent.NavigateBack)
            setLikedFestivalDeleteDialogVisible(false)
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
                        this[starIndex] = flag
                    }.toImmutableList()
                } else {
                    list
                }
            }.toImmutableList()
            currentState.copy(isStarImageClicked = updatedList)
        }
    }
}
