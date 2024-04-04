package com.unifest.android.feature.intro.viewmodel

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.lifecycle.ViewModel
import com.unifest.android.core.domain.entity.School
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@OptIn(ExperimentalFoundationApi::class)
@HiltViewModel
class IntroViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(IntroUiState())
    val uiState: StateFlow<IntroUiState> = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                // 임시 데이터
                schools = persistentListOf(
                    School("school_image_url_1", "서울대학교", "설대축제", "05.06-05.08"),
                    School("school_image_url_2", "연세대학교", "연대축제", "05.06-05.08"),
                    School("school_image_url_3", "고려대학교", "고대축제", "05.06-05.08"),
                    School("school_image_url_4", "건국대학교", "녹색지대", "05.06-05.08"),
                    School("school_image_url_5", "성균관대", "성대축제", "05.06-05.08"),
                ),
            )
        }
    }
}
