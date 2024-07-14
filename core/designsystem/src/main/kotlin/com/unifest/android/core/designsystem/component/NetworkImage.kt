package com.unifest.android.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.placeholder.PlaceholderPlugin
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.DarkComponentPreview
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.theme.UnifestTheme

@Composable
fun NetworkImage(
    imgUrl: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    placeholder: Painter? = null,
    contentScale: ContentScale = ContentScale.Crop,
) {
    if (LocalInspectionMode.current) {
        Image(
            painter = painterResource(id = R.drawable.item_placeholder),
            contentDescription = "Example Image Icon",
            modifier = modifier,
        )
    } else {
        CoilImage(
            imageModel = { imgUrl },
            modifier = modifier,
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
    }
}

@ComponentPreview
@Composable
fun NetworkImagePreview() {
    UnifestTheme {
        NetworkImage(
            imgUrl = "",
            contentDescription = "",
        )
    }
}

@DarkComponentPreview
@Composable
fun NetworkImageDarkPreview() {
    UnifestTheme {
        NetworkImage(
            imgUrl = "",
            contentDescription = "",
        )
    }
}
