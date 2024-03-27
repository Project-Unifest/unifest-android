package com.unifest.android.feature.map.viewmodel

import androidx.lifecycle.ViewModel
import com.unifest.android.core.domain.entity.BoothSpot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = this._uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                boothSpots = listOf(
                    BoothSpot(37.54053013863604, 127.07505652524804),
                    BoothSpot(37.54111712868565, 127.07839319326257),
                    BoothSpot(37.5414744247141, 127.07779237844323),
                    BoothSpot(37.54224856023523, 127.07605430700158),
                    BoothSpot(37.54003672313541, 127.07653710462426),
                    BoothSpot(37.53998567996623, 37.53998567996623),
                    BoothSpot(37.54152546686414, 127.07353303052759),
                    BoothSpot(37.54047909580466, 127.07398364164209),
                ),
            )
        }
    }
}
