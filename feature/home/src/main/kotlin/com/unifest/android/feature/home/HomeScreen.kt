package com.unifest.android.feature.home

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unifest.android.core.common.ObserveAsEvents
import com.unifest.android.core.common.UiText
import com.unifest.android.core.common.utils.formatWithDayOfWeek
import com.unifest.android.core.common.utils.toLocalDate
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.component.NetworkImage
import com.unifest.android.core.designsystem.component.UnifestHorizontalDivider
import com.unifest.android.core.designsystem.component.UnifestOutlinedButton
import com.unifest.android.core.designsystem.theme.BoothLocation
import com.unifest.android.core.designsystem.theme.Content4
import com.unifest.android.core.designsystem.theme.Content5
import com.unifest.android.core.designsystem.theme.Content6
import com.unifest.android.core.designsystem.theme.Title2
import com.unifest.android.core.designsystem.theme.Title3
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.core.model.FestivalTodayModel
import com.unifest.android.core.ui.DevicePreview
import com.unifest.android.core.ui.component.StarImage
import com.unifest.android.feature.festival.FestivalSearchBottomSheet
import com.unifest.android.feature.festival.viewmodel.FestivalUiAction
import com.unifest.android.feature.festival.viewmodel.FestivalUiEvent
import com.unifest.android.feature.festival.viewmodel.FestivalUiState
import com.unifest.android.feature.festival.viewmodel.FestivalViewModel
import com.unifest.android.feature.home.viewmodel.HomeUiAction
import com.unifest.android.feature.home.viewmodel.HomeUiEvent
import com.unifest.android.feature.home.viewmodel.HomeUiState
import com.unifest.android.feature.home.viewmodel.HomeViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Composable
internal fun HomeRoute(
    padding: PaddingValues,
    popBackStack: () -> Unit,
    onShowSnackBar: (message: UiText) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
    festivalViewModel: FestivalViewModel = hiltViewModel(),
) {
    val homeUiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val festivalUiState by festivalViewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    ObserveAsEvents(flow = homeViewModel.uiEvent) { event ->
        when (event) {
            is HomeUiEvent.NavigateBack -> popBackStack()
            is HomeUiEvent.ShowSnackBar -> onShowSnackBar(event.message)
            is HomeUiEvent.ShowToast -> Toast.makeText(context, event.message.asString(context), Toast.LENGTH_SHORT).show()
        }
    }

    ObserveAsEvents(flow = festivalViewModel.uiEvent) { event ->
        when (event) {
            is FestivalUiEvent.ShowSnackBar -> onShowSnackBar(event.message)
            is FestivalUiEvent.ShowToast -> Toast.makeText(context, event.message.asString(context), Toast.LENGTH_SHORT).show()
        }
    }

    HomeScreen(
        padding = padding,
        homeUiState = homeUiState,
        festivalUiState = festivalUiState,
        onHomeUiAction = homeViewModel::onHomeUiAction,
        onFestivalUiAction = festivalViewModel::onFestivalUiAction,
    )
}

