package com.unifest.android.feature.booth.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unifest.android.core.common.extension.checkNotificationPermission
import com.unifest.android.core.common.extension.clickableSingle
import com.unifest.android.core.common.extension.findActivity
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.component.UnifestButton
import com.unifest.android.core.designsystem.theme.BoothCaution
import com.unifest.android.core.designsystem.theme.Title4
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.feature.booth.R
import com.unifest.android.core.designsystem.R as designR
import com.unifest.android.feature.booth.viewmodel.BoothUiAction

@Composable
internal fun BoothBottomBar(
    bookmarkCount: Int,
    isBookmarked: Boolean,
    isWaitingEnable: Boolean,
    onAction: (BoothUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val activity = context.findActivity()

    Surface(
        modifier = modifier.height(116.dp),
        shadowElevation = 32.dp,
        color = MaterialTheme.colorScheme.surface,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 27.dp, top = 15.dp, end = 15.dp, bottom = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(if (isBookmarked) designR.drawable.ic_bookmarked else designR.drawable.ic_bookmark),
                        contentDescription = if (isBookmarked) "Bookmarked" else "Bookmark",
                        tint = if (isBookmarked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.clickableSingle { onAction(BoothUiAction.OnToggleBookmark) },
                    )
                    Text(
                        text = "$bookmarkCount",
                        color = if (isBookmarked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                        style = BoothCaution.copy(fontWeight = FontWeight.Bold),
                    )
                }
                Spacer(modifier = Modifier.width(18.dp))
                UnifestButton(
                    onClick = { if (activity.checkNotificationPermission()) {
                        onAction(BoothUiAction.OnWaitingButtonClick)
                    } else {
                        onAction(BoothUiAction.OnRequestNotificationPermission)
                    }},
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 15.dp),
                    enabled = isWaitingEnable,
                    containerColor = if (isWaitingEnable) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                ) {
                    Text(
                        text = if (isWaitingEnable) stringResource(R.string.booth_waiting_button)
                        else stringResource(R.string.booth_waiting_button_invalid),
                        style = Title4,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            }
        }
    }
}

@ComponentPreview
@Composable
private fun BoothBottomBarPreview() {
    UnifestTheme {
        BoothBottomBar(
            bookmarkCount = 12,
            isBookmarked = true,
            isWaitingEnable = true,
            onAction = {},
        )
    }
}
