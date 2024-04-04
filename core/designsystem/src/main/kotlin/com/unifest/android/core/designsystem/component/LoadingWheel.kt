package com.unifest.android.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.unifest.android.core.common.extension.noRippleClickable
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.theme.UnifestTheme

@Composable
fun LoadingWheel(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.noRippleClickable { },
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(color = Color.Black)
    }
}

@ComponentPreview
@Composable
fun LoadingWheelPreview() {
    UnifestTheme {
        LoadingWheel()
    }
}
