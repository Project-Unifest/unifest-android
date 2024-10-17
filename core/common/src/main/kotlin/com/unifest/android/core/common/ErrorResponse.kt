package com.unifest.android.core.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String,
)
