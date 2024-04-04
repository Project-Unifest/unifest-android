package com.unifest.android.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.domain.entity.FestivalEventEntity
import com.unifest.android.core.domain.entity.IncomingFestivalEventEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                incomingEvents = persistentListOf(
                    IncomingFestivalEventEntity(
                        imageRes = R.drawable.ic_waiting,
                        name = "녹색지대",
                        dates = "05/21(화) - 05/23(목)",
                        location = "건국대학교 서울캠퍼스",
                    ),
                    IncomingFestivalEventEntity(
                        imageRes = R.drawable.ic_waiting,
                        name = "녹색지대",
                        dates = "05/21(화) - 05/23(목)",
                        location = "건국대학교 서울캠퍼스",
                    ),
                ),
                festivalEvents = persistentListOf(
                    FestivalEventEntity(
                        id = 1,
                        date = "5/21(화)",
                        name = "녹색지대 DAY 1",
                        location = "건국대학교 서울캠퍼스",
                        celebrityImages = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15),
                    ),
                    FestivalEventEntity(
                        id = 2,
                        date = "5/21(화)",
                        name = "녹색지대 DAY 1",
                        location = "건국대학교 서울캠퍼스",
                        celebrityImages = listOf(0, 1, 2),
                    ),
                    FestivalEventEntity(
                        id = 3,
                        date = "5/21(화)",
                        name = "녹색지대 DAY 1",
                        location = "건국대학교 서울캠퍼스",
                        celebrityImages = listOf(0, 1, 2),
                    ),
                ),
            )
        }
    }
}
