package com.unifest.android.core.designsystem.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.theme.Title1
import com.unifest.android.core.designsystem.theme.UnifestTheme

enum class TopAppBarNavigationType { None, Back, Search }

@Composable
fun UnifestTopAppBar(
    navigationType: TopAppBarNavigationType,
    modifier: Modifier = Modifier,
    title: String = "",
    titleStyle: TextStyle = Title1,
    @DrawableRes navigationIconRes: Int = R.drawable.ic_arrow_back_dark_gray,
    navigationIconContentDescription: String? = null,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = MaterialTheme.colorScheme.onBackground,
    onNavigationClick: () -> Unit = {},
    onTitleClick: (Boolean) -> Unit = {},
    isOnboardingCompleted: Boolean = false,
    onTooltipClick: (Boolean) -> Unit = {},
    elevation: Dp = 0.dp,
) {
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
                        tint = MaterialTheme.colorScheme.onBackground,
                    )
                }
            }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation, RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp))
                .background(containerColor)
                .then(modifier),
        ) {
            if (navigationType == TopAppBarNavigationType.Back) {
                icon(
                    Modifier.align(Alignment.CenterStart),
                    ImageVector.vectorResource(id = navigationIconRes),
                )
            }
            if (navigationType == TopAppBarNavigationType.Search) {
                if (isOnboardingCompleted) {
                    SchoolSearchTitle(
                        title = title,
                        onTitleClick = onTitleClick,
                    )
                } else {
                    SchoolSearchTitleWithToolTip(
                        title = title,
                        onTitleClick = onTitleClick,
                        completeOnboarding = onTooltipClick,
                    )
                }
            } else {
                Text(
                    text = title,
                    modifier = if (navigationType == TopAppBarNavigationType.Back) {
                        Modifier
                            .align(Alignment.Center)
                            .padding(vertical = 18.dp)
                    } else {
                        Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 20.dp, top = 18.dp, bottom = 18.dp)
                    },
                    style = titleStyle,
                )
            }
        }
    }
}

@Composable
fun SchoolSearchTitle(
    title: String,
    onTitleClick: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(start = 22.dp, top = 10.dp, bottom = 10.dp, end = 9.dp)
            .clickable {
                onTitleClick(true)
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = Title1,
        )
        Spacer(modifier = Modifier.width(7.dp))
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_below),
            contentDescription = "Search School",
            tint = Color.Unspecified,
        )
    }
}

@ComponentPreview
@Composable
private fun UnifestTopAppBarPreview() {
    UnifestTheme {
        UnifestTopAppBar(
            navigationType = TopAppBarNavigationType.None,
            title = "UniFest",
        )
    }
}

@ComponentPreview
@Composable
private fun SchoolSearchTitlePreview() {
    UnifestTheme {
        SchoolSearchTitle(
            title = "건국대학교",
            onTitleClick = {},
        )
    }
}

@ComponentPreview
@Composable
private fun UnifestTopAppBarWithBackButtonPreview() {
    UnifestTheme {
        UnifestTopAppBar(
            navigationType = TopAppBarNavigationType.Back,
            navigationIconContentDescription = "Navigation back icon",
        )
    }
}
