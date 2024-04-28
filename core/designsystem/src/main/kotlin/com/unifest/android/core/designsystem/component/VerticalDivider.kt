package com.unifest.android.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.unifest.android.core.designsystem.ComponentPreview

@Composable
fun UnifestVerticalDivider(
    modifier: Modifier = Modifier,
) {
    VerticalDivider(
        modifier = modifier
            .fillMaxWidth()
            .height(8.dp)
            .background(Color(0xFFF1F3F7)),
    )
}

@ComponentPreview
@Composable
fun UnifestVerticalDividerPreview() {
    UnifestVerticalDivider()
}
