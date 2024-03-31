package com.unifest.android.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsCompat
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.theme.Title0
import com.unifest.android.core.designsystem.theme.UnifestTheme

enum class TopAppBarNavigationType { None, Back }

@Composable
fun UnifestTopAppBar(
    @StringRes titleRes: Int,
    navigationType: TopAppBarNavigationType,
    modifier: Modifier = Modifier,
    navigationIconContentDescription: String? = null,
    containerColor: Color = Color.White,
    contentColor: Color = Color.Black,
    onNavigationClick: () -> Unit = {},
) {
    val view = LocalView.current
    val insets = with(LocalDensity.current) {
        WindowInsetsCompat.toWindowInsetsCompat(view.rootWindowInsets, view).getInsets(WindowInsetsCompat.Type.statusBars()).top.toDp()
    }
    CompositionLocalProvider(LocalContentColor provides contentColor) {
        val icon: @Composable (Modifier, imageVector: ImageVector) -> Unit =
            { modifier, imageVector ->
                IconButton(
                    onClick = onNavigationClick,
                    modifier = modifier.size(48.dp),
                ) {
                    Icon(
                        imageVector = imageVector,
                        contentDescription = navigationIconContentDescription,
                    )
                }
            }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(containerColor)
                .padding(top = insets)
                .then(modifier),
        ) {
            if (navigationType == TopAppBarNavigationType.Back) {
                icon(
                    Modifier.align(Alignment.CenterStart),
                    ImageVector.vectorResource(id = R.drawable.ic_arrow_back),
                )
            }
            Text(
                text = stringResource(id = titleRes),
                modifier = if (navigationType == TopAppBarNavigationType.Back) {
                    Modifier
                        .align(Alignment.Center)
                        .padding(top = 18.dp, bottom = 18.dp)
                } else {
                    Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 20.dp, top = 18.dp, bottom = 18.dp)
                },
                style = Title0,
            )
        }
    }
}

@ComponentPreview
@Composable
fun UnifestTopAppBarPreview() {
    UnifestTheme {
        UnifestTopAppBar(
            titleRes = android.R.string.untitled,
            navigationType = TopAppBarNavigationType.None,
        )
    }
}

@ComponentPreview
@Composable
fun UnifestTopAppBarWithBackButtonPreview() {
    UnifestTheme {
        UnifestTopAppBar(
            titleRes = android.R.string.untitled,
            navigationType = TopAppBarNavigationType.Back,
            navigationIconContentDescription = "Navigation back icon",
        )
    }
}
