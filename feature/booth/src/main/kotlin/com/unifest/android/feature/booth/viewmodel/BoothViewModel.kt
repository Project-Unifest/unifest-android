package com.unifest.android.feature.booth.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.unifest.android.core.domain.entity.BoothDetailEntity
import com.unifest.android.core.domain.entity.MenuEntity
import com.unifest.android.feature.booth.navigation.BOOTH_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BoothViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val boothId: Long = requireNotNull(savedStateHandle.get<Long>(BOOTH_ID)) { "boothId is required." }

    private val _uiState = MutableStateFlow(BoothUiState())
    val uiState: StateFlow<BoothUiState> = this._uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                boothDetailInfo = BoothDetailEntity(
                    id = 0L,
                    name = "컴공 주점",
                    category = "컴퓨터공학부 전용 부스",
                    description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                    location = "청심대 앞",
                    latitude = 37.54224856023523f,
                    longitude = 127.07605430700158f,
                    menus = listOf(
                        MenuEntity(1L, "모둠 사시미", 45000, ""),
                        MenuEntity(2L, "모둠 사시미", 45000, ""),
                        MenuEntity(3L, "모둠 사시미", 45000, ""),
                        MenuEntity(4L, "모둠 사시미", 45000, ""),
                    ),
                ),
            )
        }
    }
}
