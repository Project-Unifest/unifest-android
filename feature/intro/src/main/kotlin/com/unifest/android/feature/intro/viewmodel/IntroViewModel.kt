package com.unifest.android.feature.intro.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.unifest.android.core.domain.entity.Festival
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(IntroUiState())
    val uiState: StateFlow<IntroUiState> = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                schools = persistentListOf(
                    Festival("https://picsum.photos/36", "서울대학교", "설대축제", "05.06-05.08"),
                    Festival("https://picsum.photos/36", "연세대학교", "연대축제", "05.06-05.08"),
                    Festival("https://picsum.photos/36", "고려대학교", "고대축제", "05.06-05.08"),
                    Festival("https://picsum.photos/36", "건국대학교", "녹색지대", "05.06-05.08"),
                    Festival("https://picsum.photos/36", "성균관대학교", "성대축제", "05.06-05.08"),
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
}
