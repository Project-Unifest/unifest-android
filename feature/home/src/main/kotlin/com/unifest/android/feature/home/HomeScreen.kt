package com.unifest.android.feature.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.component.UnifestOutlinedButton
import com.unifest.android.core.designsystem.theme.BoothLocation
import com.unifest.android.core.designsystem.theme.Content4
import com.unifest.android.core.designsystem.theme.Content5
import com.unifest.android.core.designsystem.theme.Content6
import com.unifest.android.core.designsystem.theme.Title2
import com.unifest.android.core.designsystem.theme.Title3
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.domain.entity.FestivalEventEntity
import com.unifest.android.core.domain.entity.IncomingFestivalEventEntity
import com.unifest.android.core.ui.DevicePreview
import com.unifest.android.feature.home.viewmodel.HomeUiState
import com.unifest.android.feature.home.viewmodel.HomeViewModel
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun HomeRoute(
    padding: PaddingValues,
    onNavigateToIntro: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(
        padding = padding,
        uiState = uiState,
        onNavigateToIntro = onNavigateToIntro,
    )
}

@Composable
internal fun HomeScreen(
    padding: PaddingValues,
    uiState: HomeUiState,
    @Suppress("unused")
    onNavigateToIntro: () -> Unit,
) {
    var selectedEventId by remember { mutableIntStateOf(-1) }
    val view = LocalView.current
    val insets = with(LocalDensity.current) {
        WindowInsetsCompat.toWindowInsetsCompat(view.rootWindowInsets, view).getInsets(WindowInsetsCompat.Type.statusBars()).top.toDp()
    }

    Column(modifier = Modifier.padding(top = insets)) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = padding.calculateBottomPadding()),
        ) {
            item { Calendar() }
            item {
                FestivalScheduleText()
            }
            if (uiState.festivalEvents.isEmpty()) {
                item {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(64.dp),
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_schedule),
                                contentDescription = "축제 없음",
                                modifier = Modifier.size(23.dp),
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = stringResource(id = R.string.home_empty_festival_text),
                                style = Title2,
                                textAlign = TextAlign.Center,
                            )
                            Spacer(modifier = Modifier.height(9.dp))
                            Text(
                                text = stringResource(id = R.string.home_empty_festival_schedule_description_text),
                                style = Content6,
                                textAlign = TextAlign.Center,
                                color = Color(0xFF848484),
                            )
                        }
                    }
                }
            } else {
                itemsIndexed(uiState.festivalEvents) { index, event ->
                    Column {
                        Spacer(modifier = Modifier.height(16.dp))
                        FestivalScheduleItem(event, selectedEventId) { eventId ->
                            selectedEventId = if (selectedEventId == eventId) -1 else eventId
                        }
                    }
                    if (index < uiState.festivalEvents.size - 1) {
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(
                            color = Color(0xFFDFDFDF),
                            modifier = Modifier.padding(horizontal = 20.dp),
                            thickness = 1.dp,
                        )
                    }
                }
            }
            item {
                UnifestOutlinedButton(
                    onClick = { onNavigateToIntro() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    contentColor = Color(0xFF585858),
                    borderColor = Color(0xFFD2D2D2),
                ) {
                    Text(
                        text = stringResource(id = R.string.home_add_interest_festival_button),
                        style = BoothLocation,
                    )
                }
            }
            item {
                IncomingFestivalText()
            }
            items(uiState.incomingEvents) { event ->
                IncomingFestivalCard(event)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun FestivalScheduleText() {
    Text(
        text = stringResource(id = R.string.home_festival_schedule_text),
        style = Title3,
        modifier = Modifier.padding(start = 20.dp, top = 20.dp),
    )
}

@Composable
fun FestivalScheduleItem(
    event: FestivalEventEntity,
    selectedEventId: Int,
    onEventClick: (Int) -> Unit,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onEventClick(event.id) }
                .padding(start = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .height(72.dp)
                    .background(Color(0xFF1FC0BA))
                    .align(Alignment.CenterVertically),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = event.date,
                    style = Content4,
                    color = Color(0xFFC0C0C0),
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = event.name,
                    style = Title2,
                )
                Spacer(modifier = Modifier.height(7.dp))
                Row {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_location_gray),
                        contentDescription = "Location Icon",
                        modifier = Modifier
                            .size(10.dp)
                            .align(Alignment.CenterVertically),
                        tint = Color.Unspecified,
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = event.location,
                        style = Content5,
                        color = Color(0xFF848484),
                    )
                }
            }
            Spacer(modifier = Modifier.width(39.dp))
            LazyRow {
                items(event.celebrityImages) { _ ->
                    Icon(
                        imageVector = Icons.Default.Circle,
                        contentDescription = "Celebrity",
                        tint = Color(0xFFDFDFDF),
                        modifier = Modifier
                            .size(60.dp)
                            .padding(horizontal = 5.dp),
                    )
                }
            }
        }
        AnimatedVisibility(visible = selectedEventId == event.id) {
            UnifestOutlinedButton(
                onClick = { /*관심 축제 추가하기 버튼*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 20.dp, end = 20.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.home_add_interest_festival_in_item_button),
                    style = BoothLocation,
                )
            }
        }
    }
}

@Composable
fun IncomingFestivalText() {
    Text(
        text = stringResource(id = R.string.home_incoming_festival_text),
        modifier = Modifier.padding(start = 20.dp, bottom = 16.dp),
        style = Title3,
    )
}

@Composable
fun IncomingFestivalCard(event: IncomingFestivalEventEntity) {
    Card(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFDEDEDE)),
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = event.imageRes),
                contentDescription = "${event.name} Logo",
                modifier = Modifier.size(52.dp),
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = event.dates,
                    style = Content6,
                    color = Color(0xFF848484),
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = event.name,
                    style = Content4,
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_location_gray),
                        contentDescription = "Location Icon",
                        modifier = Modifier
                            .size(10.dp)
                            .align(Alignment.CenterVertically),
                        tint = Color.Unspecified,
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = event.location,
                        style = Content6,
                        color = Color(0xFF848484),
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@DevicePreview
@Composable
fun HomeScreenPreview() {
    UnifestTheme {
        HomeScreen(
            uiState = HomeUiState(
                festivalEvents = persistentListOf(
                    FestivalEventEntity(
                        id = 1,
                        date = "5/21(화)",
                        name = "녹색지대 DAY 1",
                        location = "건국대학교 서울캠퍼스",
                        celebrityImages = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15),
                    ),
                    FestivalEventEntity(
                        id = 2,
                        date = "5/21(화)",
                        name = "녹색지대 DAY 1",
                        location = "건국대학교 서울캠퍼스",
                        celebrityImages = listOf(0, 1, 2),
                    ),
                    FestivalEventEntity(
                        id = 3,
                        date = "5/21(화)",
                        name = "녹색지대 DAY 1",
                        location = "건국대학교 서울캠퍼스",
                        celebrityImages = listOf(0, 1, 2),
                    ),
                ),
                incomingEvents = persistentListOf(
                    IncomingFestivalEventEntity(
                        imageRes = R.drawable.ic_waiting,
                        name = "녹색지대",
                        dates = "05/21(화) - 05/23(목)",
                        location = "건국대학교 서울캠퍼스",
                    ),
                    IncomingFestivalEventEntity(
                        imageRes = R.drawable.ic_waiting,
                        name = "녹색지대",
                        dates = "05/21(화) - 05/23(목)",
                        location = "건국대학교 서울캠퍼스",
                    ),
                ),
            ),

            padding = PaddingValues(0.dp),
            onNavigateToIntro = {},
        )
    }
}
