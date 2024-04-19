package com.unifest.android.feature.intro.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifest.android.core.data.repository.FestivalRepository
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.core.model.FestivalTodayModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val festivalRepository: FestivalRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(IntroUiState())
    val uiState: StateFlow<IntroUiState> = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                schools = persistentListOf(
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
                    FestivalModel(
                        3,
                        3,
                        "https://picsum.photos/36",
                        "고려대학교",
                        "고대축제",
                        "05.06",
                        "05.08",
                        126.957f,
                        37.460f,
                    ),
                    FestivalModel(
                        4,
                        4,
                        "https://picsum.photos/36",
                        "성균관대학교",
                        "성대축제",
                        "05.06",
                        "05.08",
                        126.957f,
                        37.460f,
                    ),
                    FestivalModel(
                        5,
                        5,
                        "https://picsum.photos/36",
                        "건국대학교",
                        "건대축제",
                        "05.06",
                        "05.08",
                        126.957f,
                        37.460f,
                    ),
                ),
            )
        }
    }

    fun updateSearchText(text: TextFieldValue) {
        _uiState.update {
            it.copy(searchText = text)
        }
    }

    fun initSearchText() {
        _uiState.update {
            it.copy(searchText = TextFieldValue())
        }
    }

    fun addLikeFestivals(festivals: List<FestivalModel>, onComplete: () -> Unit) {
        viewModelScope.launch {
            festivals.forEach { festival ->
                if (!festivalRepository.isFestivalExists(festival.festivalId)) {
                    festivalRepository.insertLikedFestivalAtSearch(festival)
                }
            }
            onComplete()
        }
    }
}
