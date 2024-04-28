package com.unifest.android.feature.intro.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifest.android.core.data.repository.LikedFestivalRepository
import com.unifest.android.core.data.repository.OnboardingRepository
import com.unifest.android.core.model.FestivalModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
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
    private val likedFestivalRepository: LikedFestivalRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(IntroUiState())
    val uiState: StateFlow<IntroUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<IntroUiEvent>()
    val uiEvent: Flow<IntroUiEvent> = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            if (onboardingRepository.checkIntroCompletion()) {
                _uiEvent.send(IntroUiEvent.NavigateToMain)
            } else {
                _uiState.update {
                    it.copy(
                        festivals = persistentListOf(
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
                            FestivalModel(
                                3,
                                3,
                                "https://picsum.photos/36",
                                "고려대학교",
                                "고대축제",
                                "2024-04-21",
                                "2024-04-23",
                                126.957f,
                                37.460f,
                            ),
                            FestivalModel(
                                4,
                                4,
                                "https://picsum.photos/36",
                                "성균관대학교",
                                "성대축제",
                                "2024-04-21",
                                "2024-04-23",
                                126.957f,
                                37.460f,
                            ),
                            FestivalModel(
                                5,
                                5,
                                "https://picsum.photos/36",
                                "건국대학교",
                                "건대축제",
                                "2024-04-21",
                                "2024-04-23",
                                126.957f,
                                37.460f,
                            ),
                        ),
                    )
                }
                _uiState.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }

    fun onAction(action: IntroUiAction) {
        when (action) {
            is IntroUiAction.OnSearchTextUpdated -> updateSearchText(action.text)
            is IntroUiAction.OnSearchTextCleared -> clearSearchText()
            is IntroUiAction.OnClearSelectionClick -> clearSelectedFestivals()
            is IntroUiAction.OnFestivalSelected -> addSelectedFestival(action.festival)
            is IntroUiAction.OnFestivalDeselected -> removeSelectedFestivals(action.festival)
            is IntroUiAction.OnAddCompleteClick -> addLikedFestivals()
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

    private fun clearSelectedFestivals() {
        _uiState.update {
            it.copy(selectedFestivals = emptyList())
        }
    }

    private fun addSelectedFestival(festival: FestivalModel) {
        _uiState.update {
            it.copy(
                selectedFestivals = it.selectedFestivals.toMutableList().apply { add(festival) },
            )
        }
    }

    private fun removeSelectedFestivals(festival: FestivalModel) {
        _uiState.update {
            it.copy(
                selectedFestivals = it.selectedFestivals.toMutableList().apply { remove(festival) },
            )
        }
    }

    private fun addLikedFestivals() {
        viewModelScope.launch {
            _uiState.value.selectedFestivals.forEach { festival ->
                likedFestivalRepository.insertLikedFestivalAtSearch(festival)
            }
            onboardingRepository.completeIntro(true)
            _uiEvent.send(IntroUiEvent.NavigateToMain)
        }
    }
}
