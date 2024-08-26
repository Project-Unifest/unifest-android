package com.unifest.android.feature.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.WeekCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.WeekDayPosition
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import com.kizitonwose.calendar.core.yearMonth
import com.unifest.android.core.common.utils.toLocalDate
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.theme.BoothTitle0
import com.unifest.android.core.designsystem.theme.Content6
import com.unifest.android.core.designsystem.theme.DarkBlueGreen
import com.unifest.android.core.designsystem.theme.DarkOrange
import com.unifest.android.core.designsystem.theme.DarkRed
import com.unifest.android.core.designsystem.theme.LightBlueGreen
import com.unifest.android.core.designsystem.theme.LightOrange
import com.unifest.android.core.designsystem.theme.LightRed
import com.unifest.android.core.designsystem.theme.Title5
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.model.FestivalModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month

@Composable
fun Calendar(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    adjacentMonths: Long = 500,
    allFestivals: ImmutableList<FestivalModel>,
    isWeekMode: Boolean = false,
    ocClickWeekMode: () -> Unit,
) {
    val currentDate = remember { LocalDate.now() }
    val currentYearMonth = remember(currentDate) { currentDate.yearMonth }
    val startMonth = remember(currentDate) { currentYearMonth.minusMonths(adjacentMonths) }
    val endMonth = remember(currentDate) { currentYearMonth.plusMonths(adjacentMonths) }
    val daysOfWeek = remember { daysOfWeek() }
    Box(
        modifier = Modifier.shadow(4.dp, RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp)),
    ) {
        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
        ) {
            val monthState = rememberCalendarState(
                startMonth = startMonth,
                endMonth = endMonth,
                firstVisibleMonth = currentYearMonth,
                firstDayOfWeek = daysOfWeek.first(),
            )
            val weekState = rememberWeekCalendarState(
                startDate = startMonth.atStartOfMonth(),
                endDate = endMonth.atEndOfMonth(),
                firstVisibleWeekDate = currentDate,
                firstDayOfWeek = daysOfWeek.first(),
            )

            MonthAndWeekCalendarTitle(
                isWeekMode = isWeekMode,
                monthState = monthState,
                weekState = weekState,
            )

            CalendarHeader(daysOfWeek = daysOfWeek.toImmutableList())
            AnimatedVisibility(visible = !isWeekMode) {
                HorizontalCalendar(
                    state = monthState,
                    dayContent = { day ->
                        val isSelectable = day.position == DayPosition.MonthDate
                        Day(
                            day = day.date,
                            isSelected = isSelectable && selectedDate == day.date,
                            isSelectable = isSelectable,
                            onClick = { newSelectedDate ->
                                onDateSelected(newSelectedDate)
                            },
                            allFestivals = allFestivals,
                        )
                    },
                )
            }
            AnimatedVisibility(visible = isWeekMode) {
                WeekCalendar(
                    state = weekState,
                    dayContent = { day ->
                        val isSelectable = day.position == WeekDayPosition.RangeDate
                        Day(
                            day = day.date,
                            isSelected = isSelectable && selectedDate == day.date,
                            isSelectable = isSelectable,
                            onClick = { newSelectedDate ->
                                onDateSelected(newSelectedDate)
                            },
                            allFestivals = allFestivals,
                        )
                    },
                )
            }

            ModeToggleButton(
                isWeekMode = isWeekMode,
                onModeChange = { ocClickWeekMode() },
            )
        }
    }
}

@Composable
fun ModeToggleButton(
    modifier: Modifier = Modifier,
    isWeekMode: Boolean,
    onModeChange: (Boolean) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(40.dp)
            .paint(
                painter = painterResource(id = R.drawable.calender_bottom),
                contentScale = ContentScale.FillBounds,
            )
            .then(modifier),
        contentAlignment = Alignment.Center,
    ) {
        IconButton(
            onClick = { onModeChange(!isWeekMode) },
        ) {
            Icon(
                imageVector = if (isWeekMode) {
                    ImageVector.vectorResource(id = R.drawable.ic_calender_down)
                } else {
                    ImageVector.vectorResource(id = R.drawable.ic_calender_up)
                },
                contentDescription = if (isWeekMode) "Month" else "Week",
                tint = MaterialTheme.colorScheme.onSecondaryContainer,
            )
        }
    }
}

@Composable
private fun CalendarNavigationIcon(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
) = Box(
    modifier = Modifier
        .size(20.dp)
        .clip(CircleShape)
        .clickable(
            role = Role.Button,
            onClick = onClick,
        ),
) {
    Icon(
        modifier = Modifier
            .height(15.dp)
            .width(8.dp)
            .align(Alignment.Center),
        imageVector = icon,
        contentDescription = contentDescription,
        tint = MaterialTheme.colorScheme.onSecondary,
    )
}

