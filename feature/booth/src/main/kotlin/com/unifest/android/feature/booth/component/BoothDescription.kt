package com.unifest.android.feature.booth.component

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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalConfiguration
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
import com.unifest.android.feature.booth.R
import com.unifest.android.feature.booth.viewmodel.BoothUiAction
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import com.unifest.android.core.designsystem.R as designR

@Composable
internal fun BoothDescription(
    name: String,
    warning: String,
    description: String,
    location: String,
    isScheduleExpanded: Boolean,
    scheduleList: ImmutableList<ScheduleModel>,
    onAction: (BoothUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val configuration = LocalConfiguration.current
    val maxWidth = remember(configuration) {
        val screenWidth = configuration.screenWidthDp.dp - 40.dp
        screenWidth * (2 / 3f)
    }

    // 현재 시간과 날짜 가져오기
    val koreaZoneId = ZoneId.of("Asia/Seoul")
    val currentDateTime = ZonedDateTime.now(koreaZoneId)
    val currentTime = currentDateTime.toLocalTime()
    val currentDate = currentDateTime.toLocalDate()

    // 부스가 현재 운영 중인지 확인
    val isBoothRunning = scheduleList.any { schedule ->
        // 날짜 확인
        val scheduleDate = LocalDate.parse(schedule.date)
        val isToday = scheduleDate.equals(currentDate)

        // 오늘 날짜면 시간 확인
        if (isToday) {
            val openLocalTime = LocalTime.parse(schedule.openTime)
            val closeLocalTime = LocalTime.parse(schedule.closeTime)

            // 폐장 시간이 개장 시간보다 이른 경우(다음날로 넘어가는 경우)
            if (closeLocalTime.isBefore(openLocalTime)) {
                // 현재 시간이 개장 시간 이후면 운영 중
                currentTime.isAfter(openLocalTime) || currentTime.equals(openLocalTime)
            } else {
                // 일반적인 경우 - 같은 날 내에 운영 종료
                (currentTime.isAfter(openLocalTime) || currentTime.equals(openLocalTime)) &&
                    (currentTime.isBefore(closeLocalTime) || currentTime.equals(closeLocalTime))
            }
        } else {
            // 어제 날짜의 스케줄이고, 폐장 시간이 자정을 넘어가는 경우
            val yesterday = currentDate.minusDays(1)
            if (scheduleDate.equals(yesterday)) {
                val closeLocalTime = LocalTime.parse(schedule.closeTime)
                // 현재 시간이 폐장 시간보다 이전이면 아직 운영 중
                currentTime.isBefore(closeLocalTime) || currentTime.equals(closeLocalTime)
            } else {
                false
            }
        }
    }

    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp)
            .animateContentSize(),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = name,
                modifier = Modifier
                    .widthIn(max = maxWidth)
                    .alignBy(LastBaseline),
                color = MaterialTheme.colorScheme.onBackground,
                style = BoothTitle1,
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = warning,
                modifier = Modifier.alignBy(LastBaseline),
                style = BoothCaution,
                color = MaterialTheme.colorScheme.primary,
            )
        }
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
                    .clickable { onAction(BoothUiAction.OnScheduleToggleClick) },
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
                onAction(BoothUiAction.OnCheckLocationClick)
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
private fun BoothDescriptionNoSchedulePreview() {
    UnifestTheme {
        BoothDescription(
            name = "공대주점",
            warning = "누구나 환영",
            description = "컴퓨터 공학과와 물리학과가 함께하는 협동부스입니다. 방문자 이벤트로 무료 안주 하나씩 제공중이에요!!",
            location = "공학관",
            isScheduleExpanded = false,
            scheduleList = persistentListOf(),
            onAction = {},
        )
    }
}

@ComponentPreview
@Composable
private fun BoothDescriptionClosedPreview() {
    UnifestTheme {
        BoothDescription(
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
            onAction = {},
        )
    }
}

@ComponentPreview
@Composable
private fun BoothDescriptionOpenPreview() {
    UnifestTheme {
        BoothDescription(
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
            onAction = {},
        )
    }
}

@ComponentPreview
@Composable
private fun BoothDescriptionOpenDropdownExpandedPreview() {
    UnifestTheme {
        BoothDescription(
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
            onAction = {},
        )
    }
}
