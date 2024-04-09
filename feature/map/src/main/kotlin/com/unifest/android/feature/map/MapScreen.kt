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
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@Composable
internal fun MapRoute(
    viewModel: MapViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MapScreen(
        uiState = uiState,
        setFestivalSearchBottomSheetVisible = viewModel::setFestivalSearchBottomSheetVisible,
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
    setFestivalSearchBottomSheetVisible: (Boolean) -> Unit,
    initSearchText: () -> Unit,
    setEnableSearchMode: () -> Unit,
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
                    )
                }
            }
            MapTopAppBar(
                title = uiState.selectedSchoolName,
                searchText = uiState.searchText,
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
                    searchTextHintRes = R.string.festival_search_text_field_hint,
                    setFestivalSearchBottomSheetVisible = setFestivalSearchBottomSheetVisible,
                    interestedFestivals = uiState.interestedFestivals,
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
    searchText: TextFieldState,
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
                boothSpots = persistentListOf(
                    BoothSpot(37.54053013863604, 127.07505652524804),
                    BoothSpot(37.54111712868565, 127.07839319326257),
                    BoothSpot(37.5414744247141, 127.07779237844323),
                    BoothSpot(37.54224856023523, 127.07605430700158),
                    BoothSpot(37.54003672313541, 127.07653710462426),
                    BoothSpot(37.53998567996623, 37.53998567996623),
                    BoothSpot(37.54152546686414, 127.07353303052759),
                    BoothSpot(37.54047909580466, 127.07398364164209),
                ),
                boothList = boothList.toImmutableList(),
            ),
            setFestivalSearchBottomSheetVisible = {},
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
            searchText = TextFieldState(),
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
