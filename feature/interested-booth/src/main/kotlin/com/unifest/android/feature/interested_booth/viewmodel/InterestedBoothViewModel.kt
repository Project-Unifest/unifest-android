package com.unifest.android.feature.interested_booth.viewmodel

import androidx.lifecycle.ViewModel
import com.unifest.android.core.domain.entity.BoothDetailEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class InterestedBoothViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(InterestedBoothUiState())
    val uiState: StateFlow<InterestedBoothUiState> = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                interestedBooths = persistentListOf(
                    BoothDetailEntity(1L, "컴공 주점", "컴퓨터공학부 전용 부스"),
                    BoothDetailEntity(2L, "학생회 부스", "건국대학교 학생회 부스"),
                    BoothDetailEntity(3L, "컴공 주점", "컴퓨터공학부 전용 부스"),
                    BoothDetailEntity(4L, "학생회 부스", "건국대학교 학생회 부스"),
                    BoothDetailEntity(5L, "컴공 주점", "컴퓨터공학부 전용 부스"),
                ),
            )
        }
    }
}
