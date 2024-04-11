package com.unifest.android.feature.map

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.overlay.OverlayImage
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.component.SearchTextField
import com.unifest.android.core.designsystem.component.TopAppBarNavigationType
import com.unifest.android.core.designsystem.component.UnifestTopAppBar
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.domain.entity.BoothDetailEntity
import com.unifest.android.core.domain.entity.BoothSpot
import com.unifest.android.core.ui.DevicePreview
import com.unifest.android.core.ui.component.BoothCard
import com.unifest.android.core.ui.component.BoothFilterChips
import com.unifest.android.core.ui.component.FestivalSearchBottomSheet
import com.unifest.android.feature.map.viewmodel.MapUiState
import com.unifest.android.feature.map.viewmodel.MapViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import tech.thdev.compose.exteions.system.ui.controller.rememberExSystemUiController

@Composable
internal fun MapRoute(
    onNavigateToBooth: (Long) -> Unit,
    viewModel: MapViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val systemUiController = rememberExSystemUiController()

    DisposableEffect(systemUiController) {
        systemUiController.setSystemBarsColor(
            color = Color.White,
            darkIcons = true,
            isNavigationBarContrastEnforced = false,
        )
        onDispose {}
    }

    MapScreen(
        uiState = uiState,
        onNavigateToBooth = onNavigateToBooth,
        setFestivalSearchBottomSheetVisible = viewModel::setFestivalSearchBottomSheetVisible,
        updateBoothSearchText = viewModel::updateBoothSearchText,
        updateFestivalSearchText = viewModel::updateFestivalSearchText,
        initSearchText = viewModel::initSearchText,
        setEnableSearchMode = viewModel::setEnableSearchMode,
        setEnableEditMode = viewModel::setEnableEditMode,
        setInterestedFestivalDeleteDialogVisible = viewModel::setInterestedFestivalDeleteDialogVisible,
    )
}

