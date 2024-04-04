package com.unifest.android.feature.map.viewmodel

import com.google.maps.android.compose.MapProperties
import com.unifest.android.core.domain.entity.BoothSpot
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MapUiState(
    val properties: MapProperties = MapProperties(),
    val boothSpots: ImmutableList<BoothSpot> = persistentListOf(),
)
