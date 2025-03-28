package com.unifest.android.feature.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexters.bandalart.core.data.api.repository.MessagingRepository
import com.nexters.bandalart.core.data.api.repository.OnboardingRepository
import com.nexters.bandalart.core.data.api.repository.RemoteConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

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
    fun refreshFCMToken() {
        viewModelScope.launch {
            try {
                val fcmToken = messagingRepository.refreshFCMToken()
                fcmToken?.let { token ->
                    Timber.d("New FCM token: $token")
                    messagingRepository.registerFCMToken(token)
                        .onSuccess {
                            messagingRepository.setFCMToken(token)
                        }.onFailure { exception ->
                            Timber.e(exception, "Error registering FCM token")
                        }
                }
            } catch (e: Exception) {
                Timber.e(e, "Error getting or saving FCM token")
            }
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
