package com.unifest.android.feature.map

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.PolygonOverlay
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource
import com.unifest.android.core.common.ObserveAsEvents
import com.unifest.android.core.common.PermissionDialogButtonType
import com.unifest.android.core.common.UiText
import com.unifest.android.core.common.extension.findActivity
import com.unifest.android.core.common.extension.navigateToAppSetting
import com.unifest.android.core.designsystem.MarkerCategory
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.component.NetworkErrorDialog
import com.unifest.android.core.designsystem.component.ServerErrorDialog
import com.unifest.android.core.designsystem.theme.Title4
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.core.ui.DevicePreview
import com.unifest.android.core.ui.component.LocationPermissionTextProvider
import com.unifest.android.core.ui.component.NotificationPermissionTextProvider
import com.unifest.android.core.ui.component.PermissionDialog
import com.unifest.android.feature.festival.FestivalSearchBottomSheet
import com.unifest.android.feature.festival.viewmodel.FestivalUiAction
import com.unifest.android.feature.festival.viewmodel.FestivalUiEvent
import com.unifest.android.feature.festival.viewmodel.FestivalUiState
import com.unifest.android.feature.festival.viewmodel.FestivalViewModel
import com.unifest.android.feature.map.component.BoothItem
import com.unifest.android.feature.map.component.MapTopAppBar
import com.unifest.android.feature.map.model.BoothMapModel
import com.unifest.android.feature.map.viewmodel.ErrorType
import com.unifest.android.feature.map.viewmodel.MapUiAction
import com.unifest.android.feature.map.viewmodel.MapUiEvent
import com.unifest.android.feature.map.viewmodel.MapUiState
import com.unifest.android.feature.map.viewmodel.MapViewModel
import kotlinx.collections.immutable.persistentListOf

val permissionsToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.POST_NOTIFICATIONS,
    )
} else {
    arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )
}

@Composable
internal fun MapRoute(
    padding: PaddingValues,
    navigateToBoothDetail: (Long) -> Unit,
    onShowSnackBar: (UiText) -> Unit,
    mapViewModel: MapViewModel = hiltViewModel(),
    festivalViewModel: FestivalViewModel = hiltViewModel(),
) {
    val mapUiState by mapViewModel.uiState.collectAsStateWithLifecycle()
    val festivalUiState by festivalViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val activity = context.findActivity()
    val dialogQueue = mapViewModel.permissionDialogQueue

    val permissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            permissionsToRequest.forEach { permission ->
                if (perms.contains(permission)) {
                    mapViewModel.onPermissionResult(
                        permission = permission,
                        isGranted = perms[permission] == true,
                    )
                }
            }
        },
    )

    LaunchedEffect(key1 = Unit) {
        mapViewModel.getAllBooths()
        mapViewModel.getPopularBooths()
    }

    ObserveAsEvents(flow = mapViewModel.uiEvent) { event ->
        when (event) {
            is MapUiEvent.RequestPermissions -> {
                permissionResultLauncher.launch(permissionsToRequest)
            }

            is MapUiEvent.RequestPermission -> {
                permissionResultLauncher.launch(arrayOf(event.permission))
            }

            is MapUiEvent.NavigateToAppSetting -> activity.navigateToAppSetting()
            is MapUiEvent.NavigateToBoothDetail -> navigateToBoothDetail(event.boothId)
            is MapUiEvent.ShowSnackBar -> onShowSnackBar(event.message)
        }
    }

    ObserveAsEvents(flow = festivalViewModel.uiEvent) { event ->
        when (event) {
            is FestivalUiEvent.NavigateBack -> {}
            is FestivalUiEvent.ShowSnackBar -> onShowSnackBar(event.message)
            is FestivalUiEvent.ShowToast -> Toast.makeText(context, event.message.asString(context), Toast.LENGTH_SHORT).show()
        }
    }

    dialogQueue
        .reversed()
        .forEach { permission ->
            PermissionDialog(
                permissionTextProvider = when (permission) {
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION -> {
                        LocationPermissionTextProvider()
                    }

                    Manifest.permission.POST_NOTIFICATIONS -> {
                        NotificationPermissionTextProvider()
                    }

                    else -> return@forEach
                },
                isPermanentlyDeclined = !activity.shouldShowRequestPermissionRationale(
                    permission,
                ),
                onDismiss = {
                    mapViewModel.onMapUiAction(
                        MapUiAction.OnPermissionDialogButtonClick(PermissionDialogButtonType.DISMISS),
                    )
                },
                navigateToAppSetting = {
                    mapViewModel.onMapUiAction(
                        MapUiAction.OnPermissionDialogButtonClick(PermissionDialogButtonType.NAVIGATE_TO_APP_SETTING),
                    )
                },
                onConfirm = {
                    mapViewModel.onMapUiAction(
                        MapUiAction.OnPermissionDialogButtonClick(PermissionDialogButtonType.CONFIRM, permission),
                    )
                },
            )
        }

    MapScreen(
        padding = padding,
        mapUiState = mapUiState,
        festivalUiState = festivalUiState,
        onMapUiAction = mapViewModel::onMapUiAction,
        onFestivalUiAction = festivalViewModel::onFestivalUiAction,
    )
}

