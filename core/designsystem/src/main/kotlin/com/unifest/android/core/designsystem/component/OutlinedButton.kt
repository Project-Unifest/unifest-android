package com.unifest.android.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.unifest.android.core.designsystem.ComponentPreview

@Composable
fun UnifestOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    borderColor: Color = Color(0xFFf5678E),
    contentColor: Color = Color(0xFFf5678E),
    enabled: Boolean = true,
    cornerRadius: Dp = 10.dp,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(cornerRadius),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = contentColor,
        ),
        border = BorderStroke(1.dp, borderColor),
        contentPadding = contentPadding,
        content = content,
    )
}

@ComponentPreview
@Composable
fun UnifestOutlinedButtonPreview() {
    UnifestOutlinedButton(
        onClick = {},
    ) {
        Text("Outlined Button")
    }
}
