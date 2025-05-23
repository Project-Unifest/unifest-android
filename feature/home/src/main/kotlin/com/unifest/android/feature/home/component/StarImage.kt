package com.unifest.android.feature.home.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.component.AutoResizedText
import com.unifest.android.core.designsystem.component.NetworkImage
import com.unifest.android.core.designsystem.theme.Content9
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.model.StarInfoModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StarImage(
    starInfo: StarInfoModel,
    onClick: () -> Unit,
    // onLongClick: () -> Unit,
    // isClicked: Boolean,
    modifier: Modifier = Modifier,
    placeholder: Painter? = null,
    contentScale: ContentScale = ContentScale.Crop,
    contentDescription: String? = null,
) {
    var isStarImageLoadError by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .combinedClickable(
                // onLongClick = onLongClick,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        NetworkImage(
            imgUrl = starInfo.imgUrl,
            modifier = Modifier.matchParentSize(),
            placeholder = placeholder,
            contentScale = contentScale,
            contentDescription = contentDescription,
            onError = { isError -> isStarImageLoadError = isError },
        )
//        if (isClicked) {
//            Box(
//                modifier = Modifier
//                    .matchParentSize()
//                    .background(Color.Black.copy(alpha = 0.6f)),
//            )
//            AutoResizedText(
//                text = starInfo.name,
//                color = Color.White,
//                style = Content9,
//            )
//        }
        if (starInfo.name.isEmpty() || isStarImageLoadError) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
            )
            AutoResizedText(
                text = starInfo.name,
                color = Color.White,
                style = Content9,
            )
        }
    }
}

@ComponentPreview
@Composable
private fun StarImagePreview() {
    UnifestTheme {
        StarImage(
            starInfo = StarInfoModel(
                starId = 1L,
                imgUrl = "",
                name = "",
            ),
            onClick = {},
            // label = "",
        )
    }
}

@ComponentPreview
@Composable
private fun StarImageClickedPreview() {
    UnifestTheme {
        StarImage(
            starInfo = StarInfoModel(
                starId = 1L,
                imgUrl = "",
                name = "",
            ),
            onClick = {},
            // onLongClick = {},
            // isClicked = true,
            // label = "키스오브라이프",
        )
    }
}