@Composable
internal fun MapScreen(
    padding: PaddingValues,
    mapUiState: MapUiState,
    festivalUiState: FestivalUiState,
    onMapUiAction: (MapUiAction) -> Unit,
    onFestivalUiAction: (FestivalUiAction) -> Unit,
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(LatLng(37.5430, 127.07673671067072), 14.8)
    }
    val rotationState by animateFloatAsState(targetValue = if (mapUiState.isPopularMode) 180f else 0f)
    val pagerState = rememberPagerState(pageCount = { mapUiState.selectedBoothList.size })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        MapContent(
            uiState = mapUiState,
            cameraPositionState = cameraPositionState,
            rotationState = rotationState,
            pagerState = pagerState,
            onMapUiAction = onMapUiAction,
            onFestivalUiAction = onFestivalUiAction,
        )

        if (mapUiState.isServerErrorDialogVisible) {
            ServerErrorDialog(
                onRetryClick = { onMapUiAction(MapUiAction.OnRetryClick(ErrorType.SERVER)) },
            )
        }

        if (mapUiState.isNetworkErrorDialogVisible) {
            NetworkErrorDialog(
                onRetryClick = { onMapUiAction(MapUiAction.OnRetryClick(ErrorType.NETWORK)) },
            )
        }

        if (festivalUiState.isFestivalSearchBottomSheetVisible) {
            FestivalSearchBottomSheet(
                searchText = festivalUiState.festivalSearchText,
                searchTextHintRes = R.string.festival_search_text_field_hint,
                likedFestivals = festivalUiState.likedFestivals,
                festivalSearchResults = festivalUiState.festivalSearchResults,
                isSearchMode = festivalUiState.isSearchMode,
                isLikedFestivalDeleteDialogVisible = festivalUiState.isLikedFestivalDeleteDialogVisible,
                onFestivalUiAction = onFestivalUiAction,
                isEditMode = festivalUiState.isEditMode,
            )
        }
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapContent(
    uiState: MapUiState,
    cameraPositionState: CameraPositionState,
    rotationState: Float,
    pagerState: PagerState,
    onMapUiAction: (MapUiAction) -> Unit,
    onFestivalUiAction: (FestivalUiAction) -> Unit,
) {
    // val context = LocalContext.current
    Box {
        // TODO 같은 속성의 Marker 들만 클러스터링 되도록 구현
        // TODO 클러스터링 마커 커스텀
        NaverMap(
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                locationTrackingMode = LocationTrackingMode.NoFollow,
                isNightModeEnabled = isSystemInDarkTheme(),
            ),
            uiSettings = MapUiSettings(
                isZoomControlEnabled = false,
                isScaleBarEnabled = false,
                isLogoClickEnabled = false,
            ),
            locationSource = rememberFusedLocationSource(),
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
//                        .clusterMarkerUpdater(
//                            object : DefaultClusterMarkerUpdater() {
//                                override fun updateClusterMarker(info: ClusterMarkerInfo, marker: Marker) {
//                                    super.updateClusterMarker(info, marker)
//                                }
//                            },
//                        )
//                        .leafMarkerUpdater(
//                            object : DefaultLeafMarkerUpdater() {
//                                override fun updateLeafMarker(info: LeafMarkerInfo, marker: Marker) {
//                                    super.updateLeafMarker(info, marker)
//                                    marker.apply {
//                                        icon = MarkerCategory.fromString((info.key as BoothMapModel).category)
//                                            .getMarkerIcon((info.key as BoothMapModel).isSelected)
//                                        onClickListener = Overlay.OnClickListener {
//                                            onMapUiAction(MapUiAction.OnBoothMarkerClick(listOf(info.key as BoothMapModel)))
//                                            true
//                                        }
//                                    }
//                                }
//                            },
//                        )
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
            onFestivalUiAction = onFestivalUiAction,
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
                    .background(MaterialTheme.colorScheme.surface)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
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
                        color = MaterialTheme.colorScheme.primary,
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
            mapUiState = MapUiState(
                festivalInfo = FestivalModel(
                    schoolName = "건국대학교",
                ),
                boothList = boothList,
            ),
            festivalUiState = FestivalUiState(),
            onMapUiAction = {},
            onFestivalUiAction = {},
        )
    }
}
