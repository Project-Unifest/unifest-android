package com.unifest.android.feature.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifest.android.core.data.repository.LikedFestivalRepository
import com.unifest.android.core.data.repository.MessagingRepository
import com.unifest.android.core.data.repository.RemoteConfigRepository
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
    // private val onboardingRepository: OnboardingRepository,
    private val likedFestivalRepository: LikedFestivalRepository,
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

//    // 학교를 하나만 서비스 하기 때문에 Intro 스킵
//    fun checkIntroCompletion() {
//        viewModelScope.launch {
//            if (onboardingRepository.checkIntroCompletion()) {
//                _uiEvent.send(SplashUiEvent.NavigateToMain)
//            } else {
//                _uiEvent.send(SplashUiEvent.NavigateToIntro)
//            }
//        }
//    }

    private fun setRecentLikedFestival() {
        viewModelScope.launch {
            likedFestivalRepository.setRecentLikedFestival("한국교통대학교")
            likedFestivalRepository.setRecentLikedFestivalId(2L)
            _uiEvent.send(SplashUiEvent.NavigateToMain)
        }
    }

    @Suppress("TooGenericExceptionCaught")
    fun refreshFCMToken() {
        viewModelScope.launch {
            try {
                val token = messagingRepository.refreshFCMToken()
                token?.let {
                    Timber.d("New FCM token: $it")
                    messagingRepository.setFCMToken(it)
                    // 한국교통대학교로 고정
                    setRecentLikedFestival()
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
