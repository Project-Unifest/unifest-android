package com.unifest.android.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.placeholder.PlaceholderPlugin
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.theme.Content9
import com.unifest.android.core.designsystem.theme.UnifestTheme

@Composable
fun NetworkImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    placeholder: Painter? = null,
    contentScale: ContentScale = ContentScale.Crop,
    contentDescription: String? = null,
    onClick: () -> Unit = {},
    isClicked: Boolean = false,
    label: String = "",
    isClickable: Boolean = true,
) {
    Box(
        modifier = modifier
            .clickable(enabled = isClickable, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        if (LocalInspectionMode.current) {
            Icon(
                imageVector = Icons.Outlined.Person,
                contentDescription = "Placeholder Image",
                modifier = Modifier
                    .size(72.dp)
                    .align(Alignment.Center),
            )
        } else {
            CoilImage(
                imageModel = { imageUrl },
                component = rememberImageComponent {
                    +PlaceholderPlugin.Loading(placeholder)
                    +PlaceholderPlugin.Failure(placeholder)
                },
                imageOptions = ImageOptions(
                    contentScale = contentScale,
                    alignment = Alignment.Center,
                    contentDescription = contentDescription,
                ),
            )
            if (isClicked && isClickable) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.Black.copy(alpha = 0.6f)),
                )
                Text(
                    text = label,
                    color = Color.White,
                    style = Content9,
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        }
    }
}

@ComponentPreview
@Composable
fun NetworkImagePreview() {
    UnifestTheme {
        NetworkImage(imageUrl = "")
    }
}
