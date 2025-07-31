package com.unifest.android.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.ui.DevicePreview
import com.unifest.android.feature.home.component.OriginalCardNews

@Composable
internal fun HomeCardNewsRoute(
    popBackStack: () -> Unit,
    imageUrl: String,
) {
    val density = LocalDensity.current

    val screenWidth = LocalWindowInfo.current.containerSize.width
    val screenHeight = LocalWindowInfo.current.containerSize.height
    val insets = WindowInsets.systemBars.asPaddingValues()

    val usableHeight = with(density) {
        (screenHeight.toDp() -
            insets.calculateTopPadding() - insets.calculateBottomPadding())
            .toPx()
            .toInt()
    }

    HomeCardNewsScreen(
        imageUrl = imageUrl,
        screenWidth = screenWidth,
        screenHeight = usableHeight,
        popBackStack = popBackStack,
    )
}

@Composable
internal fun HomeCardNewsScreen(
    imageUrl: String,
    screenWidth: Int,
    screenHeight: Int,
    popBackStack: () -> Unit,
) {
    Box(
        modifier = Modifier
            .background(Color.Black)
            .systemBarsPadding()
            .fillMaxSize(),
    ) {
        OriginalCardNews(
            modifier = Modifier
                .fillMaxSize()
                .clipToBounds(),
            imageUrl = imageUrl,
            screenWidth = screenWidth,
            screenHeight = screenHeight,
        )

        Icon(
            modifier = Modifier
                .padding(10.dp)
                .clip(CircleShape)
                .background(Color.Black)
                .align(Alignment.TopEnd)
                .clickable(onClick = { popBackStack() })
                .padding(20.dp),
            painter = painterResource(id = R.drawable.ic_close_24),
            contentDescription = null,
            tint = Color.Unspecified,
        )
    }
}

@DevicePreview
@Composable
private fun HomeCardNewsPreview() {
    UnifestTheme {
        val screenWidth = LocalWindowInfo.current.containerSize.width
        val screenHeight = LocalWindowInfo.current.containerSize.height

        HomeCardNewsScreen(
            imageUrl = "https://example.com/image.jpg",
            popBackStack = {},
            screenWidth = screenWidth,
            screenHeight = screenHeight,
        )
    }
}
