package com.unifest.android.feature.home.viewmodel

import com.unifest.android.core.domain.entity.FestivalEventEntity
import com.unifest.android.core.domain.entity.IncomingFestivalEventEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class HomeUiState(
    val incomingEvents: ImmutableList<IncomingFestivalEventEntity> = persistentListOf(),
    val festivalEvents: ImmutableList<FestivalEventEntity> = persistentListOf(),
)
