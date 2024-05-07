package com.unifest.android.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.compose.Balloon
import com.skydoves.balloon.compose.rememberBalloonBuilder
import com.skydoves.balloon.compose.setBackgroundColor
import com.unifest.android.core.common.extension.noRippleClickable
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.theme.Content5
import com.unifest.android.core.designsystem.theme.MainColor
import com.unifest.android.core.designsystem.theme.Title1
import com.unifest.android.core.designsystem.theme.UnifestTheme
import kotlinx.coroutines.launch

@Composable
fun ToolTip(
    description: String,
    arrowOrientation: ArrowOrientation,
    arrowPosition: Float,
    completeOnboarding: (Boolean) -> Unit,
    content: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val builder = rememberBalloonBuilder {
        setArrowSize(10)
        setArrowPosition(arrowPosition)
        setArrowOrientation(arrowOrientation)
        setWidth(BalloonSizeSpec.WRAP)
        setHeight(BalloonSizeSpec.WRAP)
        setPadding(9)
        setCornerRadius(8f)
        setBackgroundColor(MainColor)
        setBalloonAnimation(BalloonAnimation.FADE)
        setDismissWhenClicked(true)
        setDismissWhenTouchOutside(false)
        setFocusable(false)
    }

    Balloon(
        builder = builder,
        balloonContent = {
            Text(
                modifier = Modifier
                    .wrapContentWidth()
                    .noRippleClickable {
                        completeOnboarding(true)
                    },
                text = description,
                textAlign = TextAlign.Center,
                color = Color.White,
                style = Content5,
            )
        },
    ) { balloonWindow ->
        content()
        LaunchedEffect(key1 = Unit) {
            scope.launch {
                balloonWindow.awaitAlignEnd()
            }
        }
    }
}

@Composable
fun LikedFestivalToolTip(
    completeOnboarding: (Boolean) -> Unit,
) {
    ToolTip(
        description = stringResource(id = R.string.festival_search_onboarding_title),
        arrowPosition = 0.1f,
        arrowOrientation = ArrowOrientation.BOTTOM,
        completeOnboarding = completeOnboarding,
    ) {
        Row(
            modifier = Modifier
                .padding(start = 12.dp, top = 10.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {}
    }
}

@Composable
fun SchoolSearchTitleWithToolTip(
    title: String,
    onTitleClick: (Boolean) -> Unit,
    completeOnboarding: (Boolean) -> Unit,
) {
    ToolTip(
        description = stringResource(id = R.string.map_school_search_tool_tip_description),
        arrowPosition = 0.5f,
        arrowOrientation = ArrowOrientation.START,
        completeOnboarding = completeOnboarding,
    ) {
        Row(
            modifier = Modifier
                .padding(start = 22.dp, top = 10.dp, bottom = 10.dp, end = 9.dp)
                .clickable {
                    onTitleClick(true)
                    completeOnboarding(true)
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
}

@ComponentPreview
@Composable
fun LikedFestivalToolTipPreview() {
    UnifestTheme {
        LikedFestivalToolTip(
            completeOnboarding = {},
        )
    }
}

@ComponentPreview
@Composable
fun SchoolSearchTitleWithToolTipPreview() {
    UnifestTheme {
        SchoolSearchTitleWithToolTip(
            title = "건국대학교",
            onTitleClick = {},
            completeOnboarding = {},
        )
    }
}
