package com.unifest.android.feature.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarViewMonth
import androidx.compose.material.icons.filled.CalendarViewWeek
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.theme.UnifestTheme
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun Calendar(adjacentMonths: Long = 500) {
    val currentDate = remember { LocalDate.now() }
    val currentMonth = remember(currentDate) { currentDate.yearMonth }
    val startMonth = remember(currentDate) { currentMonth.minusMonths(adjacentMonths) }
    val endMonth = remember(currentDate) { currentMonth.plusMonths(adjacentMonths) }
    val selections = remember { mutableStateListOf<LocalDate>() }
    val daysOfWeek = remember { daysOfWeek() }

    var isWeekMode by remember { mutableStateOf(false) }
    var isAnimating by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        val monthState = rememberCalendarState(
            startMonth = startMonth,
            endMonth = endMonth,
            firstVisibleMonth = currentMonth,
            firstDayOfWeek = daysOfWeek.first(),
        )
        val weekState = rememberWeekCalendarState(
            startDate = startMonth.atStartOfMonth(),
            endDate = endMonth.atEndOfMonth(),
            firstVisibleWeekDate = currentDate,
            firstDayOfWeek = daysOfWeek.first(),
        )
        CalendarTitle(
            isWeekMode = isWeekMode,
            monthState = monthState,
            weekState = weekState,
            isAnimating = isAnimating,
        )
        //2024년 3월
        CalendarHeader(daysOfWeek = daysOfWeek)
        //월화수목금토일
        AnimatedVisibility(visible = !isWeekMode) {
            HorizontalCalendar(
                state = monthState,
                dayContent = { day ->
                    val isSelectable = day.position == DayPosition.MonthDate
                    Day(
                        day.date,
                        isSelected = isSelectable && selections.contains(day.date),
                        isSelectable = isSelectable,
                    ) { clicked ->
                        if (selections.contains(clicked)) {
                            selections.remove(clicked)
                        } else {
                            selections.add(clicked)
                        }
                    }
                },
            )
        }
        AnimatedVisibility(visible = isWeekMode) {
            WeekCalendar(
                state = weekState,
                dayContent = { day ->
                    val isSelectable = day.position == WeekDayPosition.RangeDate
                    Day(
                        day.date,
                        isSelected = isSelectable && selections.contains(day.date),
                        isSelectable = isSelectable,
                    ) { clicked ->
                        if (selections.contains(clicked)) {
                            selections.remove(clicked)
                        } else {
                            selections.add(clicked)
                        }
                    }
                },
            )
        }

        ModeToggleButton(
            isWeekMode = isWeekMode,
            onModeChange = { isWeekMode = it },
        )
    }
}

@Composable
fun ModeToggleButton(
    modifier: Modifier = Modifier,
    isWeekMode: Boolean,
    onModeChange: (Boolean) -> Unit,
) {
    val icon = if (isWeekMode) painterResource(id = R.drawable.ic_calender_down) else painterResource(id = R.drawable.ic_calender_up)
    val contentDescription = if (isWeekMode) "Month" else "Week"


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        contentAlignment = Alignment.Center,
    ) {
        IconButton(
            onClick = { onModeChange(!isWeekMode) },
        ) {
            Icon(
                painter = icon,
                contentDescription = contentDescription,
                tint = Color.Gray
            )
        }
    }
}

@Composable
private fun CalendarNavigationIcon(
    icon: Painter,
    contentDescription: String,
    onClick: () -> Unit,
) = Box(
    modifier = Modifier
        .fillMaxHeight()
        .aspectRatio(1f)
        .clip(shape = CircleShape)
        .clickable(role = Role.Button, onClick = onClick),
) {
    Icon(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .align(Alignment.Center),
        painter = icon,
        contentDescription = contentDescription,
        tint = Color.Gray,

        )
}


@Composable
fun MonthAndWeekCalendarTitle(
    isWeekMode: Boolean,
    currentMonth: YearMonth,
    monthState: CalendarState,
    weekState: WeekCalendarState,
) {
    val coroutineScope = rememberCoroutineScope()
    SimpleCalendarTitle(
        modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp),
        currentMonth = currentMonth,
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

@Composable
fun SimpleCalendarTitle(
    modifier: Modifier,
    currentMonth: YearMonth,
    goToPrevious: () -> Unit,
    goToNext: () -> Unit,
) {
    Row(
        modifier = modifier.height(40.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Text(
            modifier = Modifier.weight(1f),
            text = currentMonth.displayText(),
            fontSize = 14.sp,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,

            )
        CalendarNavigationIcon(
            icon = painterResource(id = R.drawable.ic_chevron_left),
            contentDescription = "Previous",
            onClick = goToPrevious,
        )

        CalendarNavigationIcon(
            icon = painterResource(id = R.drawable.ic_chevron_right),
            contentDescription = "Next",
            onClick = goToNext,
        )
    }
}


@Composable
private fun CalendarTitle(
    isWeekMode: Boolean,
    monthState: CalendarState,
    weekState: WeekCalendarState,
    isAnimating: Boolean,
) {
    val visibleMonth = rememberFirstVisibleMonthAfterScroll(monthState)
    val visibleWeek = rememberFirstVisibleWeekAfterScroll(weekState)
    val visibleWeekMonth = visibleWeek.days.first().date.yearMonth
    val currentMonth = if (isWeekMode) {
        if (isAnimating) visibleMonth.yearMonth else visibleWeekMonth
    } else {
        if (isAnimating) visibleWeekMonth else visibleMonth.yearMonth
    }
    MonthAndWeekCalendarTitle(
        isWeekMode = isWeekMode,
        currentMonth = currentMonth,
        monthState = monthState,
        weekState = weekState,
    )
}

@Composable
fun CalendarHeader(daysOfWeek: List<DayOfWeek>) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                text = dayOfWeek.displayText(),
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
            )
        }
    }
}

@Composable
fun Day(
    day: LocalDate,
    isSelected: Boolean,
    isSelectable: Boolean,
    onClick: (LocalDate) -> Unit,
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f) // This is important for square-sizing!
            .padding(6.dp)
            .clip(CircleShape)
            .background(color = if (isSelected) Color(0xFFF5687E) else Color.Transparent)
            .clickable(
                enabled = isSelectable,
                showRipple = !isSelected,
                onClick = { onClick(day) },
            ),
        contentAlignment = Alignment.Center,
    ) {
        val textColor = when {
            isSelected -> Color.White
            isSelectable -> Color.Unspecified
            else -> colorResource(R.color.inactive_text_color)
        }
        Text(
            text = day.dayOfMonth.toString(),
            color = textColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}


@Preview
@Composable
private fun CalendarPreview() {
    UnifestTheme {
        Calendar()

    }
}

