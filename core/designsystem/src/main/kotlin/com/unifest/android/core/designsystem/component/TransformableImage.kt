package com.unifest.android.core.designsystem.component

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalWindowInfo
import coil.compose.AsyncImage
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.theme.UnifestTheme
import kotlin.math.absoluteValue

@Composable
fun TransformableImage(
    modifier: Modifier = Modifier,
    screenWidth: Int,
    screenHeight: Int,
    imageUrl: String,
) {
    var imageWidth by remember { mutableIntStateOf(0) }
    var imageHeight by remember { mutableIntStateOf(0) }

    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Companion.Zero) }

    val scaledWidth by remember(imageWidth, scale) { derivedStateOf { imageWidth * scale } }
    val scaledHeight by remember(imageHeight, scale) { derivedStateOf { imageHeight * scale } }
    val imageRect by remember(
        scaledWidth,
        scaledHeight,
        offset,
    ) {
        derivedStateOf {
            Rect(
                offset = Offset(
                    x = ((screenWidth - scaledWidth) / 2f + offset.x).coerceAtLeast(0f),
                    y = ((screenHeight - scaledHeight) / 2f + offset.y).coerceAtLeast(0f),
                ),
                size = Size(scaledWidth, scaledHeight),
            )
        }
    }

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectTransformGestures { centroid, offestChange, zoomChange, _ ->
                    if (!imageRect.contains(centroid)) return@detectTransformGestures
                    // 줌 변경 시 핀치 포인트를 기준으로 확대/축소
                    if (zoomChange != 1f) {
                        val newScale = (scale * zoomChange).coerceAtLeast(minimumValue = 1f)
                        // 현재 이미지의 실제 위치 (중심점 + 오프셋)
                        val currentImageCenter = Offset(
                            x = screenWidth / 2f + offset.x,
                            y = screenHeight / 2f + offset.y,
                        )

                        // 핀치 포인트에서 현재 이미지 중심까지의 거리
                        val relativePoint = centroid - currentImageCenter

                        // 스케일 변화에 따른 오프셋 조정 (핀치 포인트가 고정되도록)
                        val scaleChange = newScale / scale
                        var newOffsetX = offset.x + relativePoint.x * (1f - scaleChange)
                        var newOffsetY = offset.y + relativePoint.y * (1f - scaleChange)
                        newOffsetX += offestChange.x
                        newOffsetY += offestChange.y

                        scale = newScale

                        // 경계 체크 및 오프셋 제한
                        val maxOffsetX = ((imageWidth * newScale - screenWidth) / 2f).coerceAtLeast(0f)
                        val maxOffsetY = ((imageHeight * newScale - screenHeight) / 2f).coerceAtLeast(0f)

                        offset = if (newScale == 1f) {
                            Offset.Zero
                        } else {
                            Offset(
                                x = newOffsetX.coerceIn(-maxOffsetX, maxOffsetX),
                                y = newOffsetY.coerceIn(-maxOffsetY, maxOffsetY),
                            )
                        }

                        // 스케일 변경 후 드래그 처리로 이동
                        return@detectTransformGestures
                    }

                    // 드래그 적용
                    val maxOffsetX = ((scaledWidth - screenWidth) / 2f).absoluteValue
                    val maxOffsetY = ((scaledHeight - screenHeight) / 2f).absoluteValue
                    offset = if (scale == 1f) {
                        Offset.Companion.Zero
                    } else {
                        Offset(
                            x = (offset.x + offestChange.x).coerceIn(
                                -maxOffsetX,
                                maxOffsetX,
                            ),
                            y = (offset.y + offestChange.y).coerceIn(
                                -maxOffsetY,
                                maxOffsetY,
                            ),
                        )
                    }
                }
            },
    ) {
        AsyncImage(
            modifier = Modifier
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offset.x,
                    translationY = offset.y,
                )
                .align(Alignment.Companion.Center)
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
private fun TransformableImagePreview() {
    UnifestTheme {
        val screenWidth = LocalWindowInfo.current.containerSize.width
        val screenHeight = LocalWindowInfo.current.containerSize.height

        TransformableImage(
            modifier = Modifier,
            screenWidth = screenWidth,
            screenHeight = screenHeight,
            imageUrl = "",
        )
    }
}
