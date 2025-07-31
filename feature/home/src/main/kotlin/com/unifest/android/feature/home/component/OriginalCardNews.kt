package com.unifest.android.feature.home.component

import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalWindowInfo
import coil.compose.AsyncImage
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.theme.UnifestTheme
import kotlin.math.absoluteValue

// https://developer.android.com/develop/ui/compose/touch-input/pointer-input/multi-touch?hl=ko
@Composable
internal fun OriginalCardNews(
    modifier: Modifier = Modifier,
    screenWidth: Int,
    screenHeight: Int,
    imageUrl: String,
) {
    var imageWidth by remember { mutableIntStateOf(0) }
    var imageHeight by remember { mutableIntStateOf(0) }

    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    val state = rememberTransformableState { zoomChange, offsetChange, _ ->
        scale = (zoomChange * scale).coerceAtLeast(minimumValue = 1f)
        val maxOffsetX = (((imageWidth * scale) - screenWidth) / 2).absoluteValue
        val maxOffsetY = (((imageHeight * scale) - screenHeight) / 2).absoluteValue
        offset = if (scale == 1f) {
            Offset.Zero
        } else {
            Offset(
                x = (offset.x + offsetChange.x * scale).coerceIn(-maxOffsetX, maxOffsetX),
                y = (offset.y + offsetChange.y * scale).coerceIn(-maxOffsetY, maxOffsetY),
            )
        }
    }

    Box(
        modifier = modifier,
    ) {
        AsyncImage(
            modifier = Modifier
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offset.x,
                    translationY = offset.y,
                )
                .transformable(state = state)
                .fillMaxWidth()
                .align(Alignment.Center)
                .onGloballyPositioned { layoutCoordinates ->
                    imageWidth = layoutCoordinates.size.width
                    imageHeight = layoutCoordinates.size.height
                },
            model = imageUrl,
            contentDescription = "",
        )
    }
}

@ComponentPreview
@Composable
private fun OriginalCardNewsPreview() {
    UnifestTheme {
        val screenWidth = LocalWindowInfo.current.containerSize.width
        val screenHeight = LocalWindowInfo.current.containerSize.height

        OriginalCardNews(
            modifier = Modifier,
            screenWidth = screenWidth,
            screenHeight = screenHeight,
            imageUrl = "",
        )
    }
}
