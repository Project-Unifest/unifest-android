package com.unifest.android.core.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.DarkComponentPreview
import com.unifest.android.core.designsystem.component.AutoResizedText
import com.unifest.android.core.designsystem.component.NetworkImage
import com.unifest.android.core.designsystem.theme.Content9
import com.unifest.android.core.designsystem.theme.UnifestTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StarImage(
    imgUrl: String?,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    isClicked: Boolean,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: Painter? = null,
    contentScale: ContentScale = ContentScale.Crop,
    contentDescription: String? = null,
) {
    Box(
        modifier = modifier
            .combinedClickable(
                onLongClick = onLongClick,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        NetworkImage(
            imgUrl = imgUrl,
            modifier = Modifier.matchParentSize(),
            placeholder = placeholder,
            contentScale = contentScale,
            contentDescription = contentDescription,
        )
        if (isClicked) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Black.copy(alpha = 0.6f)),
            )
            AutoResizedText(
                text = label,
                color = Color.White,
                style = Content9,
            )
        }
    }
}

@ComponentPreview
@Composable
fun StarImagePreview() {
    UnifestTheme {
        StarImage(
            imgUrl = "",
            onClick = {},
            onLongClick = {},
            isClicked = false,
            label = "",
        )
    }
}

@DarkComponentPreview
@Composable
fun StarImageDarkPreview() {
    UnifestTheme {
        StarImage(
            imgUrl = "",
            onClick = {},
            onLongClick = {},
            isClicked = false,
            label = "",
        )
    }
}

@ComponentPreview
@Composable
fun StarImageClickedPreview() {
    UnifestTheme {
        StarImage(
            imgUrl = "",
            onClick = {},
            onLongClick = {},
            isClicked = true,
            label = "키스오브라이프",
        )
    }
}

@DarkComponentPreview
@Composable
fun StarImageClickedDarkPreview() {
    UnifestTheme {
        StarImage(
            imgUrl = "",
            onClick = {},
            onLongClick = {},
            isClicked = true,
            label = "키스오브라이프",
        )
    }
}
