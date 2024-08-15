package com.unifest.android.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun CircularOutlineButton(
    icon: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit,
    borderColor: Color = Color(0xFFD2D2D2),
    iconTintColor: Color = MaterialTheme.colorScheme.onBackground,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .size(27.dp),
        shape = CircleShape,
        border = BorderStroke(1.dp, borderColor),
        contentPadding = PaddingValues(0.dp),
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = iconTintColor,
                modifier = Modifier.size(13.dp),
            )
        }
    }
}
