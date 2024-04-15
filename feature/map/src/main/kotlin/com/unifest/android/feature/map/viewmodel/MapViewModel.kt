package com.unifest.android.feature.map.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifest.android.core.common.ErrorHandlerActions
import com.unifest.android.core.common.handleException
import com.unifest.android.core.data.repository.BoothRepository
import com.unifest.android.core.data.repository.FestivalRepository
import com.unifest.android.core.model.BoothDetailModel
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.feature.map.mapper.toMapModel
import com.unifest.android.feature.map.model.BoothDetailMapModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val festivalRepository: FestivalRepository,
    private val boothRepository: BoothRepository,
) : ViewModel(), ErrorHandlerActions {
    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    init {
        getAllFestivals()
        getPopularBooths()

        val boothList = listOf(
            BoothDetailModel(
                id = 1L,
                name = "컴공 주점",
                category = "",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                warning = "",
                location = "청심대 앞",
                latitude = 37.54053013863604F,
                longitude = 127.07505652524804F,
                menus = emptyList(),
            ),
            BoothDetailModel(
                id = 2L,
                name = "학생회 부스",
                category = "",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                warning = "",
                location = "청심대 앞",
                latitude = 37.54111712868565F,
                longitude = 127.07839319326257F,
                menus = emptyList(),
            ),
            BoothDetailModel(
                id = 3L,
                name = "컴공 주점",
                category = "",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                warning = "",
                location = "청심대 앞",
                latitude = 37.5414744247141F,
                longitude = 127.07779237844323F,
                menus = emptyList(),
            ),
            BoothDetailModel(
                id = 4L,
                name = "학생회 부스",
                category = "",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                warning = "",
                location = "청심대 앞",
                latitude = 37.54224856023523F,
                longitude = 127.07605430700158F,
                menus = emptyList(),
            ),
            BoothDetailModel(
                id = 5L,
                name = "컴공 주점",
                category = "",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                warning = "",
                location = "청심대 앞",
                latitude = 37.54003672313541F,
                longitude = 127.07653710462426F,
                menus = emptyList(),
            ),
            BoothDetailModel(
                id = 6L,
                name = "학생회 부스",
                category = "",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                warning = "",
                location = "청심대 앞",
                latitude = 37.53998567996623F,
                longitude = 37.53998567996623F,
                menus = emptyList(),
            ),
            BoothDetailModel(
                id = 7L,
                name = "컴공 주점",
                category = "",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                warning = "",
                location = "청심대 앞",
                latitude = 37.54152546686414F,
                longitude = 127.07353303052759F,
                menus = emptyList(),
            ),
            BoothDetailModel(
                id = 8L,
                name = "학생회 부스",
                category = "",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                warning = "",
                location = "청심대 앞",
                latitude = 37.54047909580466F,
                longitude = 127.07398364164209F,
                menus = emptyList(),
            ),
        )

        _uiState.update {
            it.copy(
                selectedSchoolName = "건국대학교",
                boothList = boothList
                    .map { booth -> booth.toMapModel() }
                    .toImmutableList(),
                selectedBoothList = persistentListOf(),
                likedFestivals = mutableListOf(
                    FestivalModel("https://picsum.photos/36", "서울대학교", "설대축제", "05.06-05.08"),
                    FestivalModel("https://picsum.photos/36", "연세대학교", "연대축제", "05.06-05.08"),
                    FestivalModel("https://picsum.photos/36", "고려대학교", "고대축제", "05.06-05.08"),
                    FestivalModel("https://picsum.photos/36", "건국대학교", "녹색지대", "05.06-05.08"),
                    FestivalModel("https://picsum.photos/36", "성균관대학교", "성대축제", "05.06-05.08"),
                ),
                festivalSearchResults = persistentListOf(
                    FestivalModel("https://picsum.photos/36", "서울대학교", "설대축제", "05.06-05.08"),
                    FestivalModel("https://picsum.photos/36", "연세대학교", "연대축제", "05.06-05.08"),
                    FestivalModel("https://picsum.photos/36", "고려대학교", "고대축제", "05.06-05.08"),
                    FestivalModel("https://picsum.photos/36", "건국대학교", "녹색지대", "05.06-05.08"),
                    FestivalModel("https://picsum.photos/36", "성균관대학교", "성대축제", "05.06-05.08"),
                ),
            )
        }
    }

    fun getAllFestivals() {
        viewModelScope.launch {
            festivalRepository.getAllFestivals()
                .onSuccess { festivals ->
                    _uiState.update {
                        it.copy(
                            festivalList = festivals.toImmutableList(),
                        )
                    }
                }
                .onFailure { exception ->
                    handleException(exception, this@MapViewModel)
                }
        }
    }

    fun getPopularBooths() {
        viewModelScope.launch {
            boothRepository.getPopularBooths(_uiState.value.festivalId)
                .onSuccess { booths ->
                    _uiState.update {
                        it.copy(
                            popularBoothList = booths.toImmutableList(),
                        )
                    }
                }.onFailure { exception ->
                    handleException(exception, this@MapViewModel)
                }
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
            BoothDetailModel(
                id = 1L,
                name = "컴공 주점",
                category = "",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                warning = "",
                location = "청심대 앞",
                latitude = 37.54053013863604F,
                longitude = 127.07505652524804F,
                menus = emptyList(),
            ),
            BoothDetailModel(
                id = 2L,
                name = "학생회 부스",
                category = "",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                warning = "",
                location = "청심대 앞",
                latitude = 37.54111712868565F,
                longitude = 127.07839319326257F,
                menus = emptyList(),
            ),
            BoothDetailModel(
                id = 3L,
                name = "컴공 주점",
                category = "",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                warning = "",
                location = "청심대 앞",
                latitude = 37.5414744247141F,
                longitude = 127.07779237844323F,
                menus = emptyList(),
            ),
            BoothDetailModel(
                id = 4L,
                name = "학생회 부스",
                category = "",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                location = "청심대 앞",
                warning = "",
                latitude = 37.54224856023523F,
                longitude = 127.07605430700158F,
                menus = emptyList(),
            ),
            BoothDetailModel(
                id = 5L,
                name = "컴공 주점",
                category = "",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                warning = "",
                location = "청심대 앞",
                latitude = 37.54003672313541F,
                longitude = 127.07653710462426F,
                menus = emptyList(),
            ),
        )

        _uiState.update {
            it.copy(
                selectedBoothList = popularBoothList.map { it.toMapModel() }.toImmutableList(),
                isPopularMode = !_uiState.value.isPopularMode,
                isBoothSelectionMode = false,
            )
        }
    }

    fun setBoothSelectionMode(flag: Boolean) {
        _uiState.update {
            it.copy(
                isPopularMode = false,
                isBoothSelectionMode = flag,
            )
        }
    }

    fun updateSelectedBoothList(boothList: List<BoothDetailMapModel>) {
        _uiState.update {
            it.copy(selectedBoothList = boothList.toImmutableList())
        }
    }

    fun setLikedFestivalDeleteDialogVisible(flag: Boolean) {
        _uiState.update {
            it.copy(isLikedFestivalDeleteDialogVisible = flag)
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
}
