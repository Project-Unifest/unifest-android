package com.unifest.android.feature.stamp.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.theme.Content1
import com.unifest.android.core.designsystem.theme.Content2
import com.unifest.android.core.designsystem.theme.Title1
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.model.StampBoothModel
import com.unifest.android.feature.stamp.R
import com.unifest.android.feature.stamp.viewmodel.stamp.StampUiAction
import com.unifest.android.feature.stamp.viewmodel.stamp.StampUiState
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun StampBoothBottomSheet(
    schoolName: String,
    stampBoothList: ImmutableList<StampBoothModel>,
    onAction: (StampUiAction) -> Unit,
) {
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { it != SheetValue.Hidden },
    )
    val interactionSource = remember { MutableInteractionSource() }

    ModalBottomSheet(
        onDismissRequest = {
            onAction(StampUiAction.OnDismiss)
        },
        sheetState = bottomSheetState,
        sheetGesturesEnabled = false,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        dragHandle = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(vertical = 12.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = {},
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                HorizontalDivider(
                    thickness = 5.dp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier
                        .width(80.dp)
                        .clip(RoundedCornerShape(43.dp)),
                )
            }
        },
        contentWindowInsets = { WindowInsets(top = 0) },
        modifier = Modifier
            .fillMaxHeight()
            .statusBarsPadding()
            .padding(top = 18.dp)
            .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
            .background(MaterialTheme.colorScheme.surface),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .navigationBarsPadding(),
        ) {
            item {
                Column(
                    modifier = Modifier.padding(horizontal = 30.dp),
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = schoolName,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = Content1,
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = stringResource(id = R.string.stamp_booth),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = Title1,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "총 ${stampBoothList.size}개",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = Content2,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
            items(
                count = stampBoothList.size,
                key = { index -> stampBoothList[index].id },
            ) { index ->
                StampBoothItem(
                    stampBooth = stampBoothList[index],
                    modifier = Modifier.clickable {
                        onAction(StampUiAction.OnStampBoothItemClick(stampBoothList[index].id))
                    },
                )
            }
        }
    }
}

@ComponentPreview
@Composable
private fun SchoolSearchBottomSheetPreview() {
    UnifestTheme {
        StampBoothBottomSheet(
            schoolName = "",
            stampBoothList = StampUiState().stampBoothList,
            onAction = {},
        )
    }
}
