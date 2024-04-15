package com.unifest.android.feature.home.viewmodel

import com.unifest.android.core.model.FestivalEvent
import com.unifest.android.core.model.IncomingFestivalEvent
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class HomeUiState(
    val incomingEvents: ImmutableList<IncomingFestivalEvent> = persistentListOf(),
    val festivalEvents: ImmutableList<FestivalEvent> = persistentListOf(),
)
