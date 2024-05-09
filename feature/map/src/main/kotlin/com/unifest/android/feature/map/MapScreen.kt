package com.unifest.android.feature.map

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.PolygonOverlay
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource
import com.unifest.android.core.common.FestivalUiAction
import com.unifest.android.core.common.ObserveAsEvents
import com.unifest.android.core.common.UiText
import com.unifest.android.core.common.extension.findActivity
import com.unifest.android.core.common.extension.goToAppSettings
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.MarkerCategory
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.component.NetworkErrorDialog
import com.unifest.android.core.designsystem.component.NetworkImage
import com.unifest.android.core.designsystem.component.SearchTextField
import com.unifest.android.core.designsystem.component.ServerErrorDialog
import com.unifest.android.core.designsystem.component.TopAppBarNavigationType
import com.unifest.android.core.designsystem.component.UnifestTopAppBar
import com.unifest.android.core.designsystem.theme.Content2
import com.unifest.android.core.designsystem.theme.MainColor
import com.unifest.android.core.designsystem.theme.Title2
import com.unifest.android.core.designsystem.theme.Title4
import com.unifest.android.core.designsystem.theme.Title5
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.core.ui.DevicePreview
import com.unifest.android.core.ui.component.BoothFilterChips
import com.unifest.android.core.ui.component.FestivalSearchBottomSheet
import com.unifest.android.feature.map.model.BoothMapModel
import com.unifest.android.feature.map.viewmodel.ErrorType
import com.unifest.android.feature.map.viewmodel.MapUiAction
import com.unifest.android.feature.map.viewmodel.MapUiEvent
import com.unifest.android.feature.map.viewmodel.MapUiState
import com.unifest.android.feature.map.viewmodel.MapViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun MapRoute(
    padding: PaddingValues,
    navigateToBoothDetail: (Long) -> Unit,
    onShowSnackBar: (UiText) -> Unit,
    viewModel: MapViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val activity = context.findActivity()

    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
    )

    val locationPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            viewModel.onPermissionResult(isGranted = permissions.none { it.value })
        },
    )

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllBooths()
        viewModel.getPopularBooths()
    }

    ObserveAsEvents(flow = viewModel.uiEvent) { event ->
        when (event) {
            is MapUiEvent.RequestLocationPermission -> {
                locationPermissionResultLauncher.launch(locationPermissions)
            }

            is MapUiEvent.GoToAppSettings -> activity.goToAppSettings()
            is MapUiEvent.NavigateToBoothDetail -> navigateToBoothDetail(event.boothId)
            is MapUiEvent.ShowSnackBar -> onShowSnackBar(event.message)
            is MapUiEvent.ShowToast -> Toast.makeText(context, event.message.asString(context), Toast.LENGTH_SHORT).show()
        }
    }

    MapScreen(
        padding = padding,
        uiState = uiState,
        onMapUiAction = viewModel::onMapUiAction,
        onFestivalUiAction = viewModel::onFestivalUiAction,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun MapScreen(
    padding: PaddingValues,
    uiState: MapUiState,
    onMapUiAction: (MapUiAction) -> Unit,
    onFestivalUiAction: (FestivalUiAction) -> Unit,
) {
    val activity = LocalContext.current.findActivity()
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(LatLng(37.5430, 127.07673671067072), 14.8)
    }
    val rotationState by animateFloatAsState(targetValue = if (uiState.isPopularMode) 180f else 0f)
    val pagerState = rememberPagerState(pageCount = { uiState.selectedBoothList.size })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        MapContent(
            uiState = uiState,
            cameraPositionState = cameraPositionState,
            rotationState = rotationState,
            pagerState = pagerState,
            onMapUiAction = onMapUiAction,
        )

        if (uiState.isServerErrorDialogVisible) {
            ServerErrorDialog(
                onRetryClick = { onMapUiAction(MapUiAction.OnRetryClick(ErrorType.SERVER)) },
            )
        }

        if (uiState.isNetworkErrorDialogVisible) {
            NetworkErrorDialog(
                onRetryClick = { onMapUiAction(MapUiAction.OnRetryClick(ErrorType.NETWORK)) },
            )
        }

        if (uiState.isFestivalSearchBottomSheetVisible) {
            FestivalSearchBottomSheet(
                searchText = uiState.festivalSearchText,
                searchTextHintRes = R.string.festival_search_text_field_hint,
                likedFestivals = uiState.likedFestivals,
                festivalSearchResults = uiState.festivalSearchResults,
                isSearchMode = uiState.isSearchMode,
                isLikedFestivalDeleteDialogVisible = uiState.isLikedFestivalDeleteDialogVisible,
                onFestivalUiAction = onFestivalUiAction,
                isEditMode = uiState.isEditMode,
                isOnboardingCompleted = uiState.isFestivalOnboardingCompleted,
            )
        }
        if (uiState.isPermissionDialogVisible) {
            PermissionDialog(
                permissionTextProvider = LocationPermissionTextProvider(),
                isPermanentlyDeclined = !activity.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION),
                onMapUiAction = onMapUiAction,
            )
        }
    }
}

