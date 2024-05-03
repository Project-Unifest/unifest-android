package com.unifest.android.feature.map.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifest.android.core.common.ButtonType
import com.unifest.android.core.common.ErrorHandlerActions
import com.unifest.android.core.common.FestivalUiAction
import com.unifest.android.core.common.UiText
import com.unifest.android.core.common.handleException
import com.unifest.android.core.data.repository.BoothRepository
import com.unifest.android.core.data.repository.FestivalRepository
import com.unifest.android.core.data.repository.LikedFestivalRepository
import com.unifest.android.core.data.repository.OnboardingRepository
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.core.designsystem.R
import com.unifest.android.feature.map.mapper.toMapModel
import com.unifest.android.feature.map.model.BoothMapModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
        searchSchoolName()
        getAllFestivals()
        checkMapOnboardingCompletion()
        checkFestivalOnboardingCompletion()
        observeLikedFestivals()
    }

    fun onPermissionResult(isGranted: Boolean) {
        if (!isGranted) {
            _uiState.update {
                it.copy(isPermissionDialogVisible = true)
            }
        }
    }

    fun onMapUiAction(action: MapUiAction) {
        when (action) {
            is MapUiAction.OnTitleClick -> setFestivalSearchBottomSheetVisible(true)
            is MapUiAction.OnSearchTextUpdated -> updateBoothSearchText(action.searchText)
            is MapUiAction.OnSearchTextCleared -> clearBoothSearchText()
            is MapUiAction.OnSearch -> searchBooth()
            is MapUiAction.OnTooltipClick -> completeMapOnboarding()
            is MapUiAction.OnBoothMarkerClick -> updateSelectedBoothList(action.booths)
            is MapUiAction.OnTogglePopularBooth -> setEnablePopularMode()
            is MapUiAction.OnBoothItemClick -> navigateToBoothDetail(action.boothId)
            is MapUiAction.OnRetryClick -> refresh(action.error)
            is MapUiAction.OnPermissionDialogButtonClick -> {
                when (action.buttonType) {
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
        }
    }

    fun onFestivalUiAction(action: FestivalUiAction) {
        when (action) {
            is FestivalUiAction.OnDismiss -> setFestivalSearchBottomSheetVisible(false)
            is FestivalUiAction.OnSearchTextUpdated -> updateFestivalSearchText(action.text)
            is FestivalUiAction.OnSearchTextCleared -> clearFestivalSearchText()
            is FestivalUiAction.OnEnableSearchMode -> setEnableSearchMode(action.flag)
            is FestivalUiAction.OnEnableEditMode -> setEnableEditMode()
            is FestivalUiAction.OnLikedFestivalSelected -> setRecentLikedFestival(action.festival.schoolName)
            is FestivalUiAction.OnAddClick -> addLikeFestival(action.festival)
            is FestivalUiAction.OnDeleteIconClick -> {
                _uiState.update {
                    it.copy(deleteSelectedFestival = action.deleteSelectedFestival)
                }
                setLikedFestivalDeleteDialogVisible(true)
            }

            is FestivalUiAction.OnDialogButtonClick -> {
                when (action.type) {
                    ButtonType.CONFIRM -> {
                        setLikedFestivalDeleteDialogVisible(false)
                        _uiState.value.deleteSelectedFestival?.let { deleteLikedFestival(it) }
                    }

                    ButtonType.CANCEL -> setLikedFestivalDeleteDialogVisible(false)
                }
            }

            is FestivalUiAction.OnTooltipClick -> completeFestivalOnboarding()
        }
    }

    private fun observeLikedFestivals() {
        viewModelScope.launch {
            likedFestivalRepository.getLikedFestivals().collect { likedFestivalList ->
                _uiState.update {
                    it.copy(
                        likedFestivals = likedFestivalList.toPersistentList(),
                    )
                }
            }
        }
    }

    private fun getAllFestivals() {
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

    private fun searchSchoolName() {
        viewModelScope.launch {
            festivalRepository.searchSchool(likedFestivalRepository.getRecentLikedFestival())
                .onSuccess { festivals ->
                    _uiState.update {
                        it.copy(
                            festivalInfo = festivals[0],
                        )
                    }
                    getPopularBooths()
                    getAllBooths()
                }
                .onFailure { exception ->
                    handleException(exception, this@MapViewModel)
                }
        }
    }

    private fun getPopularBooths() {
        viewModelScope.launch {
            boothRepository.getPopularBooths(_uiState.value.festivalInfo.festivalId)
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

    private fun getAllBooths() {
        viewModelScope.launch {
            boothRepository.getAllBooths(_uiState.value.festivalInfo.festivalId)
                .onSuccess { booths ->
                    _uiState.update {
                        it.copy(
                            boothList = booths
                                .map { booth -> booth.toMapModel() }
                                .toImmutableList(),
                        )
                    }
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

    private fun updateBoothSearchText(text: TextFieldValue) {
        _uiState.update {
            it.copy(boothSearchText = text)
        }
    }

    private fun clearBoothSearchText() {
        _uiState.update {
            it.copy(festivalSearchText = TextFieldValue())
        }
    }

    private fun searchBooth() {
        val searchBoothResult = _uiState.value.boothList.filter {
            it.name.contains(_uiState.value.boothSearchText.text, ignoreCase = true) ||
                it.description.contains(_uiState.value.boothSearchText.text, ignoreCase = true)
        }
        updateSelectedBoothList(searchBoothResult)
    }

    private fun updateFestivalSearchText(text: TextFieldValue) {
        _uiState.update {
            it.copy(festivalSearchText = text)
        }
    }

    private fun clearFestivalSearchText() {
        _uiState.update {
            it.copy(festivalSearchText = TextFieldValue())
        }
    }

    private fun setFestivalSearchBottomSheetVisible(flag: Boolean) {
        _uiState.update {
            it.copy(isFestivalSearchBottomSheetVisible = flag)
        }
    }

    private fun setRecentLikedFestival(schoolName: String) {
        viewModelScope.launch {
            likedFestivalRepository.setRecentLikedFestival(schoolName)
        }
        setFestivalSearchBottomSheetVisible(false)
    }

    private fun addLikeFestival(festival: FestivalModel) {
        viewModelScope.launch {
            likedFestivalRepository.insertLikedFestivalAtSearch(festival)
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
                boothRepository.getPopularBooths(_uiState.value.festivalInfo.festivalId)
                    .onSuccess { booths ->
                        _uiState.update {
                            it.copy(
                                selectedBoothList = booths.map { it.toMapModel() }.toImmutableList(),
                                isBoothSelectionMode = false,
                            )
                        }
                        delay(500)
                        _uiState.update {
                            it.copy(
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
}
