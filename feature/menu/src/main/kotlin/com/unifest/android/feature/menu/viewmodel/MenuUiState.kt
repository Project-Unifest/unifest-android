package com.unifest.android.feature.menu.viewmodel

import com.unifest.android.core.domain.entity.BoothDetailEntity
import com.unifest.android.core.domain.entity.Festival
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MenuUiState(
    val festivals: ImmutableList<Festival> = persistentListOf(),
    val interestedBooths: ImmutableList<BoothDetailEntity> = persistentListOf(),
    val appVersion: String = "",
)