@OptIn(ExperimentalNaverMapApi::class, ExperimentalFoundationApi::class)
@Composable
fun MapContent(
    uiState: MapUiState,
    cameraPositionState: CameraPositionState,
    rotationState: Float,
    pagerState: PagerState,
    onMapUiAction: (MapUiAction) -> Unit,
) {
    // val context = LocalContext.current
    Box {
        // TODO 같은 속성의 Marker 들만 클러스터링 되도록 구현
        // TODO 클러스터링 마커 커스텀
        // TODO 지도 중앙 위치 조정
        NaverMap(
            cameraPositionState = cameraPositionState,
            locationSource = rememberFusedLocationSource(),
            uiSettings = MapUiSettings(
                isZoomControlEnabled = false,
                isScaleBarEnabled = false,
                isLogoClickEnabled = false,
            ),
        ) {
            PolygonOverlay(
                coords = uiState.outerCords,
                color = Color.Gray.copy(alpha = 0.3f),
                outlineColor = Color.Gray,
                outlineWidth = 1.dp,
                holes = persistentListOf(uiState.innerHole),
            )
            uiState.filteredBoothsList.forEach { booth ->
                Marker(
                    state = MarkerState(position = booth.position),
                    icon = MarkerCategory.fromString(booth.category).getMarkerIcon(booth.isSelected),
                    onClick = {
                        onMapUiAction(MapUiAction.OnBoothMarkerClick(booth))
                        true
                    },
                )
            }
//            var clusterManager by remember { mutableStateOf<Clusterer<BoothMapModel>?>(null) }
//            DisposableMapEffect(uiState.boothList) { map ->
//                if (clusterManager == null) {
//                    clusterManager = Clusterer.Builder<BoothMapModel>()
//                        .clusterMarkerUpdater(object : DefaultClusterMarkerUpdater() {})
//                        .leafMarkerUpdater(object : DefaultLeafMarkerUpdater() {
//                            override fun updateLeafMarker(info: LeafMarkerInfo, marker: Marker) {
//                                super.updateLeafMarker(info, marker)
//                                marker.apply {
//                                    icon = MarkerCategory.fromString((info.key as BoothMapModel).category).getMarkerIcon((info.key as BoothMapModel).isSelected)
//                                    onClickListener = Overlay.OnClickListener {
//                                        onMapUiAction(MapUiAction.OnBoothMarkerClick(listOf(info.key as BoothMapModel)))
//                                        true
//                                    }
//                                }
//                            }
//                        })
//                        .build()
//                        .apply { this.map = map }
//                }
//                val boothListMap = buildMap(uiState.boothList.size) {
//                    uiState.boothList.forEachIndexed { index, booth ->
//                        put(booth, index)
//                    }
//                }
//                clusterManager?.addAll(boothListMap)
//                onDispose {
//                    clusterManager?.clear()
//                }
//            }

//            var clusterManager by remember { mutableStateOf<TedNaverClustering<BoothMapModel>?>(null) }
//            DisposableMapEffect(uiState.filteredBoothsList) { map ->
//                if (clusterManager == null) {
//                    clusterManager = TedNaverClustering.with<BoothMapModel>(context, map)
//                        .customMarker {
//                            Marker().apply {
//                                icon = MarkerCategory.fromString(it.category).getMarkerIcon(it.isSelected)
//                            }
//                        }
//                        .markerClickListener { booth ->
//                            onMapUiAction(MapUiAction.OnBoothMarkerClick(listOf(booth)))
//                        }
//                        .clusterClickListener { booths ->
//                            onMapUiAction(MapUiAction.OnBoothMarkerClick(booths.items.toList()))
//                        }
//                        // 마커를 클릭 했을 경우 마커의 위치로 카메라 이동 비활성화
//                        .clickToCenter(false)
//                        .make()
//                }
//                clusterManager?.addItems(uiState.filteredBoothsList)
//                onDispose {
//                    clusterManager?.clearItems()
//                }
//            }
        }
        MapTopAppBar(
            title = uiState.festivalInfo.schoolName,
            boothSearchText = uiState.boothSearchText,
            onMapUiAction = onMapUiAction,
            isOnboardingCompleted = uiState.isMapOnboardingCompleted,
            selectedChips = uiState.selectedBoothTypeChips,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .width(116.dp)
                    .height(36.dp)
                    .clip(RoundedCornerShape(39.dp))
                    .background(Color.White)
                    .border(
                        width = 1.dp,
                        color = MainColor,
                        shape = RoundedCornerShape(39.dp),
                    )
                    .clickable {
                        onMapUiAction(MapUiAction.OnTogglePopularBooth)
                    },
            ) {
                Row(
                    modifier = Modifier.align(Alignment.Center),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = stringResource(id = R.string.map_popular_booth),
                        color = MainColor,
                        style = Title4,
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_dropdown),
                        contentDescription = "Dropdown Icon",
                        tint = Color.Unspecified,
                        modifier = Modifier.rotate(rotationState),
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            AnimatedVisibility(uiState.isPopularMode || uiState.isBoothSelectionMode) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.wrapContentHeight(),
                    contentPadding = PaddingValues(horizontal = 30.dp),
                ) { page ->
                    BoothItem(
                        boothInfo = uiState.selectedBoothList[page],
                        isPopularMode = uiState.isPopularMode,
                        ranking = page + 1,
                        onAction = onMapUiAction,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                    )
                }
            }
            Spacer(modifier = Modifier.height(21.dp))
        }
    }
}

