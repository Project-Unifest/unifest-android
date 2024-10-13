package com.unifest.android.feature.intro.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifest.android.core.common.ErrorHandlerActions
import com.unifest.android.core.common.handleException
import com.unifest.android.core.data.repository.FestivalRepository
import com.unifest.android.core.data.repository.LikedFestivalRepository
import com.unifest.android.core.data.repository.OnboardingRepository
import com.unifest.android.core.model.FestivalModel
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
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val onboardingRepository: OnboardingRepository,
    private val festivalRepository: FestivalRepository,
    private val likedFestivalRepository: LikedFestivalRepository,
) : ViewModel(), ErrorHandlerActions {
    private val _uiState = MutableStateFlow(IntroUiState())
    val uiState: StateFlow<IntroUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<IntroUiEvent>()
    val uiEvent: Flow<IntroUiEvent> = _uiEvent.receiveAsFlow()

    init {
        getAllFestivals()
    }

    fun onAction(action: IntroUiAction) {
        when (action) {
            is IntroUiAction.OnSearchTextUpdated -> updateSearchText(action.searchText)
            is IntroUiAction.OnSearchTextCleared -> clearSearchText()
            is IntroUiAction.OnSearch -> searchSchool(action.searchText.text)
            is IntroUiAction.OnRegionTapClicked -> searchRegion(action.region)
            is IntroUiAction.OnClearSelectionClick -> clearSelectedFestivals()
            is IntroUiAction.OnFestivalSelected -> addSelectedFestival(action.festival)
            is IntroUiAction.OnFestivalDeselected -> removeSelectedFestivals(action.festival)
            is IntroUiAction.OnAddCompleteClick -> addLikedFestivals()
            is IntroUiAction.OnRetryClick -> refresh(action.error)
        }
    }

    private fun updateSearchText(text: TextFieldValue) {
        _uiState.update {
            it.copy(searchText = text)
        }
    }

    private fun clearSearchText() {
        _uiState.update {
            it.copy(searchText = TextFieldValue())
        }
    }

    private fun getAllFestivals() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            festivalRepository.getAllFestivals()
                .onSuccess { festivals ->
                    _uiState.update {
                        it.copy(festivals = festivals.toImmutableList())
                    }
                }
                .onFailure { exception ->
                    handleException(exception, this@IntroViewModel)
                }
            _uiState.update {
                it.copy(isLoading = false)
            }
        }
    }

    private fun searchSchool(searchText: String) {
        if (searchText.isEmpty()) return

        viewModelScope.launch {
            _uiState.update {
                it.copy(isSearchLoading = true)
            }
            festivalRepository.searchSchool(searchText)
                .onSuccess { festivals ->
                    if (_uiState.value.selectedRegion == "전체") {
                        _uiState.update {
                            it.copy(festivals = festivals.toImmutableList())
                        }
                    } else {
                        festivals.filter { festival ->
                            festival.region == _uiState.value.selectedRegion
                        }.let { filteredFestivals ->
                            _uiState.update {
                                it.copy(festivals = filteredFestivals.toImmutableList())
                            }
                        }
                    }
                }.onFailure { exception ->
                    handleException(exception, this@IntroViewModel)
                }
            _uiState.update {
                it.copy(isSearchLoading = false)
            }
        }
    }

    private fun searchRegion(region: String) {
        viewModelScope.launch {
//            _uiState.update {
//                it.copy(isSearchLoading = true)
//            }
            if (region == "전체") {
                getAllFestivals()
            } else {
                _uiState.update {
                    it.copy(selectedRegion = region)
                }
                festivalRepository.searchRegion(region)
                    .onSuccess { festivals ->
                        if (_uiState.value.searchText.text.isEmpty()) {
                            _uiState.update {
                                it.copy(festivals = festivals.toImmutableList())
                            }
                        } else {
                            festivals.filter { festival ->
                                // 축제 이름이 영어 일 수 있으므로, 영어 대소문자를 구분하지 않는 옵션(ignoreCase = true) 추가
                                festival.schoolName.contains(_uiState.value.searchText.text, ignoreCase = true) ||
                                    festival.festivalName.contains(_uiState.value.searchText.text, ignoreCase = true)
                            }.let { filteredFestivals ->
                                _uiState.update {
                                    it.copy(festivals = filteredFestivals.toImmutableList())
                                }
                            }
                        }
                        _uiState.update {
                            it.copy(festivals = festivals.toImmutableList())
                        }
                    }.onFailure { exception ->
                        handleException(exception, this@IntroViewModel)
                    }
            }
//            _uiState.update {
//                it.copy(isSearchLoading = false)
//            }
        }
    }

    private fun clearSelectedFestivals() {
        _uiState.update {
            it.copy(selectedFestivals = persistentListOf())
        }
    }

    private fun addSelectedFestival(festival: FestivalModel) {
        _uiState.update {
            it.copy(
                selectedFestivals = it.selectedFestivals.add(festival),
            )
        }
    }

    private fun removeSelectedFestivals(festival: FestivalModel) {
        _uiState.update {
            it.copy(
                selectedFestivals = it.selectedFestivals.remove(festival),
            )
        }
    }

    // TODO 추후에 OO대로 고정하지 않고, 사용자가 선택한 학교로 변경
    private fun addLikedFestivals() {
        viewModelScope.launch {
            _uiState.value.selectedFestivals.forEach { festival ->
                likedFestivalRepository.insertLikedFestivalAtSearch(festival)
            }
            likedFestivalRepository.setRecentLikedFestival("한국교통대")
            likedFestivalRepository.setRecentLikedFestivalId(2L)
            onboardingRepository.completeIntro(true)
            _uiEvent.send(IntroUiEvent.NavigateToMain)
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

    private fun refresh(error: ErrorType) {
        when (error) {
            ErrorType.NETWORK -> setNetworkErrorDialogVisible(false)
            ErrorType.SERVER -> setServerErrorDialogVisible(false)
        }
    }
}
