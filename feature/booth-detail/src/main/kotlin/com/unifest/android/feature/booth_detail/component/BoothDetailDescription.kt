package com.unifest.android.feature.booth_detail.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unifest.android.core.common.extension.toFormattedString
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.component.UnifestOutlinedButton
import com.unifest.android.core.designsystem.theme.BoothCaution
import com.unifest.android.core.designsystem.theme.BoothLocation
import com.unifest.android.core.designsystem.theme.BoothTitle1
import com.unifest.android.core.designsystem.theme.Content2
import com.unifest.android.core.designsystem.theme.Title5
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.model.ScheduleModel
import com.unifest.android.feature.booth_detail.R
import com.unifest.android.feature.booth_detail.viewmodel.BoothDetailUiAction
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import com.unifest.android.core.designsystem.R as designR

@Composable
internal fun BoothDetailDescription(
    name: String,
    warning: String,
    description: String,
    location: String,
    isScheduleExpanded: Boolean,
    scheduleList: ImmutableList<ScheduleModel>,
    isBoothRunning: Boolean,
    onAction: (BoothDetailUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp)
            .animateContentSize(),
    ) {
        Text(
            text = name,
            color = MaterialTheme.colorScheme.onBackground,
            style = BoothTitle1,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = warning,
            style = BoothCaution,
            color = MaterialTheme.colorScheme.primary,
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = description,
            modifier = Modifier.padding(top = 8.dp),
            color = MaterialTheme.colorScheme.onSecondary,
            style = Content2.copy(lineHeight = 18.sp),
        )
        if (scheduleList.isNotEmpty()) {
            Spacer(modifier = Modifier.height(22.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clickable { onAction(BoothDetailUiAction.OnScheduleToggleClick) },
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_clock),
                    contentDescription = "location icon",
                    tint = Color.Unspecified,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isBoothRunning) stringResource(id = R.string.booth_is_running)
                    else stringResource(id = R.string.booth_is_closed),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = BoothLocation,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    modifier = if (isScheduleExpanded) Modifier.scale(scaleX = 1f, scaleY = -1f) else Modifier,
                    imageVector = ImageVector.vectorResource(designR.drawable.ic_arrow_below),
                    contentDescription = "Arrow Down",
                    tint = Color.Unspecified,
                )
            }
            AnimatedVisibility(visible = isScheduleExpanded) {
                LazyColumn(
                    modifier = Modifier
                        .height((23 * scheduleList.size).dp)
                        .padding(start = 24.dp),
                    contentPadding = PaddingValues(vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(7.dp),
                ) {
                    items(
                        items = scheduleList,
                        key = { it.id },
                    ) { schedule ->
                        Text(
                            text = schedule.toFormattedString(),
                            color = MaterialTheme.colorScheme.onBackground,
                            style = BoothLocation,
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(11.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 8.dp),
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = designR.drawable.ic_location_green),
                contentDescription = "location icon",
                tint = Color.Unspecified,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = location,
                color = MaterialTheme.colorScheme.onBackground,
                style = BoothLocation,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        UnifestOutlinedButton(
            onClick = {
                onAction(BoothDetailUiAction.OnCheckLocationClick)
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = stringResource(id = R.string.booth_check_location),
                style = Title5,
            )
        }
    }
}

@ComponentPreview
@Composable
private fun BoothDetailDescriptionNoSchedulePreview() {
    UnifestTheme {
        BoothDetailDescription(
            name = "공대주점",
            warning = "누구나 환영",
            description = "컴퓨터 공학과와 물리학과가 함께하는 협동부스입니다. 방문자 이벤트로 무료 안주 하나씩 제공중이에요!!",
            location = "공학관",
            isScheduleExpanded = false,
            scheduleList = persistentListOf(),
            isBoothRunning = false,
            onAction = {},
        )
    }
}

@ComponentPreview
@Composable
private fun BoothDetailDescriptionClosedPreview() {
    UnifestTheme {
        BoothDetailDescription(
            name = "공대주점",
            warning = "누구나 환영",
            description = "컴퓨터 공학과와 물리학과가 함께하는 협동부스입니다. 방문자 이벤트로 무료 안주 하나씩 제공중이에요!!",
            location = "공학관",
            isScheduleExpanded = false,
            scheduleList = persistentListOf(
                ScheduleModel(
                    id = 14,
                    date = "2025-03-12",
                    openTime = "10:00:00",
                    closeTime = "18:00:00",
                ),
                ScheduleModel(
                    id = 15,
                    date = "2025-03-13",
                    openTime = "10:00:00",
                    closeTime = "18:00:00",
                ),
            ),
            isBoothRunning = false,
            onAction = {},
        )
    }
}

@ComponentPreview
@Composable
private fun BoothDetailDescriptionOpenPreview() {
    UnifestTheme {
        BoothDetailDescription(
            name = "공대주점",
            warning = "누구나 환영",
            description = "컴퓨터 공학과와 물리학과가 함께하는 협동부스입니다. 방문자 이벤트로 무료 안주 하나씩 제공중이에요!!",
            location = "공학관",
            isScheduleExpanded = false,
            scheduleList = persistentListOf(
                ScheduleModel(
                    id = 14,
                    date = "2025-05-07",
                    openTime = "10:00:00",
                    closeTime = "18:00:00",
                ),
                ScheduleModel(
                    id = 15,
                    date = "2025-05-08",
                    openTime = "10:00:00",
                    closeTime = "18:00:00",
                ),
            ),
            isBoothRunning = true,
            onAction = {},
        )
    }
}

@ComponentPreview
@Composable
private fun BoothDetailDescriptionOpenDropdownExpandedPreview() {
    UnifestTheme {
        BoothDetailDescription(
            name = "공대주점",
            warning = "누구나 환영",
            description = "컴퓨터 공학과와 물리학과가 함께하는 협동부스입니다. 방문자 이벤트로 무료 안주 하나씩 제공중이에요!!",
            location = "공학관",
            isScheduleExpanded = true,
            scheduleList = persistentListOf(
                ScheduleModel(
                    id = 14,
                    date = "2025-05-07",
                    openTime = "10:00:00",
                    closeTime = "18:00:00",
                ),
                ScheduleModel(
                    id = 15,
                    date = "2025-05-08",
                    openTime = "10:00:00",
                    closeTime = "18:00:00",
                ),
            ),
            isBoothRunning = true,
            onAction = {},
        )
    }
}
