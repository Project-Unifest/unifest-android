package com.unifest.android.feature.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifest.android.core.data.api.repository.MessagingRepository
import com.unifest.android.core.data.api.repository.OnboardingRepository
import com.unifest.android.core.data.api.repository.RemoteConfigRepository
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

// TODO 서버 에러 분기 처리, 알림 권한 스플래시 화면으로 옮겨보기
@HiltViewModel
class SplashViewModel @Inject constructor(
    private val onboardingRepository: OnboardingRepository,
    private val messagingRepository: MessagingRepository,
    remoteConfigRepository: RemoteConfigRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<SplashUiEvent>()
    val uiEvent: Flow<SplashUiEvent> = _uiEvent.receiveAsFlow()

    val shouldUpdate = remoteConfigRepository.shouldUpdate()

    fun onAction(action: SplashUiAction) {
        when (action) {
            is SplashUiAction.OnUpdateClick -> navigateToPlayStore()
            is SplashUiAction.OnUpdateDismissClick -> closeApp()
            is SplashUiAction.OnConfirmClick -> closeApp()
        }
    }

    fun checkIntroCompletion() {
        viewModelScope.launch {
            if (onboardingRepository.checkIntroCompletion()) {
                _uiEvent.send(SplashUiEvent.NavigateToMain)
            } else {
                _uiEvent.send(SplashUiEvent.NavigateToIntro)
            }
        }
    }

    @Suppress("TooGenericExceptionCaught")
    suspend fun refreshFCMToken(): Boolean {
        return try {
            val fcmToken = messagingRepository.refreshFCMToken()
            if (fcmToken == null) {
                Timber.e("FCM token is null")
                _uiState.update {
                    it.copy(isNetworkErrorDialogVisible = true)
                }
                return false
            }

            messagingRepository.registerFCMToken(fcmToken)
                .fold(
                    onSuccess = {
                        messagingRepository.setFCMToken(fcmToken)
                        true
                    },
                    onFailure = { exception ->
                        Timber.e(exception, "Error registering FCM token")
                        _uiState.update {
                            it.copy(isNetworkErrorDialogVisible = true)
                        }
                        false
                    },
                )
        } catch (e: Exception) {
            Timber.e(e, "Error getting or saving FCM token")
            _uiState.update {
                it.copy(isNetworkErrorDialogVisible = true)
            }
            false
        }
    }

    private fun navigateToPlayStore() {
        viewModelScope.launch {
            _uiEvent.send(SplashUiEvent.NavigateToPlayStore)
        }
    }

    private fun closeApp() {
        viewModelScope.launch {
            _uiEvent.send(SplashUiEvent.CloseApp)
        }
    }
}
