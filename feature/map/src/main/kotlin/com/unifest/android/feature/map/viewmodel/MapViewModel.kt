package com.unifest.android.feature.map.viewmodel

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.lifecycle.ViewModel
import com.unifest.android.core.domain.entity.BoothDetailEntity
import com.unifest.android.core.domain.entity.BoothSpot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@OptIn(ExperimentalFoundationApi::class)
@HiltViewModel
class MapViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = this._uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                selectedSchoolName = "건국대학교",
                boothSpots = persistentListOf(
                    BoothSpot(37.54053013863604, 127.07505652524804),
                    BoothSpot(37.54111712868565, 127.07839319326257),
                    BoothSpot(37.5414744247141, 127.07779237844323),
                    BoothSpot(37.54224856023523, 127.07605430700158),
                    BoothSpot(37.54003672313541, 127.07653710462426),
                    BoothSpot(37.53998567996623, 37.53998567996623),
                    BoothSpot(37.54152546686414, 127.07353303052759),
                    BoothSpot(37.54047909580466, 127.07398364164209),
                ),
                boothList = persistentListOf(
                    BoothDetailEntity(
                        id = 1L,
                        name = "컴공 주점",
                        category = "",
                        description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                        warning = "",
                        location = "청심대 앞",
                        latitude = 0f,
                        longitude = 0f,
                        menus = emptyList(),
                    ),
                    BoothDetailEntity(
                        id = 1L,
                        name = "컴공 주점",
                        category = "",
                        description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                        warning = "",
                        location = "청심대 앞",
                        latitude = 0f,
                        longitude = 0f,
                        menus = emptyList(),
                    ),
                    BoothDetailEntity(
                        id = 1L,
                        name = "컴공 주점",
                        category = "",
                        description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                        warning = "",
                        location = "청심대 앞",
                        latitude = 0f,
                        longitude = 0f,
                        menus = emptyList(),
                    ),
                    BoothDetailEntity(
                        id = 1L,
                        name = "컴공 주점",
                        category = "",
                        description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                        warning = "",
                        location = "청심대 앞",
                        latitude = 0f,
                        longitude = 0f,
                        menus = emptyList(),
                    ),
                    BoothDetailEntity(
                        id = 1L,
                        name = "컴공 주점",
                        category = "",
                        description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                        warning = "",
                        location = "청심대 앞",
                        latitude = 0f,
                        longitude = 0f,
                        menus = emptyList(),
                    ),
                ),
            )
        }
    }
}
