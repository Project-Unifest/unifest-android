package com.unifest.android.feature.booth.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifest.android.core.common.ErrorHandlerActions
import com.unifest.android.core.common.UiText
import com.unifest.android.core.common.handleException
import com.unifest.android.core.data.repository.BoothRepository
import com.unifest.android.core.data.repository.LikedBoothRepository
import com.unifest.android.core.data.repository.LikedFestivalRepository
import com.unifest.android.core.data.repository.WaitingRepository
import com.unifest.android.core.model.MenuModel
import com.unifest.android.feature.booth.R
import com.unifest.android.core.designsystem.R as designR
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

@HiltViewModel
class BoothViewModel @Inject constructor(
    private val boothRepository: BoothRepository,
    private val likedBoothRepository: LikedBoothRepository,
    private val likedFestivalRepository: LikedFestivalRepository,
    private val waitingRepository: WaitingRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel(), ErrorHandlerActions {
    companion object {
        private const val BOOTH_ID = "boothId"
    }

    private val boothId: Long = requireNotNull(savedStateHandle.get<Long>(BOOTH_ID)) { "boothId is required." }

    private val _uiState = MutableStateFlow(BoothUiState())
    val uiState: StateFlow<BoothUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<BoothUiEvent>()
    val uiEvent: Flow<BoothUiEvent> = _uiEvent.receiveAsFlow()

    init {
        getBoothDetail()
        getLikedBooths()
        getMyWaitingList()
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
            is BoothUiAction.OnRunningClick -> expandRunningTime()
        }
    }

    private fun checkMyWaitingListNumbers() {
        viewModelScope.launch {
            val isAlreadyInWaitingList = _uiState.value.myWaitingList.any { it.boothId == _uiState.value.boothDetailInfo.id }
            when {
                isAlreadyInWaitingList -> {
                    _uiEvent.send(BoothUiEvent.ShowSnackBar(UiText.StringResource(R.string.booth_waiting_already_exists)))
                }

                _uiState.value.myWaitingList.size >= 3 -> {
                    _uiEvent.send(BoothUiEvent.ShowSnackBar(UiText.StringResource(R.string.booth_waiting_full)))
                }

                else -> {
                    setPinCheckDialogVisible(true)
                }
            }
        }
    }

    private fun getMyWaitingList() {
        viewModelScope.launch {
            waitingRepository.getMyWaitingList()
                .onSuccess { waitingLists ->
                    _uiState.update {
                        it.copy(myWaitingList = waitingLists.toImmutableList())
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

    private fun expandRunningTime() {
        _uiState.update {
            it.copy(isRunning = !it.isRunning)
        }
    }

    private fun toggleBookmark() {
        val currentBookmarkFlag = _uiState.value.isLiked
        val newBookmarkFlag = !currentBookmarkFlag
        viewModelScope.launch {
            if (currentBookmarkFlag) {
                unregisterLikedFestival()
            } else {
                registerLikedFestival()
            }

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

    private fun registerLikedFestival() {
        viewModelScope.launch {
            likedFestivalRepository.registerLikedFestival()
                .onSuccess {}
                .onFailure {
                    _uiEvent.send(BoothUiEvent.ShowSnackBar(UiText.StringResource(designR.string.liked_booth_saved_failed_message)))
                }
        }
    }

    private fun unregisterLikedFestival() {
        viewModelScope.launch {
            likedFestivalRepository.unregisterLikedFestival()
                .onSuccess {}
                .onFailure {
                    _uiEvent.send(BoothUiEvent.ShowSnackBar(UiText.StringResource(designR.string.liked_booth_removed_failed_message)))
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

    private fun setConfirmDialogVisible(flag: Boolean) {
        _uiState.update {
            it.copy(isConfirmDialogVisible = flag)
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
