package com.unifest.android.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.unifest.android.core.common.extension.noRippleClickable
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.theme.UnifestTheme

@Composable
fun CircularOutlineButton(
    icon: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit,
    borderColor: Color = Color(0xFFD2D2D2),
    iconTintColor: Color = MaterialTheme.colorScheme.onBackground,
) {
    Box(
        modifier = Modifier
            .size(27.dp)
            .border(BorderStroke(1.dp, borderColor), shape = CircleShape)
            .noRippleClickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = iconTintColor,
            modifier = Modifier.size(13.dp),
        )
    }
}

@ComponentPreview
@Composable
private fun CircularOutlineButtonPreview() {
    UnifestTheme {
        CircularOutlineButton(
            icon = Icons.Default.Remove,
            contentDescription = "Minus Button",
            onClick = {},
        )
    }
}
