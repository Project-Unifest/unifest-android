package com.unifest.android.feature.booth_detail.viewmodel

import android.Manifest
import androidx.compose.foundation.text.input.clearText
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
import com.unifest.android.feature.booth_detail.R
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
class BoothDetailViewModel @Inject constructor(
    private val boothRepository: BoothRepository,
    private val likedBoothRepository: LikedBoothRepository,
    private val waitingRepository: WaitingRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel(), ErrorHandlerActions {
    private val boothId = savedStateHandle.toRoute<Route.BoothDetail.BoothDetail>().boothId

    private val _uiState = MutableStateFlow(BoothDetailUiState())
    val uiState: StateFlow<BoothDetailUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<BoothDetailUiEvent>()
    val uiEvent: Flow<BoothDetailUiEvent> = _uiEvent.receiveAsFlow()

    init {
        getBoothDetail()
        getLikedBooths()
    }

    fun onAction(action: BoothDetailUiAction) {
        when (action) {
            is BoothDetailUiAction.OnBackClick -> navigateBack()
            is BoothDetailUiAction.OnCheckLocationClick -> navigateToBoothDetailLocation()
            is BoothDetailUiAction.OnToggleBookmark -> toggleBookmark()
            is BoothDetailUiAction.OnRetryClick -> refresh(action.error)
            is BoothDetailUiAction.OnMenuImageClick -> showMenuImageDialog(action.menu)
            is BoothDetailUiAction.OnMenuImageDialogDismiss -> hideMenuImageDialog()
            is BoothDetailUiAction.OnWaitingButtonClick -> checkMyWaitingListNumbers()
            is BoothDetailUiAction.OnDialogPinButtonClick -> checkPinValidation()
            is BoothDetailUiAction.OnDialogWaitingButtonClick -> requestBoothWaiting()
            is BoothDetailUiAction.OnWaitingDialogDismiss -> setWaitingDialogVisible(false)
            is BoothDetailUiAction.OnPinDialogDismiss -> setPinCheckDialogVisible(false)
            is BoothDetailUiAction.OnConfirmDialogDismiss -> setConfirmDialogVisible(false)
            is BoothDetailUiAction.OnWaitingMinusClick -> minusWaitingPartySize()
            is BoothDetailUiAction.OnWaitingPlusClick -> plusWaitingPartySize()
            is BoothDetailUiAction.OnPolicyCheckBoxClick -> privacyConsentClick()
            is BoothDetailUiAction.OnPrivatePolicyClick -> navigateToPrivatePolicy()
            is BoothDetailUiAction.OnThirdPartyPolicyClick -> navigateToThirdPartyPolicy()
            is BoothDetailUiAction.OnScheduleToggleClick -> toggleScheduleExpanded()
            is BoothDetailUiAction.OnMoveClick -> navigateToWaiting()
            is BoothDetailUiAction.OnNoShowDialogCancelClick -> setNoShowDialogVisible(false)
            is BoothDetailUiAction.OnRequestLocationPermission -> navigateToBoothDetailLocation()
            is BoothDetailUiAction.OnRequestNotificationPermission -> setNotificationPermissionDialogVisible(true)
            is BoothDetailUiAction.OnPermissionDialogButtonClick -> handlePermissionDialogButtonClick(action.buttonType, action.permission)
        }
    }

    fun requestLocationPermission() {
        viewModelScope.launch {
            _uiEvent.send(BoothDetailUiEvent.RequestPermission)
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
                            BoothDetailUiEvent.ShowSnackBar(UiText.StringResource(R.string.booth_waiting_already_exists)),
                        )

                        _uiState.value.myWaitingList.size >= 3 -> _uiEvent.send(
                            BoothDetailUiEvent.ShowSnackBar(UiText.StringResource(R.string.booth_waiting_full)),
                        )

                        else -> setPinCheckDialogVisible(true)
                    }
                }
                .onFailure { exception ->
                    handleException(exception, this@BoothDetailViewModel)
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
                    handleException(exception, this@BoothDetailViewModel)
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
                    handleException(exception, this@BoothDetailViewModel)
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
                    handleException(exception, this@BoothDetailViewModel)
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
            _uiEvent.send(BoothDetailUiEvent.NavigateBack)
        }
    }

    private fun navigateToBoothDetailLocation() {
        viewModelScope.launch {
            _uiEvent.send(BoothDetailUiEvent.NavigateToBoothDetailLocation)
        }
    }

    private fun navigateToWaiting() {
        setNoShowDialogVisible(false)
        viewModelScope.launch {
            _uiEvent.send(BoothDetailUiEvent.NavigateToWaiting)
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
                        _uiEvent.send(BoothDetailUiEvent.ShowSnackBar(UiText.StringResource(designR.string.liked_booth_removed_message)))
                    } else {
                        likedBoothRepository.insertLikedBooth(_uiState.value.boothDetailInfo)
                        _uiEvent.send(BoothDetailUiEvent.ShowSnackBar(UiText.StringResource(designR.string.liked_booth_saved_message)))
                    }
                }
                .onFailure {
                    if (currentBookmarkFlag) {
                        _uiEvent.send(BoothDetailUiEvent.ShowSnackBar(UiText.StringResource(designR.string.liked_booth_removed_failed_message)))
                    } else {
                        _uiEvent.send(BoothDetailUiEvent.ShowSnackBar(UiText.StringResource(designR.string.liked_booth_saved_failed_message)))
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
        val tel = _uiState.value.waitingTel.text.toString()
        val partySize = _uiState.value.waitingPartySize

        if (isTelValid(tel) && isPartySizeValid(partySize)) {
            viewModelScope.launch {
                boothRepository.requestBoothWaiting(
                    boothId = _uiState.value.boothDetailInfo.id,
                    tel = tel,
                    partySize = partySize,
                    pinNumber = _uiState.value.boothPinNumber.text.toString(),
                ).onSuccess { waiting ->
                    _uiState.update {
                        it.copy(
                            waitingId = waiting.waitingId,
                        )
                    }
                    _uiState.value.waitingTel.clearText()
                    _uiState.value.boothPinNumber.clearText()
                    waitingRepository.registerFCMTopic(waiting.waitingId.toString())
                    setWaitingDialogVisible(false)
                    setConfirmDialogVisible(true)
                }.onFailure { exception ->
                    handleException(exception, this@BoothDetailViewModel)
                }
            }
        } else {
            viewModelScope.launch {
                _uiEvent.send(BoothDetailUiEvent.ShowToast(UiText.StringResource(R.string.booth_empty_waiting)))
            }
        }
    }

    private fun isTelValid(tel: String): Boolean {
        return tel.matches(Regex("^010\\d{8}$"))
    }

    private fun isPartySizeValid(partySize: Long): Boolean {
        return partySize >= 1
    }


    private fun checkPinValidation() {
        if (_uiState.value.isWrongPinInserted) {
            return
        }

        viewModelScope.launch {
            boothRepository.checkPinValidation(_uiState.value.boothDetailInfo.id, _uiState.value.boothPinNumber.text.toString())
                .onSuccess { waitingTeamNumber ->
                    if (waitingTeamNumber > -1) {
                        _uiState.update {
                            it.copy(waitingTeamNumber = waitingTeamNumber)
                        }
                        setPinCheckDialogVisible(false)
                        setWaitingDialogVisible(true)
                    } else {
                        _uiState.update {
                            it.copy(isWrongPinInserted = true)
                        }
                        _uiState.value.boothPinNumber.clearText()
                        delay(2000L)
                        _uiState.update {
                            it.copy(isWrongPinInserted = false)
                        }
                    }
                }
                .onFailure { exception ->
                    handleException(exception, this@BoothDetailViewModel)
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
            _uiEvent.send(BoothDetailUiEvent.NavigateToPrivatePolicy)
        }
    }

    private fun navigateToThirdPartyPolicy() {
        viewModelScope.launch {
            _uiEvent.send(BoothDetailUiEvent.NavigateToThirdPartyPolicy)
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
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION -> {
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
                    _uiEvent.send(BoothDetailUiEvent.NavigateToAppSetting)
                }
            }

            PermissionDialogButtonType.CONFIRM -> {
                dismissDialog(permission)
                viewModelScope.launch {
                    _uiEvent.send(BoothDetailUiEvent.RequestPermission)
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
