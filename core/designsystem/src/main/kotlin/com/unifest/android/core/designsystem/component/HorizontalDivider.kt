package com.unifest.android.core.designsystem.component

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.theme.UnifestTheme

@Composable
fun UnifestHorizontalDivider(
    modifier: Modifier = Modifier,
) {
    HorizontalDivider(
        thickness = 8.dp,
        color = Color(0xFFF1F3F7),
        modifier = modifier,
    )
}

@ComponentPreview
@Composable
fun UnifestHorizontalDividerPreview() {
    UnifestTheme {
        UnifestHorizontalDivider()
    }
}
