package com.unifest.android.feature.map.viewmodel

import com.google.maps.android.compose.MapProperties
import com.unifest.android.core.domain.entity.BoothSpot

data class MapUiState(
    val properties: MapProperties = MapProperties(),
    val boothSpots: List<BoothSpot> = emptyList(),
)
