package com.unifest.android.feature.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
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
import com.unifest.android.core.common.utils.toLocalDate
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.theme.BoothTitle0
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.model.FestivalModel
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month

@Composable
fun Calendar(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    adjacentMonths: Long = 500,
    allFestivals: List<FestivalModel>,
) {
    val currentDate = remember { LocalDate.now() }
    val currentYearMonth = remember(currentDate) { currentDate.yearMonth }
    val startMonth = remember(currentDate) { currentYearMonth.minusMonths(adjacentMonths) }
    val endMonth = remember(currentDate) { currentYearMonth.plusMonths(adjacentMonths) }
    val daysOfWeek = remember { daysOfWeek() }
    var isWeekMode by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.shadow(4.dp, RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp)),
    ) {
        Column(
            modifier = Modifier
                .background(Color.White),
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

            CalendarHeader(daysOfWeek = daysOfWeek)
            AnimatedVisibility(visible = !isWeekMode) {
                HorizontalCalendar(
                    state = monthState,
                    dayContent = { day ->
                        val isSelectable = day.position == DayPosition.MonthDate
                        Day(
                            day.date,
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
                            day.date,
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
                onModeChange = { isWeekMode = it },
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
    val icon = if (isWeekMode) painterResource(id = R.drawable.ic_calender_down) else painterResource(id = R.drawable.ic_calender_up)
    val contentDescription = if (isWeekMode) "Month" else "Week"
    val backgroundPainter = painterResource(id = R.drawable.calender_bottom)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(40.dp)
            .paint(painter = backgroundPainter, contentScale = ContentScale.FillBounds)
            .then(modifier),
        contentAlignment = Alignment.Center,
    ) {
        IconButton(
            onClick = { onModeChange(!isWeekMode) },
        ) {
            Icon(
                painter = icon,
                contentDescription = contentDescription,
                tint = Color(0xFFD9D9D9),
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
    monthState: CalendarState,
    weekState: WeekCalendarState,
) {
    val visibleMonth = rememberFirstVisibleMonthAfterScroll(monthState)
    val currentMonth = visibleMonth.yearMonth.month

    val coroutineScope = rememberCoroutineScope()
    if (!isWeekMode) {
        SimpleCalendarTitle(
            modifier = Modifier.padding(20.dp),
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
}

@Composable
fun SimpleCalendarTitle(
    // 실제로 달력의 상단에 현재 월을 표시하고, 이전/다음 월로 이동할 수 있는 화살표 아이콘을 제공하는 UI 컴포넌트
    modifier: Modifier,
    currentMonth: Month,
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
            style = BoothTitle0,
            textAlign = TextAlign.Start,
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
    allFestivals: List<FestivalModel>,
) {
    val currentDate = LocalDate.now()
    val isToday = day == currentDate
    val showFestivalDot = allFestivals.any { festival ->
        val beginDate = festival.beginDate.toLocalDate()
        val endDate = festival.endDate.toLocalDate()
        !(day.isBefore(beginDate) || day.isAfter(endDate))
    }

    Column(
        modifier = Modifier.clickable(
            enabled = isSelectable,
            showRipple = !isSelected,
            onClick = { onClick(day) },
        ),
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1f) // This is important for square-sizing!
                .padding(10.dp)
                .clip(CircleShape)
                .background(color = if (isSelected) Color(0xFFF5687E) else Color.Transparent)
                .then(
                    if (day == currentDate) {
                        Modifier.border(2.dp, Color(0xFFF5687E), CircleShape)
                    } else Modifier,
                ),
            contentAlignment = Alignment.Center,
        ) {
            val textColor = when {
                isSelected -> Color.White
                isToday -> Color(0xFFF5687E)
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
        if (showFestivalDot) {
            Box(
                modifier = Modifier
                    .size(7.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF1FC0BA))
                    .align(Alignment.CenterHorizontally),
            )
        }
    }
}

@Preview
@Composable
private fun CalendarPreview() {
    UnifestTheme {
        Calendar(
            selectedDate = LocalDate.now(),
            onDateSelected = {},
            allFestivals = emptyList(),
        )
    }
}
