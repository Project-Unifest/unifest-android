package com.unifest.android.feature.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _waitingId: MutableStateFlow<Long> = MutableStateFlow(0L)
    val waitingId: StateFlow<Long> = _waitingId.asStateFlow()

    private val _boothId: MutableStateFlow<Long> = MutableStateFlow(0L)
    val boothId: StateFlow<Long> = _boothId.asStateFlow()

    fun setWaitingId(waitingId: Long) {
        _waitingId.update { waitingId }
    }

    fun setBoothId(boothId: Long) {
        _boothId.update { boothId }
    }
}
