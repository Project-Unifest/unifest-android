package com.unifest.android.feature.stamp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifest.android.core.common.QRErrorHandlerActions
import com.unifest.android.core.common.UiText
import com.unifest.android.core.common.handleException
import com.nexters.bandalart.core.data.api.repository.StampRepository
import com.unifest.android.feature.stamp.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
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
class QRScanViewModel @Inject constructor(
    private val stampRepository: StampRepository,
) : ViewModel(), QRErrorHandlerActions {
    private val _uiState = MutableStateFlow(QRScanUiState())
    val uiState: StateFlow<QRScanUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<QRScanUiEvent>()
    val uiEvent: Flow<QRScanUiEvent> = _uiEvent.receiveAsFlow()

    fun onAction(action: QRScanUiAction) {
        when (action) {
            is QRScanUiAction.OnBackClick -> navigateBack()
            is QRScanUiAction.OnQRCodeScanned -> registerStamp(action.boothId)
        }
    }

    fun registerStamp(boothId: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            stampRepository.registerStamp(boothId)
                .onSuccess {
                    _uiEvent.send(QRScanUiEvent.ShowToast(UiText.StringResource(R.string.stamp_register_completed)))
                    _uiEvent.send(QRScanUiEvent.RegisterStampCompleted)
                }.onFailure { exception ->
                    Timber.e(exception)
                    handleException(exception, this@QRScanViewModel)
                    _uiEvent.send(QRScanUiEvent.RegisterStampFailed)
                }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _uiEvent.send(QRScanUiEvent.NavigateBack)
        }
    }

    /**
     * 스캐너가 QR코드를 스캔하면 호출하는 함수
     *
     * @param entryCode 스캔한 QR 의 데이터
     */
    fun scan(entryCode: String) {
        Timber.d("스캔 결과: $entryCode")
        viewModelScope.launch {
            val boothId = entryCode.toLongOrNull()
            if (boothId != null) {
                _uiEvent.send(QRScanUiEvent.ScanSuccess(entryCode))
            } else {
                handleException(NumberFormatException("Invalid QR code: $entryCode"), this@QRScanViewModel)
            }
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

    override fun showErrorMessage(message: UiText) {
        viewModelScope.launch {
            _uiEvent.send(QRScanUiEvent.ShowToast(message))
        }
    }
}
