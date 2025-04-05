package com.unifest.android.feature.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unifest.android.core.common.ObserveAsEvents
import com.unifest.android.core.common.UiText
import com.unifest.android.core.designsystem.component.UnifestOutlinedButton
import com.unifest.android.core.designsystem.theme.BoothLocation
import com.unifest.android.core.designsystem.theme.Content6
import com.unifest.android.core.designsystem.theme.Title2
import com.unifest.android.core.designsystem.theme.Title3
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.ui.DevicePreview
import com.unifest.android.feature.festival.FestivalSearchBottomSheet
import com.unifest.android.feature.festival.viewmodel.FestivalUiAction
import com.unifest.android.feature.festival.viewmodel.FestivalUiEvent
import com.unifest.android.feature.festival.viewmodel.FestivalUiState
import com.unifest.android.feature.festival.viewmodel.FestivalViewModel
import com.unifest.android.feature.home.component.Calendar
import com.unifest.android.feature.home.component.FestivalScheduleItem
import com.unifest.android.feature.home.component.IncomingFestivalCard
import com.unifest.android.feature.home.component.StarImageDialog
import com.unifest.android.feature.home.preview.HomePreviewParameterProvider
import com.unifest.android.feature.home.viewmodel.HomeUiAction
import com.unifest.android.feature.home.viewmodel.HomeUiEvent
import com.unifest.android.feature.home.viewmodel.HomeUiState
import com.unifest.android.feature.home.viewmodel.HomeViewModel
import java.time.format.DateTimeFormatter
import com.unifest.android.core.designsystem.R as designR

// TODO 이미 관심 축제로 추가된 축제는 관심 축제로 추가하기 버튼이 보이면 안됨
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
            is HomeUiEvent.ShowSnackBar -> onShowSnackBar(event.message)
        }
    }

    ObserveAsEvents(flow = festivalViewModel.uiEvent) { event ->
        when (event) {
            is FestivalUiEvent.NavigateBack -> popBackStack()
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
            .background(MaterialTheme.colorScheme.background)
            .padding(padding),
    ) {
        HomeContent(
            homeUiState = homeUiState,
            onHomeUiAction = onHomeUiAction,
            onFestivalUiAction = onFestivalUiAction,
        )

        if (festivalUiState.isFestivalSearchBottomSheetVisible) {
            FestivalSearchBottomSheet(
                searchText = festivalUiState.festivalSearchText,
                searchTextHintRes = designR.string.festival_search_text_field_hint,
                likedFestivals = festivalUiState.likedFestivals,
                festivalSearchResults = festivalUiState.festivalSearchResults,
                isSearchMode = festivalUiState.isSearchMode,
                isEditMode = festivalUiState.isEditMode,
                isLikedFestivalDeleteDialogVisible = festivalUiState.isLikedFestivalDeleteDialogVisible,
                onFestivalUiAction = onFestivalUiAction,
            )
        }

        if (homeUiState.isStarImageDialogVisible && homeUiState.selectedStar != null) {
            StarImageDialog(
                onDismissRequest = { onHomeUiAction(HomeUiAction.OnStarImageDialogDismiss) },
                starInfo = homeUiState.selectedStar,
            )
        }
    }
}

@Composable
internal fun HomeContent(
    homeUiState: HomeUiState,
    onHomeUiAction: (HomeUiAction) -> Unit,
    onFestivalUiAction: (FestivalUiAction) -> Unit,
) {
    LazyColumn {
        item {
            Calendar(
                selectedDate = homeUiState.selectedDate,
                onDateSelected = { date -> onHomeUiAction(HomeUiAction.OnDateSelected(date)) },
                allFestivals = homeUiState.allFestivals,
                isWeekMode = homeUiState.isWeekMode,
                onClickWeekMode = { onHomeUiAction(HomeUiAction.OnClickWeekMode) },
            )
        }
        item {
            Text(
                text = DateTimeFormatter.ofPattern("M월 d일")
                    .format(homeUiState.selectedDate) + stringResource(id = R.string.home_festival_schedule_text),
                modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                color = MaterialTheme.colorScheme.onBackground,
                style = Title3,
            )
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
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_schedule),
                            contentDescription = "축제 없음",
                            modifier = Modifier.size(23.dp),
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = stringResource(id = R.string.home_empty_festival_text),
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center,
                            style = Title2,
                        )
                        Spacer(modifier = Modifier.height(9.dp))
                        Text(
                            text = stringResource(id = R.string.home_empty_festival_schedule_description_text),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                            style = Content6,
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
                        // likedFestivals = homeUiState.likedFestivals,
                        selectedDate = homeUiState.selectedDate,
                        isDataReady = homeUiState.isDataReady,
                        // isStarImageClicked = homeUiState.isStarImageClicked[scheduleIndex],
                        onHomeUiAction = onHomeUiAction,
                    )
                }
                if (scheduleIndex < homeUiState.todayFestivals.size - 1) {
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        color = MaterialTheme.colorScheme.outline,
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
                contentColor = MaterialTheme.colorScheme.onSecondary,
                borderColor = MaterialTheme.colorScheme.secondaryContainer,
            ) {
                Text(
                    text = stringResource(id = R.string.home_add_interest_festival_button),
                    style = BoothLocation,
                )
            }
        }
        item {
            HorizontalDivider(
                thickness = 8.dp,
                color = MaterialTheme.colorScheme.outline,
            )
        }
        item { Spacer(modifier = Modifier.height(20.dp)) }
        item {
            Text(
                text = stringResource(id = R.string.home_incoming_festival_text),
                modifier = Modifier.padding(start = 20.dp, bottom = 16.dp),
                color = MaterialTheme.colorScheme.onBackground,
                style = Title3,
            )
        }
        if (homeUiState.incomingFestivals.isNotEmpty()) {
            items(homeUiState.incomingFestivals) { festival ->
                IncomingFestivalCard(festival = festival)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@DevicePreview
@Composable
private fun HomeScreenPreview(
    @PreviewParameter(HomePreviewParameterProvider::class)
    homeUiState: HomeUiState,
) {
    UnifestTheme {
        HomeScreen(
            padding = PaddingValues(),
            homeUiState = homeUiState,
            festivalUiState = FestivalUiState(),
            onHomeUiAction = {},
            onFestivalUiAction = {},
        )
    }
}
