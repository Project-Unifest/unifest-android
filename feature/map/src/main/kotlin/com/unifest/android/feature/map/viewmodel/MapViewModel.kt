package com.unifest.android.feature.map.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifest.android.core.common.ButtonType
import com.unifest.android.core.common.ErrorHandlerActions
import com.unifest.android.core.common.FestivalUiAction
import com.unifest.android.core.common.UiText
import com.unifest.android.core.common.handleException
import com.unifest.android.core.common.utils.matchesSearchText
import com.unifest.android.core.data.repository.BoothRepository
import com.unifest.android.core.data.repository.FestivalRepository
import com.unifest.android.core.data.repository.LikedFestivalRepository
import com.unifest.android.core.data.repository.OnboardingRepository
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.feature.map.mapper.toMapModel
import com.unifest.android.feature.map.model.BoothMapModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val onboardingRepository: OnboardingRepository,
    private val festivalRepository: FestivalRepository,
    private val boothRepository: BoothRepository,
    private val likedFestivalRepository: LikedFestivalRepository,
) : ViewModel(), ErrorHandlerActions {
    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<MapUiEvent>()
    val uiEvent: Flow<MapUiEvent> = _uiEvent.receiveAsFlow()

    init {
        requestLocationPermission()
        searchSchoolName()
        getAllFestivals()
        checkMapOnboardingCompletion()
        checkFestivalOnboardingCompletion()
        observeLikedFestivals()
    }

    private fun requestLocationPermission() {
        viewModelScope.launch {
            _uiEvent.send(MapUiEvent.RequestLocationPermission)
        }
    }

    fun onPermissionResult(isGranted: Boolean) {
        if (!isGranted) {
            _uiState.update { it.copy(isPermissionDialogVisible = true) }
        }
    }

    fun onMapUiAction(action: MapUiAction) {
        when (action) {
            is MapUiAction.OnTitleClick -> setFestivalSearchBottomSheetVisible(true)
            is MapUiAction.OnSearchTextUpdated -> updateBoothSearchText(action.searchText)
            is MapUiAction.OnSearchTextCleared -> clearBoothSearchText()
            is MapUiAction.OnSearch -> searchBooth()
            is MapUiAction.OnTooltipClick -> completeMapOnboarding()
            // is MapUiAction.OnBoothMarkerClick -> updateSelectedBoothList(action.booths)
            is MapUiAction.OnBoothMarkerClick -> updateSelectedBooth(action.booth)
            is MapUiAction.OnTogglePopularBooth -> setEnablePopularMode()
            is MapUiAction.OnBoothItemClick -> navigateToBoothDetail(action.boothId)
            is MapUiAction.OnRetryClick -> refresh(action.error)
            is MapUiAction.OnBoothTypeChipClick -> updateSelectedBoothChipList(action.chipName)
            is MapUiAction.OnPermissionDialogButtonClick -> handlePermissionDialogButtonClick(action.buttonType)
        }
    }

    fun onFestivalUiAction(action: FestivalUiAction) {
        when (action) {
            is FestivalUiAction.OnDismiss -> setFestivalSearchBottomSheetVisible(false)
            is FestivalUiAction.OnSearchTextUpdated -> updateFestivalSearchText(action.searchText)
            is FestivalUiAction.OnSearchTextCleared -> clearFestivalSearchText()
            is FestivalUiAction.OnEnableSearchMode -> setEnableSearchMode(action.flag)
            is FestivalUiAction.OnEnableEditMode -> setEnableEditMode()
            is FestivalUiAction.OnLikedFestivalSelected -> {
                completeFestivalOnboarding()
                setRecentLikedFestival(action.festival.schoolName)
            }

            is FestivalUiAction.OnAddClick -> addLikeFestival(action.festival)
            is FestivalUiAction.OnDeleteIconClick -> {
                _uiState.update {
                    it.copy(deleteSelectedFestival = action.deleteSelectedFestival)
                }
                setLikedFestivalDeleteDialogVisible(true)
            }

            is FestivalUiAction.OnDeleteDialogButtonClick -> handleDeleteDialogButtonClick(action.buttonType)
            is FestivalUiAction.OnTooltipClick -> completeFestivalOnboarding()
        }
    }

    private fun updateSelectedBoothChipList(chipName: String) {
        _uiState.update {
            val newChips = it.selectedBoothTypeChips.toMutableList().apply {
                if (contains(chipName)) {
                    remove(chipName)
                } else {
                    add(chipName)
                }
            }.toImmutableList()
            it.copy(selectedBoothTypeChips = newChips)
        }
        filterBoothsByType(_uiState.value.selectedBoothTypeChips)
    }

    private fun handlePermissionDialogButtonClick(buttonType: PermissionDialogButtonType) {
        when (buttonType) {
            PermissionDialogButtonType.CONFIRM -> {
                viewModelScope.launch {
                    _uiState.update {
                        it.copy(isPermissionDialogVisible = false)
                    }
                    _uiEvent.send(MapUiEvent.RequestLocationPermission)
                }
            }

            PermissionDialogButtonType.GO_TO_APP_SETTINGS -> {
                viewModelScope.launch {
                    _uiEvent.send(MapUiEvent.GoToAppSettings)
                }
            }

            PermissionDialogButtonType.DISMISS -> {
                _uiState.update {
                    it.copy(isPermissionDialogVisible = false)
                }
            }
        }
    }

    private fun filterBoothsByType(chipList: List<String>) {
        val englishCategories = chipList.map { it.toEnglishCategory() }
        val filteredBooths = _uiState.value.boothList.filter { booth ->
            englishCategories.contains(booth.category)
        }
        _uiState.update {
            it.copy(filteredBoothsList = filteredBooths.toImmutableList())
        }
    }

    private fun observeLikedFestivals() {
        viewModelScope.launch {
            likedFestivalRepository.getLikedFestivals().collect { likedFestivalList ->
                _uiState.update {
                    it.copy(likedFestivals = likedFestivalList.toPersistentList())
                }
            }
        }
    }

    private fun getAllFestivals() {
        viewModelScope.launch {
            festivalRepository.getAllFestivals()
                .onSuccess { festivals ->
                    _uiState.update {
                        it.copy(festivals = festivals.toImmutableList())
                    }
                }
                .onFailure { exception ->
                    handleException(exception, this@MapViewModel)
                }
        }
    }

    private fun searchSchoolName() {
        viewModelScope.launch {
            festivalRepository.searchSchool(likedFestivalRepository.getRecentLikedFestival())
                .onSuccess { festivals ->
                    if (festivals.isNotEmpty()) {
                        _uiState.update {
                            it.copy(festivalInfo = festivals[0])
                        }
                    }
                }
                .onFailure { exception ->
                    handleException(exception, this@MapViewModel)
                }
        }
    }

    fun getPopularBooths() {
        viewModelScope.launch {
            boothRepository.getPopularBooths(1)
                .onSuccess { booths ->
                    _uiState.update {
                        it.copy(popularBoothList = booths.toImmutableList())
                    }
                    if (_uiState.value.isPopularMode) {
                        _uiState.update { currentState ->
                            currentState.copy(
                                selectedBoothList = booths.map { it.toMapModel() }.toImmutableList(),
                            )
                        }
                    }
                }.onFailure { exception ->
                    handleException(exception, this@MapViewModel)
                }
        }
    }

    fun getAllBooths() {
        viewModelScope.launch {
            boothRepository.getAllBooths(1)
                .onSuccess { booths ->
                    _uiState.update {
                        it.copy(
                            boothList = booths
                                .map { booth -> booth.toMapModel() }
                                .toImmutableList(),
                        )
                    }
                    filterBoothsByType(_uiState.value.selectedBoothTypeChips)
                }.onFailure { exception ->
                    handleException(exception, this@MapViewModel)
                }
        }
    }

    private fun checkMapOnboardingCompletion() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isMapOnboardingCompleted = onboardingRepository.checkMapOnboardingCompletion())
            }
        }
    }

    private fun completeMapOnboarding() {
        viewModelScope.launch {
            onboardingRepository.completeMapOnboarding(true)
            _uiState.update {
                it.copy(isMapOnboardingCompleted = true)
            }
        }
    }

    private fun navigateToBoothDetail(boothId: Long) {
        viewModelScope.launch {
            _uiEvent.send(MapUiEvent.NavigateToBoothDetail(boothId))
        }
    }

    private fun refresh(error: ErrorType) {
        searchSchoolName()
        getPopularBooths()
        when (error) {
            ErrorType.NETWORK -> setNetworkErrorDialogVisible(false)
            ErrorType.SERVER -> setServerErrorDialogVisible(false)
        }
    }

    private fun updateBoothSearchText(searchText: TextFieldValue) {
        _uiState.update {
            it.copy(
                boothSearchText = searchText,
                festivalSearchResults = it.festivals.filter { festival ->
                    festival.schoolName.replace(" ", "").contains(searchText.text.replace(" ", ""), ignoreCase = true) ||
                        festival.festivalName.replace(" ", "").contains(searchText.text.replace(" ", ""), ignoreCase = true)
                }.toImmutableList(),
            )
        }
    }

    private fun clearBoothSearchText() {
        _uiState.update {
            it.copy(boothSearchText = TextFieldValue())
        }
    }

    private fun searchBooth() {
        val searchBoothResult = _uiState.value.boothList.filter {
            it.name.replace(" ", "").contains(_uiState.value.boothSearchText.text.replace(" ", ""), ignoreCase = true) ||
                it.description.replace(" ", "").contains(_uiState.value.boothSearchText.text.replace(" ", ""), ignoreCase = true)
        }
        updateSelectedBoothList(searchBoothResult)
        if (searchBoothResult.isEmpty()) {
            viewModelScope.launch {
                _uiEvent.send(MapUiEvent.ShowSnackBar(UiText.StringResource(R.string.map_search_no_result_message)))
            }
        }
    }

    private fun updateFestivalSearchText(searchText: TextFieldValue) {
        _uiState.update {
            it.copy(
                festivalSearchText = searchText,
                festivalSearchResults = it.festivals.filter { festival ->
                    matchesSearchText(festival, searchText)
                }.toImmutableList(),
            )
        }
    }

    private fun clearFestivalSearchText() {
        _uiState.update {
            it.copy(
                festivalSearchText = TextFieldValue(),
                festivalSearchResults = persistentListOf(),
            )
        }
    }

    private fun setFestivalSearchBottomSheetVisible(flag: Boolean) {
        _uiState.update {
            it.copy(isFestivalSearchBottomSheetVisible = flag)
        }
    }

    private fun setRecentLikedFestival(schoolName: String) {
        viewModelScope.launch {
            if (schoolName == likedFestivalRepository.getRecentLikedFestival()) {
                // likedFestivalRepository.setRecentLikedFestival(schoolName)
                setFestivalSearchBottomSheetVisible(false)
            } else {
                _uiEvent.send(MapUiEvent.ShowToast(UiText.StringResource(R.string.menu_interest_festival_snack_bar)))
            }
        }
    }

    private fun addLikeFestival(festival: FestivalModel) {
        viewModelScope.launch {
            likedFestivalRepository.insertLikedFestivalAtSearch(festival)
        }
    }

    private fun handleDeleteDialogButtonClick(buttonType: ButtonType) {
        when (buttonType) {
            ButtonType.CONFIRM -> {
                setLikedFestivalDeleteDialogVisible(false)
                _uiState.value.deleteSelectedFestival?.let { deleteLikedFestival(it) }
            }

            ButtonType.CANCEL -> setLikedFestivalDeleteDialogVisible(false)
        }
    }

    private fun setEnableSearchMode(flag: Boolean) {
        _uiState.update {
            it.copy(isSearchMode = flag)
        }
    }

    private fun setEnableEditMode() {
        _uiState.update {
            it.copy(isEditMode = !_uiState.value.isEditMode)
        }
    }

    private fun setEnablePopularMode() {
        if (_uiState.value.isBoothSelectionMode) {
            viewModelScope.launch {
                boothRepository.getPopularBooths(1)
                    .onSuccess { booths ->
                        _uiState.update { currentState ->
                            currentState.copy(
                                selectedBoothList = booths.map { it.toMapModel() }.toImmutableList(),
                                isBoothSelectionMode = false,
                                filteredBoothsList = currentState.filteredBoothsList.map { booth ->
                                    booth.copy(isSelected = false)
                                }.toImmutableList(),
                            )
                        }
                        delay(500)
                        _uiState.update { currentState ->
                            currentState.copy(
                                selectedBoothList = _uiState.value.popularBoothList.map { it.toMapModel() }.toImmutableList(),
                                isPopularMode = true,
                            )
                        }
                    }.onFailure {
                        _uiEvent.send(MapUiEvent.ShowSnackBar(UiText.StringResource(R.string.map_popular_booth_update_failed_message)))
                    }
            }
        } else {
            _uiState.update {
                it.copy(
                    selectedBoothList = _uiState.value.popularBoothList.map { it.toMapModel() }.toImmutableList(),
                    isPopularMode = !_uiState.value.isPopularMode,
                )
            }
        }
    }

    private fun updateSelectedBooth(booth: BoothMapModel) {
        if (_uiState.value.isPopularMode) {
            viewModelScope.launch {
                _uiState.update {
                    it.copy(isPopularMode = false)
                }
                delay(500)
                _uiState.update {
                    it.copy(
                        isBoothSelectionMode = true,
                        selectedBoothList = listOf(booth).toImmutableList(),
                    )
                }
            }
        } else {
            _uiState.update {
                it.copy(
                    isBoothSelectionMode = true,
                    selectedBoothList = listOf(booth).toImmutableList(),
                )
            }
        }
        _uiState.update {
            it.copy(
                filteredBoothsList = it.filteredBoothsList.map { boothMapModel ->
                    if (boothMapModel.id == booth.id) {
                        boothMapModel.copy(isSelected = true)
                    } else {
                        boothMapModel.copy(isSelected = false)
                    }
                }.toImmutableList(),
            )
        }
    }

    private fun updateSelectedBoothList(booths: List<BoothMapModel>) {
        if (_uiState.value.isPopularMode) {
            viewModelScope.launch {
                _uiState.update {
                    it.copy(isPopularMode = false)
                }
                delay(500)
                _uiState.update {
                    it.copy(
                        isBoothSelectionMode = true,
                        selectedBoothList = booths.toImmutableList(),
                    )
                }
            }
        } else {
            _uiState.update {
                it.copy(
                    isBoothSelectionMode = true,
                    selectedBoothList = booths.toImmutableList(),
                )
            }
        }
        if (booths.size == 1) {
            _uiState.update {
                it.copy(
                    filteredBoothsList = it.filteredBoothsList.map { boothMapModel ->
                        if (boothMapModel.id == booths[0].id) {
                            boothMapModel.copy(isSelected = true)
                        } else {
                            boothMapModel.copy(isSelected = false)
                        }
                    }.toImmutableList(),
                )
            }
        }
    }

    private fun setLikedFestivalDeleteDialogVisible(flag: Boolean) {
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

    private fun deleteLikedFestival(festival: FestivalModel) {
        viewModelScope.launch {
            likedFestivalRepository.deleteLikedFestival(festival)
        }
    }

    private fun checkFestivalOnboardingCompletion() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isFestivalOnboardingCompleted = onboardingRepository.checkFestivalOnboardingCompletion())
            }
        }
    }

    private fun completeFestivalOnboarding() {
        viewModelScope.launch {
            onboardingRepository.completeFestivalOnboarding(true)
            _uiState.update {
                it.copy(isFestivalOnboardingCompleted = true)
            }
        }
    }

    private fun String.toEnglishCategory(): String {
        return when (this) {
            "주점" -> "BAR"
            "먹거리" -> "FOOD"
            "이벤트" -> "EVENT"
            "일반" -> "NORMAL"
            "의무실" -> "MEDICAL"
            "화장실" -> "TOILET"
            else -> ""
        }
    }
}
