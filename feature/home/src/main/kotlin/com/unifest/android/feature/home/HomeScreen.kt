package com.unifest.android.feature.home

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.component.NetworkImage
import com.unifest.android.core.designsystem.component.UnifestOutlinedButton
import com.unifest.android.core.designsystem.theme.BoothLocation
import com.unifest.android.core.designsystem.theme.Content4
import com.unifest.android.core.designsystem.theme.Content5
import com.unifest.android.core.designsystem.theme.Content6
import com.unifest.android.core.designsystem.theme.Title2
import com.unifest.android.core.designsystem.theme.Title3
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.model.FestivalTodayModel
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.core.ui.DevicePreview
import com.unifest.android.core.ui.component.FestivalSearchBottomSheet
import com.unifest.android.feature.home.viewmodel.HomeUiState
import com.unifest.android.feature.home.viewmodel.HomeViewModel
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
internal fun HomeRoute(
    padding: PaddingValues,
    onShowSnackBar: (message: Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(
        padding = padding,
        uiState = uiState,
        onShowSnackBar = onShowSnackBar,
        setFestivalSearchBottomSheetVisible = viewModel::setFestivalSearchBottomSheetVisible,
        updateFestivalSearchText = viewModel::updateFestivalSearchText,
        initSearchText = viewModel::initSearchText,
        setEnableSearchMode = viewModel::setEnableSearchMode,
        setEnableEditMode = viewModel::setEnableEditMode,
        setLikedFestivalDeleteDialogVisible = viewModel::setLikedFestivalDeleteDialogVisible,
        setSelectedDate = viewModel::setSelectedDate,
        onAddLikeFestivalClick = viewModel::addLikeFestival,
        onAddLikeFestivalAtBottomSheetSearch = viewModel::addLikeFestivalAtBottomSheetSearch,
    )
}

@Composable
internal fun HomeScreen(
    padding: PaddingValues,
    uiState: HomeUiState,
    @Suppress("unused")
    setFestivalSearchBottomSheetVisible: (Boolean) -> Unit,
    updateFestivalSearchText: (TextFieldValue) -> Unit,
    initSearchText: () -> Unit,
    setEnableSearchMode: (Boolean) -> Unit,
    setEnableEditMode: () -> Unit,
    setLikedFestivalDeleteDialogVisible: (Boolean) -> Unit,
    onShowSnackBar: (message: Int) -> Unit,
    setSelectedDate: (LocalDate) -> Unit,
    onAddLikeFestivalClick: (FestivalTodayModel) -> Unit,
    onAddLikeFestivalAtBottomSheetSearch: (FestivalModel) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
    ) {
        LazyColumn {
            item {
                Calendar(
                    selectedDate = uiState.selectedDate,
                    onDateSelected = setSelectedDate,
                )
            }
            item {
                FestivalScheduleText(selectedDate = uiState.selectedDate)
            }
            if (uiState.todayFestivals.isEmpty()) {
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
                    items = uiState.todayFestivals,
                    key = { _, festival -> festival.festivalId },
                ) { index, festival ->
                    Column {
                        Spacer(modifier = Modifier.height(16.dp))
                        FestivalScheduleItem(
                            festival, onShowSnackBar,
                            onAddLikeFestivalClick = {
                                onAddLikeFestivalClick(festival)
                            },
                        )
                    }
                    if (index < uiState.todayFestivals.size - 1) {
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
                    onClick = { setFestivalSearchBottomSheetVisible(true) },
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
                VerticalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .background(Color(0xFFF1F3F7)),
                )
            }
            item { Spacer(modifier = Modifier.height(20.dp)) }
            item {
                IncomingFestivalText()
            }
            items(uiState.incomingFestivals) { festival ->
                IncomingFestivalCard(festival)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        if (uiState.isFestivalSearchBottomSheetVisible) {
            FestivalSearchBottomSheet(
                searchText = uiState.festivalSearchText,
                updateSearchText = updateFestivalSearchText,
                searchTextHintRes = R.string.festival_search_text_field_hint,
                setFestivalSearchBottomSheetVisible = setFestivalSearchBottomSheetVisible,
                likedFestivals = uiState.likedFestivals,
                festivalSearchResults = uiState.festivalSearchResults,
                initSearchText = initSearchText,
                setEnableSearchMode = setEnableSearchMode,
                isSearchMode = uiState.isSearchMode,
                setEnableEditMode = setEnableEditMode,
                isLikedFestivalDeleteDialogVisible = uiState.isLikedFestivalDeleteDialogVisible,
                setLikedFestivalDeleteDialogVisible = setLikedFestivalDeleteDialogVisible,
                isEditMode = uiState.isEditMode,
                addLikeFestivalAtBottomSheetSearch = onAddLikeFestivalAtBottomSheetSearch,
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
    onShowSnackBar: (message: Int) -> Unit,
    onAddLikeFestivalClick: (FestivalTodayModel) -> Unit,
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
            Column {
                Text(
                    text = festival.date,
                    style = Content4,
                    color = Color(0xFFC0C0C0),
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = festival.festivalName,
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
                        text = festival.schoolName,
                        style = Content5,
                        color = Color(0xFF848484),
                    )
                }
            }
            Spacer(modifier = Modifier.width(39.dp))
            LazyRow {
                items(festival.starInfo) { _ ->
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
        UnifestOutlinedButton(
            onClick = {
                onAddLikeFestivalClick(festival)
                onShowSnackBar(R.string.home_add_interest_festival_snack_bar)
            },
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
                imageUrl = festival.thumbnail,
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape),
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = festival.beginDate + " - " + festival.endDate,
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

@OptIn(ExperimentalFoundationApi::class)
@DevicePreview
@Composable
fun HomeScreenPreview() {
    UnifestTheme {
        HomeScreen(
            padding = PaddingValues(0.dp),
            uiState = HomeUiState(
                todayFestivals = persistentListOf(
                    FestivalTodayModel(
                        festivalId = 1,
                        date = "5/21(화)",
                        festivalName = "녹색지대 DAY 1",
                        schoolName = "건국대학교 서울캠퍼스",
                        starInfo = listOf(),
                        thumbnail = "https://picsum.photos/36",
                        schoolId = 1,
                    ),

                    FestivalTodayModel(
                        festivalId = 2,
                        date = "5/21(화)",
                        festivalName = "녹색지대 DAY 1",
                        schoolName = "건국대학교 서울캠퍼스",
                        starInfo = listOf(),
                        thumbnail = "https://picsum.photos/36",
                        schoolId = 2,
                    ),
                    FestivalTodayModel(
                        festivalId = 3,
                        date = "5/21(화)",
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
                        "설대축제",
                        "05.06",
                        "05.08",
                        126.957f,
                        37.460f,
                    ),
                    FestivalModel(
                        2,
                        2,
                        "https://picsum.photos/36",
                        "연세대학교",
                        "연대축제",
                        "05.06",
                        "05.08",
                        126.957f,
                        37.460f,
                    ),
                ),
            ),
            onShowSnackBar = {},
            setFestivalSearchBottomSheetVisible = {},
            updateFestivalSearchText = {},
            initSearchText = {},
            setEnableSearchMode = {},
            setEnableEditMode = {},
            setLikedFestivalDeleteDialogVisible = {},
            setSelectedDate = {},
            onAddLikeFestivalClick = {},
            onAddLikeFestivalAtBottomSheetSearch = {},
        )
    }
}
