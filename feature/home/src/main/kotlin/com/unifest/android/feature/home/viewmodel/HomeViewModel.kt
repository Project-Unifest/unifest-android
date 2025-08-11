package com.unifest.android.feature.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifest.android.core.common.ErrorHandlerActions
import com.unifest.android.core.common.handleException
import com.unifest.android.core.data.api.repository.FestivalRepository
import com.unifest.android.core.data.api.repository.HomeRepository
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
    private val homeRepository: HomeRepository,
) : ViewModel(), ErrorHandlerActions {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<HomeUiEvent>()
    val uiEvent: Flow<HomeUiEvent> = _uiEvent.receiveAsFlow()

    init {
        getIncomingFestivals()
        getAllFestivals()
        initStarImageClicked()
        getHomeCardNews()
        getTodayFestivals(_uiState.value.selectedDate.toString())
    }

    private fun getHomeCardNews() {
        Log.d("HomeViewModel", "getHomeCardNews called")
        viewModelScope.launch {
            homeRepository.getHomeInfo()
                .onSuccess { homeInfo ->
                    _uiState.update {
                        it.copy(
                            cardNews = homeInfo.homeCardList.toImmutableList(),
                            tips = homeInfo.homeTipList.toImmutableList(),
                            isDataReady = true,
                        )
                    }
                }
                .onFailure { exception ->
                    handleException(exception, this@HomeViewModel)
                }
        }
    }

    fun onHomeUiAction(action: HomeUiAction) {
        when (action) {
            is HomeUiAction.OnDateSelected -> {
                _uiState.update {
                    it.copy(isDataReady = false)
                }
                setSelectedDate(action.date)
                getTodayFestivals(action.date.toString())
            }
            // is HomeUiAction.OnToggleStarImageClick -> toggleStarImageClicked(action.scheduleIndex, action.starIndex, action.flag)
            is HomeUiAction.OnStarImageClick -> showStarImageDialog(action.scheduleIndex, action.starIndex)
            is HomeUiAction.OnStarImageDialogDismiss -> hideStarImageDialog()
            is HomeUiAction.OnClickWeekMode -> setWeekMode(!_uiState.value.isWeekMode)
            is HomeUiAction.OnCardNewsClick -> navigateToCardNews(action.selectedCardNews.detailImgUrl)
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
                            isDataReady = true,
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

    private fun initStarImageClicked() {
        _uiState.update {
            it.copy(
                isStarImageClicked = it.todayFestivals.map { festival ->
                    persistentListOf<Boolean>().addAll(List(festival.starInfo.size) { false }).toImmutableList()
                }.toImmutableList(),
            )
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

//    private fun toggleStarImageClicked(scheduleIndex: Int, starIndex: Int, flag: Boolean) {
//        _uiState.update { currentState ->
//            val updatedList = currentState.isStarImageClicked.mapIndexed { index, list ->
//                if (index == scheduleIndex) {
//                    list.toMutableList().apply {
//                        if (starIndex < this.size) {
//                            this[starIndex] = flag
//                        }
//                    }.toImmutableList()
//                } else {
//                    list
//                }
//            }.toImmutableList()
//            currentState.copy(isStarImageClicked = updatedList)
//        }
//    }

    private fun showStarImageDialog(scheduleIndex: Int, starIndex: Int) {
        _uiState.update {
            it.copy(
                isStarImageDialogVisible = true,
                selectedStar = it.todayFestivals[scheduleIndex].starInfo[starIndex],
            )
        }
    }

    private fun hideStarImageDialog() {
        _uiState.update {
            it.copy(
                isStarImageDialogVisible = false,
                selectedStar = null,
            )
        }
    }

    private fun setWeekMode(flag: Boolean) {
        _uiState.update {
            it.copy(isWeekMode = flag)
        }
    }

    private fun navigateToCardNews(imgUrl: String) {
        viewModelScope.launch {
            _uiEvent.send(HomeUiEvent.NavigateToCardNews(imgUrl))
        }
    }
}
