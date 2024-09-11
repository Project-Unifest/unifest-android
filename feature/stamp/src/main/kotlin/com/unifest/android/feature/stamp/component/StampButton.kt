package com.unifest.android.feature.stamp.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.unifest.android.core.common.utils.dpToPx
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.theme.Title2
import com.unifest.android.core.designsystem.theme.UnifestTheme

@Composable
internal fun StampButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val widthPx = dpToPx(196.dp)
    val heightPx = dpToPx(78.dp)

    val gradient = Brush.linearGradient(
        colorStops = arrayOf(
            0.0f to Color(0xF5FF8699),
            0.45f to Color(0xFFFF4264),
            1.0f to Color(0xFFEF39FF),
        ),
        start = Offset(0f, 0f),
        end = Offset(widthPx, heightPx),
    )

    Button(
        onClick = onClick,
        modifier = modifier
            .width(140.dp)
            .height(52.dp)
            .background(gradient, shape = RoundedCornerShape(26.dp))
            .then(Modifier.clip(RoundedCornerShape(26.dp))),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
    ) {
        Text(
            text = text,
            color = Color.White,
            style = Title2,
        )
    }
}

@ComponentPreview
@Composable
private fun StampButtonPreview() {
    UnifestTheme {
        StampButton(
            text = "스탬프 받기",
            onClick = {},
        )
    }
}