@Composable
fun MonthAndWeekCalendarTitle(
    isWeekMode: Boolean,
    monthState: CalendarState,
    weekState: WeekCalendarState,
) {
    val visibleMonth = rememberFirstVisibleMonthAfterScroll(monthState)
    val currentMonth = visibleMonth.yearMonth.month
    val currentYear = visibleMonth.yearMonth.year

    val coroutineScope = rememberCoroutineScope()
    if (!isWeekMode) {
        SimpleCalendarTitle(
            modifier = Modifier.padding(start = 18.dp, end = 7.dp, top = 20.dp, bottom = 20.dp),
            currentMonth = currentMonth,
            currentYear = currentYear,
            goToPrevious = {
                coroutineScope.launch {
                    if (isWeekMode) {
                        val targetDate = weekState.firstVisibleWeek.days.first().date.minusDays(1)
                        weekState.animateScrollToWeek(targetDate)
                    } else {
                        val targetMonth = monthState.firstVisibleMonth.yearMonth.previousMonth
                        monthState.animateScrollToMonth(targetMonth)
                    }
                }
            },
            goToNext = {
                coroutineScope.launch {
                    if (isWeekMode) {
                        val targetDate = weekState.firstVisibleWeek.days.last().date.plusDays(1)
                        weekState.animateScrollToWeek(targetDate)
                    } else {
                        val targetMonth = monthState.firstVisibleMonth.yearMonth.nextMonth
                        monthState.animateScrollToMonth(targetMonth)
                    }
                }
            },
        )
    }
}

// 실제로 달력의 상단에 현재 월을 표시하고, 이전/다음 월로 이동할 수 있는 화살표 아이콘을 제공하는 UI 컴포넌트
@Composable
fun SimpleCalendarTitle(
    currentMonth: Month,
    currentYear: Int,
    goToPrevious: () -> Unit,
    goToNext: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .height(40.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "${currentYear}년 ${currentMonth.displayText()}",
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Start,
                style = BoothTitle0,
            )
            Spacer(modifier = Modifier.width(6.dp))
            ColorCircleWithText(color = Color(0xFF1FC0BA), text = "1개")
            Spacer(modifier = Modifier.width(4.dp))
            ColorCircleWithText(color = Color(0xFFFF8A1F), text = "2개")
            Spacer(modifier = Modifier.width(4.dp))
            ColorCircleWithText(color = Color(0xFFFF3939), text = "3개 이상")
        }
        CalendarNavigationIcon(
            icon = ImageVector.vectorResource(id = R.drawable.ic_calender_left),
            contentDescription = "Previous",
            onClick = goToPrevious,
        )
        Spacer(modifier = Modifier.width(10.dp))
        CalendarNavigationIcon(
            icon = ImageVector.vectorResource(id = R.drawable.ic_calender_right),
            contentDescription = "Next",
            onClick = goToNext,
        )
    }
}

@Composable
fun ColorCircleWithText(color: Color, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 4.dp),
    ) {
        Box(
            modifier = Modifier
                .size(7.dp)
                .clip(CircleShape)
                .background(color),
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = Content6,
        )
    }
}

@Composable
fun CalendarHeader(daysOfWeek: ImmutableList<DayOfWeek>) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                text = dayOfWeek.displayText(),
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                style = Content6,
            )
        }
    }
}

@Composable
fun Day(
    day: LocalDate,
    isSelected: Boolean,
    isSelectable: Boolean,
    allFestivals: ImmutableList<FestivalModel>,
    onClick: (LocalDate) -> Unit,
) {
    val currentDate = LocalDate.now()
    val isToday = day == currentDate
    val festivalCount = allFestivals.count { festival ->
        val beginDate = festival.beginDate.toLocalDate()
        val endDate = festival.endDate.toLocalDate()
        !(day.isBefore(beginDate) || day.isAfter(endDate))
    }

    Box(
        modifier = Modifier
            .clickable(
                enabled = isSelectable,
                showRipple = false,
                onClick = { onClick(day) },
            ),
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .padding(16.dp)
                .clip(CircleShape)
                .background(color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent)
                .then(
                    if (day == currentDate) {
                        Modifier.border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    } else Modifier,
                ),
            contentAlignment = Alignment.Center,
        ) {
            val textColor = when {
                isSelected -> Color.White
                isToday -> MaterialTheme.colorScheme.primary
                isSelectable -> MaterialTheme.colorScheme.onBackground
                else -> MaterialTheme.colorScheme.onSecondaryContainer
            }
            Text(
                text = day.dayOfMonth.toString(),
                color = textColor,
                style = Title5,
            )
        }
        if (festivalCount > 0) {
            val festivalDotColor = when (festivalCount) {
                1 -> if (isSystemInDarkTheme()) DarkBlueGreen else LightBlueGreen
                2 -> if (isSystemInDarkTheme()) DarkOrange else LightOrange
                else -> if (isSystemInDarkTheme()) DarkRed else LightRed
            }
            Box(
                modifier = Modifier
                    .size(9.dp)
                    .clip(CircleShape)
                    .background(festivalDotColor)
                    .align(Alignment.BottomCenter),
            )
        }
    }
}

@ComponentPreview
@Composable
fun CalendarPreview() {
    UnifestTheme {
        Calendar(
            selectedDate = LocalDate.now(),
            onDateSelected = {},
            allFestivals = persistentListOf(),
            isWeekMode = false,
            ocClickWeekMode = {},
        )
    }
}
