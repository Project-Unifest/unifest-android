package com.unifest.android.feature.map.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.unifest.android.core.domain.entity.BoothDetailEntity
import com.unifest.android.core.domain.entity.Festival
import com.unifest.android.feature.map.mapper.toModel
import com.unifest.android.feature.map.model.BoothDetailModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = this._uiState.asStateFlow()

    init {
        val boothList = listOf(
            BoothDetailEntity(
                id = 1L,
                name = "컴공 주점",
                category = "",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                warning = "",
                location = "청심대 앞",
                latitude = 37.54053013863604F,
                longitude = 127.07505652524804F,
            ),
            BoothDetailEntity(
                id = 2L,
                name = "학생회 부스",
                category = "",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                location = "청심대 앞",
                latitude = 37.54111712868565F,
                longitude = 127.07839319326257F,
            ),
            BoothDetailEntity(
                id = 3L,
                name = "컴공 주점",
                category = "",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                location = "청심대 앞",
                latitude = 37.5414744247141F,
                longitude = 127.07779237844323F,
            ),
            BoothDetailEntity(
                id = 4L,
                name = "학생회 부스",
                category = "",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                location = "청심대 앞",
                latitude = 37.54224856023523F,
                longitude = 127.07605430700158F,
            ),
            BoothDetailEntity(
                id = 5L,
                name = "컴공 주점",
                category = "",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                location = "청심대 앞",
                latitude = 37.54003672313541F,
                longitude = 127.07653710462426F,
            ),
            BoothDetailEntity(
                id = 6L,
                name = "학생회 부스",
                category = "",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                location = "청심대 앞",
                latitude = 37.53998567996623F,
                longitude = 37.53998567996623F,
            ),
            BoothDetailEntity(
                id = 7L,
                name = "컴공 주점",
                category = "",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                location = "청심대 앞",
                latitude = 37.54152546686414F,
                longitude = 127.07353303052759F,
            ),
            BoothDetailEntity(
                id = 8L,
                name = "학생회 부스",
                category = "",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                location = "청심대 앞",
                latitude = 37.54047909580466F,
                longitude = 127.07398364164209F,
            ),
        )

        _uiState.update {
            it.copy(
                selectedSchoolName = "건국대학교",
                boothList = boothList
                    .map { booth -> booth.toModel() }
                    .toImmutableList(),
                selectedBoothList = persistentListOf(),
                likedFestivals = mutableListOf(
                    Festival("https://picsum.photos/36", "서울대학교", "설대축제", "05.06-05.08"),
                    Festival("https://picsum.photos/36", "연세대학교", "연대축제", "05.06-05.08"),
                    Festival("https://picsum.photos/36", "고려대학교", "고대축제", "05.06-05.08"),
                    Festival("https://picsum.photos/36", "건국대학교", "녹색지대", "05.06-05.08"),
                    Festival("https://picsum.photos/36", "성균관대학교", "성대축제", "05.06-05.08"),
                ),
                festivalSearchResults = persistentListOf(
                    Festival("https://picsum.photos/36", "서울대학교", "설대축제", "05.06-05.08"),
                    Festival("https://picsum.photos/36", "연세대학교", "연대축제", "05.06-05.08"),
                    Festival("https://picsum.photos/36", "고려대학교", "고대축제", "05.06-05.08"),
                    Festival("https://picsum.photos/36", "건국대학교", "녹색지대", "05.06-05.08"),
                    Festival("https://picsum.photos/36", "성균관대학교", "성대축제", "05.06-05.08"),
                ),
            )
        }
    }

    fun updateBoothSearchText(text: TextFieldValue) {
        _uiState.update {
            it.copy(boothSearchText = text)
        }
    }

    fun updateFestivalSearchText(text: TextFieldValue) {
        _uiState.update {
            it.copy(festivalSearchText = text)
        }
    }

    fun initSearchText() {
        _uiState.update {
            it.copy(festivalSearchText = TextFieldValue())
        }
    }

    fun setFestivalSearchBottomSheetVisible(flag: Boolean) {
        _uiState.update {
            it.copy(isFestivalSearchBottomSheetVisible = flag)
        }
    }

    fun setEnableSearchMode(flag: Boolean) {
        _uiState.update {
            it.copy(isSearchMode = flag)
        }
    }

    fun setEnableEditMode() {
        _uiState.update {
            it.copy(isEditMode = !_uiState.value.isEditMode)
        }
    }

    fun setEnablePopularMode() {
        val popularBoothList = listOf(
            BoothDetailEntity(
                id = 1L,
                name = "컴공 주점",
                category = "",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                warning = "",
                location = "청심대 앞",
                latitude = 37.54053013863604F,
                longitude = 127.07505652524804F,
            ),
            BoothDetailEntity(
                id = 2L,
                name = "학생회 부스",
                category = "",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                location = "청심대 앞",
                latitude = 37.54111712868565F,
                longitude = 127.07839319326257F,
            ),
            BoothDetailEntity(
                id = 3L,
                name = "컴공 주점",
                category = "",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                location = "청심대 앞",
                latitude = 37.5414744247141F,
                longitude = 127.07779237844323F,
            ),
            BoothDetailEntity(
                id = 4L,
                name = "학생회 부스",
                category = "",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                location = "청심대 앞",
                latitude = 37.54224856023523F,
                longitude = 127.07605430700158F,
            ),
            BoothDetailEntity(
                id = 5L,
                name = "컴공 주점",
                category = "",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                location = "청심대 앞",
                latitude = 37.54003672313541F,
                longitude = 127.07653710462426F,
            ),
        )

        _uiState.update {
            it.copy(
                selectedBoothList = popularBoothList
                    .map { popularBooth -> popularBooth.toModel() }
                    .toImmutableList(),
                isPopularMode = !_uiState.value.isPopularMode,
                isBoothSelectionMode = false,
            )
        }
    }

    fun setBoothSelectionMode(flag: Boolean) {
        _uiState.update {
            it.copy(
                isPopularMode = flag,
                isBoothSelectionMode = flag,
            )
        }
    }

    fun updateSelectedBoothList(boothList: List<BoothDetailModel>) {
        _uiState.update {
            it.copy(selectedBoothList = boothList.toImmutableList())
        }
    }

    fun setLikedFestivalDeleteDialogVisible(flag: Boolean) {
        _uiState.update {
            it.copy(isLikedFestivalDeleteDialogVisible = flag)
        }
    }
}