@Composable
internal fun HomeScreen(
    padding: PaddingValues,
    homeUiState: HomeUiState,
    festivalUiState: FestivalUiState,
    onHomeUiAction: (HomeUiAction) -> Unit,
    onFestivalUiAction: (FestivalUiAction) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
    ) {
        LazyColumn {
            item {
                Calendar(
                    selectedDate = homeUiState.selectedDate,
                    onDateSelected = { date -> onHomeUiAction(HomeUiAction.OnDateSelected(date)) },
                    allFestivals = homeUiState.allFestivals,
                    isWeekMode = homeUiState.isWeekMode,
                    ocClickWeekMode = { onHomeUiAction(HomeUiAction.OnClickWeekMode) },
                )
            }
            item {
                FestivalScheduleText(selectedDate = homeUiState.selectedDate)
            }
            if (homeUiState.todayFestivals.isEmpty()) {
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
                itemsIndexed(
                    items = homeUiState.todayFestivals,
                    key = { _, festival -> festival.festivalId },
                ) { scheduleIndex, festival ->
                    Column {
                        Spacer(modifier = Modifier.height(16.dp))
                        FestivalScheduleItem(
                            festival = festival,
                            scheduleIndex = scheduleIndex,
                            likedFestivals = homeUiState.likedFestivals,
                            selectedDate = homeUiState.selectedDate,
                            isDataReady = homeUiState.isDataReady,
                            isStarImageClicked = homeUiState.isStarImageClicked[scheduleIndex],
                            onHomeUiAction = onHomeUiAction,
                        )
                    }
                    if (scheduleIndex < homeUiState.todayFestivals.size - 1) {
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(
                            color = Color(0xFFDFDFDF),
                            modifier = Modifier.padding(horizontal = 20.dp),
                        )
                    }
                }
            }
            item {
                UnifestOutlinedButton(
                    onClick = { onFestivalUiAction(FestivalUiAction.OnAddLikedFestivalClick) },
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
            item { UnifestHorizontalDivider() }
            item { Spacer(modifier = Modifier.height(20.dp)) }
            item { IncomingFestivalText() }
            if (homeUiState.incomingFestivals.isNotEmpty()) {
                items(homeUiState.incomingFestivals) { festival ->
                    IncomingFestivalCard(festival)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        if (festivalUiState.isFestivalSearchBottomSheetVisible) {
            FestivalSearchBottomSheet(
                searchText = festivalUiState.festivalSearchText,
                searchTextHintRes = R.string.festival_search_text_field_hint,
                likedFestivals = festivalUiState.likedFestivals,
                festivalSearchResults = festivalUiState.festivalSearchResults,
                isSearchMode = festivalUiState.isSearchMode,
                isEditMode = festivalUiState.isEditMode,
                isLikedFestivalDeleteDialogVisible = festivalUiState.isLikedFestivalDeleteDialogVisible,
                onFestivalUiAction = onFestivalUiAction,
            )
        }
    }
}

@Composable
fun FestivalScheduleText(selectedDate: LocalDate) {
    val formattedDate = DateTimeFormatter.ofPattern("M월 d일").format(selectedDate)
    Text(
        text = formattedDate + stringResource(id = R.string.home_festival_schedule_text),
        style = Title3,
        modifier = Modifier.padding(start = 20.dp, top = 20.dp),
    )
}

@Composable
fun FestivalScheduleItem(
    festival: FestivalTodayModel,
    scheduleIndex: Int,
    likedFestivals: ImmutableList<FestivalModel>,
    selectedDate: LocalDate,
    isStarImageClicked: ImmutableList<Boolean>,
    isDataReady: Boolean,
    onHomeUiAction: (HomeUiAction) -> Unit,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
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
            Column(
                modifier = Modifier.width(172.dp),
            ) {
                Text(
                    text = "${festival.beginDate.toLocalDate().formatWithDayOfWeek()} - ${festival.endDate.toLocalDate().formatWithDayOfWeek()}",
                    style = Content4,
                    color = Color(0xFFC0C0C0),
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row {
                    Text(
                        text = festival.festivalName + " Day ",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = Title2,
                    )
                    if (isDataReady) {
                        Text(
                            text = (ChronoUnit.DAYS.between(festival.beginDate.toLocalDate(), selectedDate) + 1).toString(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = Title2,
                        )
                    }
                }
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
                        text = festival.schoolName,
                        style = Content5,
                        color = Color(0xFF848484),
                    )
                }
            }
            if (festival.starInfo.isNotEmpty()) {
                LazyRow {
                    itemsIndexed(
                        items = festival.starInfo,
                        key = { _, starInfo -> starInfo.starId },
                    ) { starIndex, starInfo ->
                        StarImage(
                            imgUrl = starInfo.imgUrl,
                            onClick = {
                                onHomeUiAction(HomeUiAction.OnToggleStarImageClick(scheduleIndex, starIndex, !isStarImageClicked[starIndex]))
                            },
                            isClicked = isStarImageClicked[starIndex],
                            label = starInfo.name,
                            modifier = Modifier
                                .size(72.dp)
                                .clip(CircleShape),
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
            }
        }
        if (!likedFestivals.any { it.festivalId == festival.festivalId }) {
            UnifestOutlinedButton(
                onClick = {
                    onHomeUiAction(HomeUiAction.OnAddAsLikedFestivalClick(festival))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(top = 16.dp, start = 20.dp, end = 20.dp),
                contentPadding = PaddingValues(6.dp),
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
fun IncomingFestivalCard(festival: FestivalModel) {
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
            NetworkImage(
                imgUrl = festival.thumbnail,
                contentDescription = "Festival Thumbnail",
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape),
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "${festival.beginDate.toLocalDate().formatWithDayOfWeek()} - ${festival.endDate.toLocalDate().formatWithDayOfWeek()}",
                    style = Content6,
                    color = Color(0xFF848484),
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = festival.festivalName,
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
                        text = festival.schoolName,
                        style = Content6,
                        color = Color(0xFF848484),
                    )
                }
            }
        }
    }
}

@DevicePreview
@Composable
fun HomeScreenPreview() {
    UnifestTheme {
        HomeScreen(
            padding = PaddingValues(),
            homeUiState = HomeUiState(
                todayFestivals = persistentListOf(
                    FestivalTodayModel(
                        festivalId = 1,
                        beginDate = "2024-04-05",
                        endDate = "2024-04-07",
                        festivalName = "녹색지대 DAY 1",
                        schoolName = "건국대학교 서울캠퍼스",
                        starInfo = listOf(),
                        thumbnail = "https://picsum.photos/36",
                        schoolId = 1,
                    ),

                    FestivalTodayModel(
                        festivalId = 2,
                        beginDate = "2024-04-05",
                        endDate = "2024-04-07",
                        festivalName = "녹색지대 DAY 1",
                        schoolName = "건국대학교 서울캠퍼스",
                        starInfo = listOf(),
                        thumbnail = "https://picsum.photos/36",
                        schoolId = 2,
                    ),
                    FestivalTodayModel(
                        festivalId = 3,
                        beginDate = "2024-04-05",
                        endDate = "2024-04-07",
                        festivalName = "녹색지대 DAY 1",
                        schoolName = "건국대학교 서울캠퍼스",
                        starInfo = listOf(),
                        thumbnail = "https://picsum.photos/36",
                        schoolId = 3,
                    ),
                ),
                incomingFestivals = persistentListOf(
                    FestivalModel(
                        1,
                        1,
                        "https://picsum.photos/36",
                        "서울대학교",
                        "서울",
                        "설대축제",
                        "2024-04-21",
                        "2024-04-23",
                        126.957f,
                        37.460f,
                    ),
                    FestivalModel(
                        2,
                        2,
                        "https://picsum.photos/36",
                        "연세대학교",
                        "서울",
                        "연대축제",
                        "2024-04-21",
                        "2024-04-23",
                        126.957f,
                        37.460f,
                    ),
                ),
            ),
            festivalUiState = FestivalUiState(),
            onHomeUiAction = {},
            onFestivalUiAction = {},
        )
    }
}
