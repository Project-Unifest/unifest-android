package com.unifest.android.feature.booth.viewmodel

import com.unifest.android.core.model.BoothTabModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class BoothUiState(
    val campusName: String = "",
    val totalBoothCount: Int = 0,
    val waitingAvailabilityChecked: Boolean = false,
    val stampEnabledBoothList: ImmutableList<BoothTabModel> = persistentListOf(),
)
