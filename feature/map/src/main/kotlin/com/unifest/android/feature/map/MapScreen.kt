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
import com.unifest.android.feature.map.viewmodel.MapUiState
import com.unifest.android.feature.map.viewmodel.MapViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun MapRoute(
    padding: PaddingValues,
    onNavigateToBooth: (Long) -> Unit,
    viewModel: MapViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MapScreen(
        padding = padding,
        uiState = uiState,
        onNavigateToBooth = onNavigateToBooth,
    )
}


@OptIn(
    ExperimentalNaverMapApi::class,
    ExperimentalFoundationApi::class,
)
@Composable
internal fun MapScreen(
    padding: PaddingValues,
    uiState: MapUiState,
    onNavigateToBooth: (Long) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = padding.calculateBottomPadding()),
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
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MapTopAppBar(
    title: String,
    searchText: TextFieldState,
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
            )
            SearchTextField(
                searchText = searchText,
                searchTextHintRes = R.string.map_search_text_field_hint,
                onSearch = {},
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
            padding = PaddingValues(0.dp),
            uiState = MapUiState(
                selectedSchoolName = "건국대학교",
                searchText = TextFieldState(),
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
