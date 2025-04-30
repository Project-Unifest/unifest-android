package com.unifest.android.feature.booth.viewmodel

import android.Manifest
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.unifest.android.core.common.ErrorHandlerActions
import com.unifest.android.core.common.PermissionDialogButtonType
import com.unifest.android.core.common.UiText
import com.unifest.android.core.common.handleException
import com.unifest.android.core.data.api.repository.BoothRepository
import com.unifest.android.core.data.api.repository.LikedBoothRepository
import com.unifest.android.core.data.api.repository.WaitingRepository
import com.unifest.android.core.model.MenuModel
import com.unifest.android.core.model.WaitingStatus
import com.unifest.android.core.navigation.Route
import com.unifest.android.feature.booth.R
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
import javax.inject.Inject
import com.unifest.android.core.designsystem.R as designR

@HiltViewModel
class BoothViewModel @Inject constructor(
    private val boothRepository: BoothRepository,
    private val likedBoothRepository: LikedBoothRepository,
    private val waitingRepository: WaitingRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel(), ErrorHandlerActions {
    private val boothId = savedStateHandle.toRoute<Route.Booth.BoothDetail>().boothId

    private val _uiState = MutableStateFlow(BoothUiState())
    val uiState: StateFlow<BoothUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<BoothUiEvent>()
    val uiEvent: Flow<BoothUiEvent> = _uiEvent.receiveAsFlow()

    init {
        getBoothDetail()
        getLikedBooths()
    }

    fun onAction(action: BoothUiAction) {
        when (action) {
            is BoothUiAction.OnBackClick -> navigateBack()
            is BoothUiAction.OnCheckLocationClick -> navigateToBoothLocation()
            is BoothUiAction.OnToggleBookmark -> toggleBookmark()
            is BoothUiAction.OnRetryClick -> refresh(action.error)
            is BoothUiAction.OnMenuImageClick -> showMenuImageDialog(action.menu)
            is BoothUiAction.OnMenuImageDialogDismiss -> hideMenuImageDialog()
            is BoothUiAction.OnPinNumberUpdated -> updatePinNumberText(action.pinNumber)
            is BoothUiAction.OnWaitingTelUpdated -> updateWaitingTelText(action.tel)
            is BoothUiAction.OnWaitingButtonClick -> checkMyWaitingListNumbers()
            is BoothUiAction.OnDialogPinButtonClick -> checkPinValidation()
            is BoothUiAction.OnDialogWaitingButtonClick -> requestBoothWaiting()
            is BoothUiAction.OnWaitingDialogDismiss -> setWaitingDialogVisible(false)
            is BoothUiAction.OnPinDialogDismiss -> setPinCheckDialogVisible(false)
            is BoothUiAction.OnConfirmDialogDismiss -> setConfirmDialogVisible(false)
            is BoothUiAction.OnWaitingMinusClick -> minusWaitingPartySize()
            is BoothUiAction.OnWaitingPlusClick -> plusWaitingPartySize()
            is BoothUiAction.OnPolicyCheckBoxClick -> privacyConsentClick()
            is BoothUiAction.OnPrivatePolicyClick -> navigateToPrivatePolicy()
            is BoothUiAction.OnThirdPartyPolicyClick -> navigateToThirdPartyPolicy()
            is BoothUiAction.OnScheduleToggleClick -> toggleScheduleExpanded()
            is BoothUiAction.OnMoveClick -> navigateToWaiting()
            is BoothUiAction.OnNoShowDialogCancelClick -> setNoShowDialogVisible(false)
            is BoothUiAction.OnRequestLocationPermission -> navigateToBoothLocation()
            is BoothUiAction.OnRequestNotificationPermission -> setNotificationPermissionDialogVisible(true)
            is BoothUiAction.OnPermissionDialogButtonClick -> handlePermissionDialogButtonClick(action.buttonType, action.permission)
        }
    }

    private fun checkMyWaitingListNumbers() {
        viewModelScope.launch {
            waitingRepository.getMyWaitingList()
                .onSuccess { waitingLists ->
                    _uiState.update {
                        it.copy(myWaitingList = waitingLists.toImmutableList())
                    }
                    val currentBoothId = _uiState.value.boothDetailInfo.id
                    val matchingBooth = _uiState.value.myWaitingList.find { it.boothId == currentBoothId }
                    when {
                        matchingBooth?.waitingStatus == WaitingStatus.NOSHOW -> setNoShowDialogVisible(true)
                        matchingBooth != null -> _uiEvent.send(
                            BoothUiEvent.ShowSnackBar(UiText.StringResource(R.string.booth_waiting_already_exists)),
                        )

                        _uiState.value.myWaitingList.size >= 3 -> _uiEvent.send(
                            BoothUiEvent.ShowSnackBar(UiText.StringResource(R.string.booth_waiting_full)),
                        )

                        else -> setPinCheckDialogVisible(true)
                    }
                }
                .onFailure { exception ->
                    handleException(exception, this@BoothViewModel)
                }
        }
    }

    private fun getBoothDetail() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            boothRepository.getBoothDetail(boothId)
                .onSuccess { booth ->
                    _uiState.update {
                        it.copy(boothDetailInfo = booth)
                    }
                    getBoothLikes()
                }
                .onFailure { exception ->
                    handleException(exception, this@BoothViewModel)
                }
            _uiState.update {
                it.copy(isLoading = false)
            }
        }
    }

    private fun getBoothLikes() {
        viewModelScope.launch {
            boothRepository.getBoothLikes(boothId)
                .onSuccess { likes ->
                    _uiState.update {
                        it.copy(
                            boothDetailInfo = it.boothDetailInfo.copy(
                                likes = likes,
                            ),
                        )
                    }
                }
                .onFailure { exception ->
                    handleException(exception, this@BoothViewModel)
                }
        }
    }

    private fun getLikedBooths() {
        viewModelScope.launch {
            likedBoothRepository.getLikedBooths()
                .onSuccess { likedBooths ->
                    _uiState.update {
                        it.copy(likedBooths = likedBooths.toImmutableList())
                    }
                    checkLikedBooth()
                }
                .onFailure { exception ->
                    handleException(exception, this@BoothViewModel)
                }
        }
    }

    private fun checkLikedBooth() {
        val isLiked = _uiState.value.likedBooths.any { it.id == boothId }
        _uiState.update {
            it.copy(isLiked = isLiked)
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _uiEvent.send(BoothUiEvent.NavigateBack)
        }
    }

    private fun navigateToBoothLocation() {
        viewModelScope.launch {
            _uiEvent.send(BoothUiEvent.NavigateToBoothLocation)
        }
    }

    private fun navigateToWaiting() {
        setNoShowDialogVisible(false)
        viewModelScope.launch {
            _uiEvent.send(BoothUiEvent.NavigateToWaiting)
        }
    }

    private fun toggleScheduleExpanded() {
        _uiState.update {
            it.copy(isScheduleExpanded = !it.isScheduleExpanded)
        }
    }

    private fun toggleBookmark() {
        val currentBookmarkFlag = _uiState.value.isLiked
        val newBookmarkFlag = !currentBookmarkFlag
        viewModelScope.launch {
            boothRepository.likeBooth(boothId)
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            boothDetailInfo = it.boothDetailInfo.copy(
                                likes = it.boothDetailInfo.likes + if (newBookmarkFlag) 1 else -1,
                            ),
                            isLiked = newBookmarkFlag,
                        )
                    }
                    if (currentBookmarkFlag) {
                        likedBoothRepository.deleteLikedBooth(_uiState.value.boothDetailInfo)
                        _uiEvent.send(BoothUiEvent.ShowSnackBar(UiText.StringResource(designR.string.liked_booth_removed_message)))
                    } else {
                        likedBoothRepository.insertLikedBooth(_uiState.value.boothDetailInfo)
                        _uiEvent.send(BoothUiEvent.ShowSnackBar(UiText.StringResource(designR.string.liked_booth_saved_message)))
                    }
                }
                .onFailure {
                    if (currentBookmarkFlag) {
                        _uiEvent.send(BoothUiEvent.ShowSnackBar(UiText.StringResource(designR.string.liked_booth_removed_failed_message)))
                    } else {
                        _uiEvent.send(BoothUiEvent.ShowSnackBar(UiText.StringResource(designR.string.liked_booth_saved_failed_message)))
                    }
                }
        }
    }

    private fun refresh(error: ErrorType) {
        getBoothDetail()
        when (error) {
            ErrorType.NETWORK -> setNetworkErrorDialogVisible(false)
            ErrorType.SERVER -> setServerErrorDialogVisible(false)
        }
    }

    private fun requestBoothWaiting() {
        val tel = _uiState.value.waitingTel
        val partySize = _uiState.value.waitingPartySize

        if (isTelValid(tel) && isPartySizeValid(partySize)) {
            viewModelScope.launch {
                boothRepository.requestBoothWaiting(
                    boothId = _uiState.value.boothDetailInfo.id,
                    tel = tel,
                    partySize = partySize,
                    pinNumber = _uiState.value.boothPinNumber,
                ).onSuccess { waiting ->
                    _uiState.update {
                        it.copy(
                            waitingId = waiting.waitingId,
                            waitingTel = "",
                            boothPinNumber = "",
                        )
                    }
                    waitingRepository.registerFCMTopic(waiting.waitingId.toString())
                    setWaitingDialogVisible(false)
                    setConfirmDialogVisible(true)
                }.onFailure { exception ->
                    handleException(exception, this@BoothViewModel)
                }
            }
        } else {
            viewModelScope.launch {
                _uiEvent.send(BoothUiEvent.ShowToast(UiText.StringResource(R.string.booth_empty_waiting)))
            }
        }
    }

    private fun isTelValid(tel: String): Boolean {
        return tel.matches(Regex("^010\\d{8}$"))
    }

    private fun isPartySizeValid(partySize: Long): Boolean {
        return partySize >= 1
    }

    private fun updatePinNumberText(pinNumber: String) {
        _uiState.update {
            it.copy(
                boothPinNumber = pinNumber,
            )
        }
    }

    private fun updateWaitingTelText(tel: String) {
        _uiState.update {
            it.copy(
                waitingTel = tel,
            )
        }
    }

    private fun checkPinValidation() {
        if (_uiState.value.isWrongPinInserted) {
            return
        }

        viewModelScope.launch {
            boothRepository.checkPinValidation(_uiState.value.boothDetailInfo.id, _uiState.value.boothPinNumber)
                .onSuccess { waitingTeamNumber ->
                    if (waitingTeamNumber > -1) {
                        _uiState.update {
                            it.copy(waitingTeamNumber = waitingTeamNumber)
                        }
                        setPinCheckDialogVisible(false)
                        setWaitingDialogVisible(true)
                    } else {
                        _uiState.update {
                            it.copy(
                                isWrongPinInserted = true,
                                boothPinNumber = "",
                            )
                        }
                        delay(2000L)
                        _uiState.update {
                            it.copy(isWrongPinInserted = false)
                        }
                    }
                }
                .onFailure { exception ->
                    handleException(exception, this@BoothViewModel)
                }
        }
    }

    private fun privacyConsentClick() {
        _uiState.update {
            it.copy(privacyConsentChecked = !it.privacyConsentChecked)
        }
    }

    private fun minusWaitingPartySize() {
        if (_uiState.value.waitingPartySize <= 1) return

        _uiState.update { currentState ->
            currentState.copy(waitingPartySize = currentState.waitingPartySize - 1)
        }
    }

    private fun plusWaitingPartySize() {
        if (_uiState.value.waitingPartySize >= 100) return

        _uiState.update { currentState ->
            currentState.copy(waitingPartySize = currentState.waitingPartySize + 1)
        }
    }

    private fun showMenuImageDialog(menu: MenuModel) {
        _uiState.update {
            it.copy(
                isMenuImageDialogVisible = true,
                selectedMenu = menu,
            )
        }
    }

    private fun hideMenuImageDialog() {
        _uiState.update {
            it.copy(
                isMenuImageDialogVisible = false,
                selectedMenu = null,
            )
        }
    }

    private fun navigateToPrivatePolicy() {
        viewModelScope.launch {
            _uiEvent.send(BoothUiEvent.NavigateToPrivatePolicy)
        }
    }

    private fun navigateToThirdPartyPolicy() {
        viewModelScope.launch {
            _uiEvent.send(BoothUiEvent.NavigateToThirdPartyPolicy)
        }
    }

    private fun setPinCheckDialogVisible(flag: Boolean) {
        _uiState.update {
            it.copy(isPinCheckDialogVisible = flag)
        }
    }

    private fun setWaitingDialogVisible(flag: Boolean) {
        _uiState.update {
            it.copy(isWaitingDialogVisible = flag)
        }
    }

    private fun setNoShowDialogVisible(flag: Boolean) {
        _uiState.update {
            it.copy(isNoShowDialogVisible = flag)
        }
    }

    private fun setConfirmDialogVisible(flag: Boolean) {
        _uiState.update {
            it.copy(isConfirmDialogVisible = flag)
        }
    }

    fun onPermissionResult(permission: String, isGranted: Boolean) {
        when (permission) {
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION -> {
                if (isGranted) {
                    setLocationPermissionDialogVisible(false)
                } else {
                    setLocationPermissionDialogVisible(true)
                }
            }

            Manifest.permission.POST_NOTIFICATIONS -> {
                if (isGranted) {
                    setNotificationPermissionDialogVisible(false)
                    checkMyWaitingListNumbers()
                } else {
                    setNotificationPermissionDialogVisible(true)
                }
            }
        }
    }

    private fun handlePermissionDialogButtonClick(buttonType: PermissionDialogButtonType, permission: String) {
        when (buttonType) {
            PermissionDialogButtonType.DISMISS -> {
                dismissDialog(permission)
            }

            PermissionDialogButtonType.NAVIGATE_TO_APP_SETTING -> {
                viewModelScope.launch {
                    _uiEvent.send(BoothUiEvent.NavigateToAppSetting)
                }
            }

            PermissionDialogButtonType.CONFIRM -> {
                dismissDialog(permission)
                viewModelScope.launch {
                    _uiEvent.send(BoothUiEvent.RequestPermission(permission))
                }
            }
        }
    }

    private fun dismissDialog(permission: String) {
        when (permission) {
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION -> setLocationPermissionDialogVisible(false)
            Manifest.permission.POST_NOTIFICATIONS -> setNotificationPermissionDialogVisible(false)
        }
    }

    private fun setNotificationPermissionDialogVisible(flag: Boolean) {
        _uiState.update {
            it.copy(isNotificationPermissionDialogVisible = flag)
        }
    }

    private fun setLocationPermissionDialogVisible(flag: Boolean) {
        _uiState.update {
            it.copy(isLocationPermissionDialogVisible = flag)
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
