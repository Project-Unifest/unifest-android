package com.unifest.android.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}

@ComponentPreview
@Composable
fun LoadingWheelPreview() {
    UnifestTheme {
        LoadingWheel()
    }
}
