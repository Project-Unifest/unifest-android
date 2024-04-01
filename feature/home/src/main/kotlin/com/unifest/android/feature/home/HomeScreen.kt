package com.unifest.android.feature.home

import androidx.compose.ui.unit.sp


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsCompat
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.ui.DevicePreview

@Composable
internal fun HomeRoute(
    padding: PaddingValues,
    onNavigateToIntro: () -> Unit,
) {
    HomeScreen(
        padding = padding,
        onNavigateToIntro = onNavigateToIntro,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    padding: PaddingValues,
    @Suppress("unused")
    onNavigateToIntro: () -> Unit,
) {
    var selectedEventId by remember { mutableStateOf(-1) }
    val view = LocalView.current
    val insets = with(LocalDensity.current) {
        WindowInsetsCompat.toWindowInsetsCompat(view.rootWindowInsets, view).getInsets(WindowInsetsCompat.Type.statusBars()).top.toDp()
    }
    Column(modifier = Modifier.padding(top = insets)) {
        Calendar()
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = padding.calculateBottomPadding()),
        ) {
            item { FestivalScheduleText() }
            items(festivalEvents) { event ->
                EventItem(event, selectedEventId) { eventId ->
                    selectedEventId = if (selectedEventId == eventId) -1 else eventId
                }
            }
            item { IncomingScheduleText() }
            item { FestivalEventCard() }
        }
    }
}

@DevicePreview
@Composable
fun HomeScreenPreview() {
    UnifestTheme {
        HomeScreen(
            padding = PaddingValues(0.dp),
            onNavigateToIntro = {},
        )
    }
}


data class FestivalEvent(
    val id: Int,
    val date: String,
    val name: String,
    val location: String,
    val celebrityImages: List<Int>,
)

val festivalEvents = listOf(
    FestivalEvent(
        id = 1,
        date = "5/21(화)",
        name = "녹색지대 DAY 1",
        location = "건국대학교 서울캠퍼스",
        celebrityImages = listOf(0, 1, 2),
    ),
    FestivalEvent(
        id = 2,
        date = "5/21(화)",
        name = "녹색지대 DAY 1",
        location = "건국대학교 서울캠퍼스",
        celebrityImages = listOf(0, 1, 2),
    ),
    FestivalEvent(
        id = 3,
        date = "5/21(화)",
        name = "녹색지대 DAY 1",
        location = "건국대학교 서울캠퍼스",
        celebrityImages = listOf(0, 1, 2),
    ),

    )

@Composable
fun FestivalScheduleText() {
    Text(
        text = "오늘의 축제 일정",
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp),
    )
}

@Composable
fun IncomingScheduleText() {
    Text(
        text = "다가오는 축제 일정",
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp),
    )
}

@Composable
fun EventItem(
    event: FestivalEvent,
    selectedEventId: Int,
    onEventClick: (Int) -> Unit,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onEventClick(event.id) },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
            ) {
                Text(text = event.date, fontSize = 20.sp)
                Text(text = event.name, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Text(text = event.location, color = Color.Gray, fontSize = 16.sp)
            }
            LazyRow {
                items(event.celebrityImages) { _ ->
                    Icon(
                        imageVector = Icons.Default.Circle,
                        contentDescription = "Celebrity",
                        tint = Color.Gray,
                        modifier = Modifier
                            .size(52.dp)
                            .padding(horizontal = 4.dp),
                    )
                }
            }
        }
        AnimatedVisibility(visible = selectedEventId == event.id) {
            Button(
                onClick = { /* 관심 축제 추가 */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                Text("관심 축제로 추가")
            }
        }
    }
}

@Composable
fun FestivalEventCard() {
    val imageResource: Painter = painterResource(id = com.unifest.android.core.designsystem.R.drawable.ic_waiting) // 실제 리소스 ID로 변경 필요
    val eventName = "녹색지대"
    val eventDates = "05/21(화) - 05/23(목)"
    val eventLocation = "건국대학교 서울캠퍼스"
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color.Gray),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = imageResource,
                contentDescription = "Event Logo",
                modifier = Modifier.size(48.dp),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = eventDates)
                Text(text = eventName)
                Text(text = eventLocation)
            }
        }
    }
}