@Composable
fun MapTopAppBar(
    title: String,
    boothSearchText: TextFieldValue,
    isOnboardingCompleted: Boolean,
    onMapUiAction: (MapUiAction) -> Unit,
    selectedChips: ImmutableList<String>,
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
                onTitleClick = { onMapUiAction(MapUiAction.OnTitleClick) },
                isOnboardingCompleted = isOnboardingCompleted,
                onTooltipClick = { onMapUiAction(MapUiAction.OnTooltipClick) },
            )
            SearchTextField(
                searchText = boothSearchText,
                updateSearchText = { text -> onMapUiAction(MapUiAction.OnSearchTextUpdated(text)) },
                searchTextHintRes = R.string.map_booth_search_text_field_hint,
                onSearch = { onMapUiAction(MapUiAction.OnSearch(boothSearchText)) },
                clearSearchText = { onMapUiAction(MapUiAction.OnSearchTextCleared) },
                modifier = Modifier
                    .height(46.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
            )
            Spacer(modifier = Modifier.height(10.dp))
            BoothFilterChips(
                onChipClick = { chip -> onMapUiAction(MapUiAction.OnBoothTypeChipClick(chip)) },
                selectedChips = selectedChips,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .clip(
                        RoundedCornerShape(16.dp),
                    ),
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun BoothItem(
    boothInfo: BoothMapModel,
    isPopularMode: Boolean,
    ranking: Int,
    onAction: (MapUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val textStyle = Content2
    val textHeight = remember(textStyle) {
        with(density) { Content2.fontSize.toDp() * 2 }
    }

    Card(
        modifier = modifier.clickable {
            onAction(MapUiAction.OnBoothItemClick(boothInfo.id))
        },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Box {
            Row(
                modifier = Modifier.padding(15.dp),
            ) {
                NetworkImage(
                    imgUrl = boothInfo.thumbnail,
                    contentDescription = "Booth Thumbnail",
                    modifier = Modifier
                        .size(86.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    placeholder = painterResource(id = R.drawable.ic_item_placeholder),
                )
                Column(
                    modifier = Modifier.padding(start = 15.dp),
                ) {
                    Text(
                        text = boothInfo.name,
                        style = Title2,
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = boothInfo.description,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = Content2,
                        modifier = Modifier.heightIn(min = textHeight),
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_location_green),
                            contentDescription = "Location Icon",
                            tint = Color.Unspecified,
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = boothInfo.location,
                            style = Title5,
                        )
                    }
                }
            }
            if (isPopularMode) {
                RankingBadge(ranking = ranking)
            }
        }
    }
}

@Composable
fun RankingBadge(ranking: Int) {
    Box(
        modifier = Modifier
            .size(width = 43.dp, height = 45.dp)
            .padding(start = 7.dp, top = 9.dp)
            .clip(CircleShape)
            .background(MainColor, CircleShape),
        contentAlignment = Alignment.TopStart,
    ) {
        Text(
            text = stringResource(id = R.string.map_ranking, ranking),
            color = Color.White,
            style = Title5,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@DevicePreview
@Composable
fun MapScreenPreview() {
    val boothList = persistentListOf<BoothMapModel>()
    repeat(5) { index ->
        boothList.add(
            BoothMapModel(
                id = index.toLong(),
                name = "컴공 주점",
                category = "",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                location = "청심대 앞",
            ),
        )
    }

    UnifestTheme {
        MapScreen(
            padding = PaddingValues(),
            uiState = MapUiState(
                festivalInfo = FestivalModel(
                    schoolName = "건국대학교",
                ),
                boothList = boothList,
            ),
            onMapUiAction = {},
            onFestivalUiAction = {},
        )
    }
}

@ComponentPreview
@Composable
fun MapTopAppBarPreview() {
    UnifestTheme {
        MapTopAppBar(
            title = "건국대학교",
            boothSearchText = TextFieldValue(),
            isOnboardingCompleted = false,
            onMapUiAction = {},
            selectedChips = persistentListOf("주점", "먹거리"),
        )
    }
}

@ComponentPreview
@Composable
fun BoothItemPreview() {
    UnifestTheme {
        BoothItem(
            boothInfo = BoothMapModel(
                id = 1L,
                name = "컴공 주점",
                category = "",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                location = "청심대 앞",
            ),
            isPopularMode = true,
            ranking = 1,
            onAction = {},
        )
    }
}

@ComponentPreview
@Composable
fun RankingBadgePreview() {
    UnifestTheme {
        RankingBadge(ranking = 1)
    }
}
