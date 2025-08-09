package com.unifest.android.core.model

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

@Stable
@Serializable
data class CardNewsModel(
    val coverImgUrl: String = "",
    val originalUrl: String = "",
)