@OptIn(
    ExperimentalNaverMapApi::class,
    ExperimentalFoundationApi::class,
)
@Composable
internal fun MapScreen(
    uiState: MapUiState,
    onNavigateToBooth: (Long) -> Unit,
    setFestivalSearchBottomSheetVisible: (Boolean) -> Unit,
    updateBoothSearchText: (TextFieldValue) -> Unit,
    updateFestivalSearchText: (TextFieldValue) -> Unit,
    initSearchText: () -> Unit,
    setEnableSearchMode: (Boolean) -> Unit,
    setEnableEditMode: () -> Unit,
    setInterestedFestivalDeleteDialogVisible: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition(LatLng(37.540470588662664, 127.0765263757882), 14.0)
        }
        val pagerState = rememberPagerState(pageCount = { uiState.boothList.size })
        Box {
            NaverMap(
                cameraPositionState = cameraPositionState,
                modifier = Modifier.fillMaxSize(),
            ) {
                uiState.boothSpots.forEach { spot ->
                    Marker(
                        state = MarkerState(position = LatLng(spot.lat, spot.lng)),
                        icon = OverlayImage.fromResource(R.drawable.ic_general),
                        onClick = {
                            onNavigateToBooth(spot.id)
                            true
                        },
                    )
                }
            }
            MapTopAppBar(
                title = uiState.selectedSchoolName,
                searchText = uiState.boothSearchText,
                updateSearchText = updateBoothSearchText,
                onTitleClick = setFestivalSearchBottomSheetVisible,
                initSearchText = initSearchText,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter),
            )
            BoothCards(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .wrapContentHeight()
                    .padding(bottom = 21.dp),
                boothList = uiState.boothList,
            )
            if (uiState.isFestivalSearchBottomSheetVisible) {
                FestivalSearchBottomSheet(
                    searchText = uiState.festivalSearchText,
                    updateSearchText = updateFestivalSearchText,
                    searchTextHintRes = R.string.festival_search_text_field_hint,
                    setFestivalSearchBottomSheetVisible = setFestivalSearchBottomSheetVisible,
                    interestedFestivals = uiState.interestedFestivals,
                    festivalSearchResults = uiState.festivalSearchResults,
                    initSearchText = initSearchText,
                    setEnableSearchMode = setEnableSearchMode,
                    isSearchMode = uiState.isSearchMode,
                    setEnableEditMode = setEnableEditMode,
                    isInterestedFestivalDeleteDialogVisible = uiState.isInterestedFestivalDeleteDialogVisible,
                    setInterestedFestivalDeleteDialogVisible = setInterestedFestivalDeleteDialogVisible,
                    isEditMode = uiState.isEditMode,
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MapTopAppBar(
    title: String,
    searchText: TextFieldValue,
    updateSearchText: (TextFieldValue) -> Unit,
    onTitleClick: (Boolean) -> Unit,
    initSearchText: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(
            bottomStart = 32.dp,
            bottomEnd = 32.dp,
        ),
    ) {
        Column(
            modifier = Modifier.background(Color.White),
        ) {
            UnifestTopAppBar(
                navigationType = TopAppBarNavigationType.Search,
                title = title,
                onTitleClick = onTitleClick,
            )
            SearchTextField(
                searchText = searchText,
                updateSearchText = updateSearchText,
                searchTextHintRes = R.string.map_booth_search_text_field_hint,
                onSearch = {},
                initSearchText = initSearchText,
                modifier = Modifier
                    .height(46.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
            )
            Spacer(modifier = Modifier.height(10.dp))
            BoothFilterChips(
                onChipClick = {},
                modifier = Modifier.padding(start = 22.dp),
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BoothCards(
    pagerState: PagerState,
    boothList: ImmutableList<BoothDetailEntity>,
    modifier: Modifier = Modifier,
) {
    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 30.dp),
    ) { page ->
        BoothCard(
            boothInfo = boothList[page],
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@DevicePreview
@Composable
fun MapScreenPreview() {
    val boothList = mutableListOf<BoothDetailEntity>()
    repeat(5) {
        boothList.add(
            BoothDetailEntity(
                id = 1L,
                name = "컴공 주점",
                category = "",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                warning = "",
                location = "청심대 앞",
                latitude = 0f,
                longitude = 0f,
                menus = emptyList(),
            ),
        )
    }
    UnifestTheme {
        MapScreen(
            uiState = MapUiState(
                selectedSchoolName = "건국대학교",
                boothList = boothList.toImmutableList(),
                boothSpots = persistentListOf(
                    BoothSpot(
                        lat = 37.540470588662664,
                        lng = 127.0765263757882,
                        id = 1L,
                    ),
                ),
            ),
            onNavigateToBooth = {},
            setFestivalSearchBottomSheetVisible = {},
            updateBoothSearchText = {},
            updateFestivalSearchText = {},
            initSearchText = {},
            setEnableSearchMode = {},
            setEnableEditMode = {},
            setInterestedFestivalDeleteDialogVisible = {},
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@ComponentPreview
@Composable
fun MapTopAppBarPreview() {
    UnifestTheme {
        MapTopAppBar(
            title = "건국대학교",
            searchText = TextFieldValue(),
            updateSearchText = {},
            initSearchText = {},
            onTitleClick = {},
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@ComponentPreview
@Composable
fun BoothCardsPreview() {
    val boothList = mutableListOf<BoothDetailEntity>()
    repeat(5) {
        boothList.add(
            BoothDetailEntity(
                id = 1L,
                name = "컴공 주점",
                category = "",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                warning = "",
                location = "청심대 앞",
                latitude = 0f,
                longitude = 0f,
                menus = emptyList(),
            ),
        )
    }

    UnifestTheme {
        BoothCards(
            pagerState = rememberPagerState(pageCount = { boothList.size }),
            modifier = Modifier.height(116.dp),
            boothList = boothList.toImmutableList(),
        )
    }
}
