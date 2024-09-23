package com.unifest.android.feature.map.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifest.android.core.common.ErrorHandlerActions
import com.unifest.android.core.common.PermissionDialogButtonType
import com.unifest.android.core.common.UiText
import com.unifest.android.core.common.handleException
import com.unifest.android.core.data.repository.BoothRepository
import com.unifest.android.core.data.repository.FestivalRepository
import com.unifest.android.core.data.repository.LikedFestivalRepository
import com.unifest.android.core.data.repository.MessagingRepository
import com.unifest.android.core.data.repository.OnboardingRepository
import com.unifest.android.feature.map.R
import com.unifest.android.feature.map.mapper.toMapModel
import com.unifest.android.feature.map.model.BoothMapModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val onboardingRepository: OnboardingRepository,
    private val festivalRepository: FestivalRepository,
    private val boothRepository: BoothRepository,
    private val likedFestivalRepository: LikedFestivalRepository,
    private val messagingRepository: MessagingRepository,
) : ViewModel(), ErrorHandlerActions {
    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<MapUiEvent>()
    val uiEvent: Flow<MapUiEvent> = _uiEvent.receiveAsFlow()

    val permissionDialogQueue = mutableStateListOf<String>()

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean,
    ) {
        if (isGranted && permissionDialogQueue.isEmpty()) {
            refreshFCMToken()
        }

        if (!isGranted && !permissionDialogQueue.contains(permission)) {
            permissionDialogQueue.add(permission)
        }
    }

    @Suppress("TooGenericExceptionCaught")
    private fun refreshFCMToken() {
        viewModelScope.launch {
            try {
                val token = messagingRepository.refreshFCMToken()
                token?.let {
                    Timber.d("New FCM token: $it")
                    messagingRepository.setFCMToken(it)
                }
            } catch (e: Exception) {
                Timber.e(e, "Error getting or saving FCM token")
            }
        }
    }

    init {
        requestLocationPermission()
        searchSchoolName()
        getAllFestivals()
        checkMapOnboardingCompletion()
    }

    private fun requestLocationPermission() {
        viewModelScope.launch {
            _uiEvent.send(MapUiEvent.RequestPermissions)
        }
    }

    fun onMapUiAction(action: MapUiAction) {
        when (action) {
            is MapUiAction.OnSearchTextUpdated -> updateBoothSearchText(action.searchText)
            is MapUiAction.OnSearchTextCleared -> clearBoothSearchText()
            is MapUiAction.OnSearch -> searchBooth()
            is MapUiAction.OnTooltipClick -> completeMapOnboarding()
            is MapUiAction.OnBoothMarkerClick -> updateSelectedBoothList(action.booths)
            // is MapUiAction.OnBoothMarkerClick -> updateSelectedBooth(action.booth)
            is MapUiAction.OnTogglePopularBooth -> setEnablePopularMode()
            is MapUiAction.OnBoothItemClick -> navigateToBoothDetail(action.boothId)
            is MapUiAction.OnRetryClick -> refresh(action.error)
            is MapUiAction.OnBoothTypeChipClick -> updateSelectedBoothChipList(action.chipName)
            is MapUiAction.OnPermissionDialogButtonClick -> handlePermissionDialogButtonClick(action.buttonType, action.permission)
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

    private fun handlePermissionDialogButtonClick(buttonType: PermissionDialogButtonType, permission: String?) {
        when (buttonType) {
            PermissionDialogButtonType.DISMISS -> {
                dismissDialog()
            }

            PermissionDialogButtonType.NAVIGATE_TO_APP_SETTING -> {
                viewModelScope.launch {
                    _uiEvent.send(MapUiEvent.NavigateToAppSetting)
                }
            }

            PermissionDialogButtonType.CONFIRM -> {
                dismissDialog()
                viewModelScope.launch {
                    _uiEvent.send(MapUiEvent.RequestPermission(permission!!))
                }
            }
        }
    }

    private fun dismissDialog() {
        permissionDialogQueue.removeFirst()
    }

    private fun filterBoothsByType(chipList: List<String>) {
        val englishCategories = chipList.map { it.toEnglishCategory() }
        val filteredBooths = _uiState.value.boothList.filter { booth ->
            englishCategories.contains(booth.category)
        }
        _uiState.update {
            it.copy(filteredBoothList = filteredBooths.toImmutableList())
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

    private fun setEnablePopularMode() {
        if (_uiState.value.isBoothSelectionMode) {
            viewModelScope.launch {
                boothRepository.getPopularBooths(1)
                    .onSuccess { booths ->
                        _uiState.update { currentState ->
                            currentState.copy(
                                selectedBoothList = booths.map { it.toMapModel() }.toImmutableList(),
                                isBoothSelectionMode = false,
                                filteredBoothList = currentState.filteredBoothList.map { booth ->
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

//    private fun updateSelectedBooth(booth: BoothMapModel) {
//        if (_uiState.value.isPopularMode) {
//            viewModelScope.launch {
//                _uiState.update {
//                    it.copy(isPopularMode = false)
//                }
//                delay(500)
//                _uiState.update {
//                    it.copy(
//                        isBoothSelectionMode = true,
//                        selectedBoothList = listOf(booth).toImmutableList(),
//                    )
//                }
//            }
//        } else {
//            _uiState.update {
//                it.copy(
//                    isBoothSelectionMode = true,
//                    selectedBoothList = listOf(booth).toImmutableList(),
//                )
//            }
//        }
//        _uiState.update {
//            it.copy(
//                filteredBoothList = it.filteredBoothList.map { boothMapModel ->
//                    if (boothMapModel.id == booth.id) {
//                        boothMapModel.copy(isSelected = true)
//                    } else {
//                        boothMapModel.copy(isSelected = false)
//                    }
//                }.toImmutableList(),
//            )
//        }
//    }

    private fun updateSelectedBoothList(booths: List<BoothMapModel>) {
        Timber.d("booths.size: ${booths.size} updateSelectedBoothList: $booths")
        Timber.d("filteredBoothsList: ${_uiState.value.filteredBoothList}")
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
                    filteredBoothList = it.filteredBoothList.map { boothMapModel ->
                        if (boothMapModel.id == booths[0].id) {
                            boothMapModel.copy(isSelected = true)
                        } else {
                            boothMapModel.copy(isSelected = false)
                        }
                    }.toImmutableList(),
                )
            }
            Timber.d("booths.size: ${booths.size} updateSelectedBoothList: $booths")
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
