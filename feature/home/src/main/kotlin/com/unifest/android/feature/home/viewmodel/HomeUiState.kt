package com.unifest.android.feature.home.viewmodel

import com.unifest.android.core.model.FestivalEventModel
import com.unifest.android.core.model.IncomingFestivalEventModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class HomeUiState(
    val incomingEvents: ImmutableList<IncomingFestivalEventModel> = persistentListOf(),
    val festivalEvents: ImmutableList<FestivalEventModel> = persistentListOf(),
)
