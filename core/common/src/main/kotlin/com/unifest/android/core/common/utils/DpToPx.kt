package com.unifest.android.core.common.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun dpToPx(dp: Dp): Float {
    return with(LocalDensity.current) { dp.toPx() }
}
