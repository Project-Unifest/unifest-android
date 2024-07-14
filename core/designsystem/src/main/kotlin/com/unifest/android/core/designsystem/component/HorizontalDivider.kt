package com.unifest.android.core.designsystem.component

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.DarkComponentPreview
import com.unifest.android.core.designsystem.theme.UnifestTheme

@Composable
fun UnifestHorizontalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 8.dp,
    color: Color = MaterialTheme.colorScheme.outline,
) {
    HorizontalDivider(
        thickness = thickness,
        color = color,
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

@DarkComponentPreview
@Composable
fun UnifestHorizontalDividerDarkPreview() {
    UnifestTheme {
        UnifestHorizontalDivider()
    }
}
