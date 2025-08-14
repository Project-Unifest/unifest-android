package com.unifest.android.feature.booth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifest.android.core.common.ErrorHandlerActions
import com.unifest.android.core.common.handleException
import com.unifest.android.core.data.api.repository.BoothRepository
import com.unifest.android.core.model.BoothTabModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoothViewModel @Inject constructor(
    private val boothRepository: BoothRepository,
) : ViewModel(), ErrorHandlerActions {
    private val _uiState: MutableStateFlow<BoothUiState> = MutableStateFlow(BoothUiState())
    val uiState: StateFlow<BoothUiState> = _uiState
        .onStart {
            fetchBoothList()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = BoothUiState(),
        )

    private val _uiEvent = Channel<BoothUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAction(action: BoothUiAction) {
        when (action) {
            is BoothUiAction.OnBoothItemClick -> navigateToBoothDetail(action.boothId)
            is BoothUiAction.OnWaitingCheckBoxClick -> setWaitingAvailabilityChecked()
            is BoothUiAction.OnRetryClick -> refresh(action.error)
        }
    }

    private fun setWaitingAvailabilityChecked() {
        _uiState.update {
            val newChecked = !it.waitingAvailabilityChecked
            it.copy(
                waitingAvailabilityChecked = newChecked,
                showingBoothList = if (newChecked) {
                    it.boothList.filter { booth -> booth.waitingEnabled }.toPersistentList()
                } else {
                    it.boothList.toPersistentList()
                },
            )
        }
    }

    private fun fetchBoothList() {
        val boothList = persistentListOf(
            BoothTabModel(
                id = 1,
                name = "컴공 주점 부스1",
                location = "학생회관 앞",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다",
                waitingEnabled = false,
                thumbnail = "https://cdn.pixabay.com/photo/2020/06/07/13/33/fireworks-5270439_1280.jpg",
            ),
            BoothTabModel(
                id = 2,
                name = "컴공 주점 부스2",
                location = "학생회관 앞",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다",
                waitingEnabled = true,
                thumbnail = "https://cdn.pixabay.com/photo/2020/06/07/13/33/fireworks-5270439_1280.jpg",
            ),
            BoothTabModel(
                id = 3,
                name = "컴공 주점 부스3",
                location = "학생회관 앞",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다",
                waitingEnabled = false,
                thumbnail = "https://cdn.pixabay.com/photo/2020/06/07/13/33/fireworks-5270439_1280.jpg",
            ),
            BoothTabModel(
                id = 4,
                name = "컴공 주점 부스4",
                location = "학생회관 앞",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다",
                waitingEnabled = true,
                thumbnail = "https://cdn.pixabay.com/photo/2020/06/07/13/33/fireworks-5270439_1280.jpg",
            ),
            BoothTabModel(
                id = 5,
                name = "컴공 주점 부스5",
                location = "학생회관 앞",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다",
                waitingEnabled = true,
                thumbnail = "https://cdn.pixabay.com/photo/2020/06/07/13/33/fireworks-5270439_1280.jpg",
            ),
            BoothTabModel(
                id = 6,
                name = "컴공 주점 부스6",
                location = "학생회관 앞",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다",
                waitingEnabled = true,
                thumbnail = "https://cdn.pixabay.com/photo/2020/06/07/13/33/fireworks-5270439_1280.jpg",
            ),
            BoothTabModel(
                id = 7,
                name = "컴공 주점 부스7",
                location = "학생회관 앞",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다",
                waitingEnabled = true,
                thumbnail = "https://cdn.pixabay.com/photo/2020/06/07/13/33/fireworks-5270439_1280.jpg",
            ),
            BoothTabModel(
                id = 8,
                name = "컴공 주점 부스8",
                location = "학생회관 앞",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다",
                waitingEnabled = false,
                thumbnail = "https://cdn.pixabay.com/photo/2020/06/07/13/33/fireworks-5270439_1280.jpg",
            ),
        )

        viewModelScope.launch {
            boothRepository.getTabBooths(festivalId = 1)
                .onSuccess { booths ->
                    _uiState.update {
                        it.copy(
                            boothList = booths.toPersistentList(),
                            showingBoothList = booths.toPersistentList(),
                            totalBoothCount = booths.size,
                        )
                    }
                }
                .onFailure { exception ->
                    handleException(exception, this@BoothViewModel)
                }
        }

//        _uiState.update {
//            it.copy(
//                campusName = "가천대 글로벌 캠퍼스",
//                boothList = boothList,
//                totalBoothCount = boothList.size,
//                showingBoothList = boothList,
//            )
//        }
    }

    private fun navigateToBoothDetail(boothId: Long) {
        viewModelScope.launch {
            _uiEvent.send(BoothUiEvent.NavigateToBoothDetail(boothId))
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

    private fun refresh(error: ErrorType) {
        fetchBoothList()
        when (error) {
            ErrorType.NETWORK -> setNetworkErrorDialogVisible(false)
            ErrorType.SERVER -> setServerErrorDialogVisible(false)
        }
    }
}
