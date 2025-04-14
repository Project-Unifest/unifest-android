package com.unifest.android.feature.map

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.clustering.Clusterer
import com.naver.maps.map.clustering.DefaultClusterOnClickListener
import com.naver.maps.map.clustering.DefaultDistanceStrategy
import com.naver.maps.map.clustering.DefaultMarkerManager
import com.naver.maps.map.clustering.DistanceStrategy
import com.naver.maps.map.clustering.Node
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.DisposableMapEffect
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.PolygonOverlay
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource
import com.naver.maps.map.compose.rememberMarkerState
import com.naver.maps.map.overlay.Align
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import com.unifest.android.core.common.ObserveAsEvents
import com.unifest.android.core.common.PermissionDialogButtonType
import com.unifest.android.core.common.UiText
import com.unifest.android.core.common.extension.findActivity
import com.unifest.android.core.designsystem.MarkerCategory
import com.unifest.android.core.designsystem.component.NetworkErrorDialog
import com.unifest.android.core.designsystem.component.ServerErrorDialog
import com.unifest.android.core.designsystem.theme.Title4
import com.unifest.android.core.designsystem.theme.UnifestTheme
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
import com.unifest.android.feature.map.model.ItemData
import com.unifest.android.feature.map.preview.MapPreviewParameterProvider
import com.unifest.android.feature.map.viewmodel.ErrorType
import com.unifest.android.feature.map.viewmodel.MapUiAction
import com.unifest.android.feature.map.viewmodel.MapUiEvent
import com.unifest.android.feature.map.viewmodel.MapUiState
import com.unifest.android.feature.map.viewmodel.MapViewModel
import kotlinx.collections.immutable.persistentListOf
import com.naver.maps.map.compose.Marker as ComposeMarker
import com.unifest.android.core.designsystem.R as designR

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

@OptIn(ExperimentalNaverMapApi::class, ExperimentalFoundationApi::class)
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
    val isClusteringEnabled by mapViewModel.isClusteringEnabled.collectAsStateWithLifecycle(true)
    val context = LocalContext.current
    val activity = context.findActivity()
    val dialogQueue = mapViewModel.permissionDialogQueue

    var isLocationPermissionGranted by remember {
        mutableStateOf(checkLocationPermission(activity))
    }

    var isNotificationPermissionGranted by remember {
        mutableStateOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                activity.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
            } else {
                true
            },
        )
    }

    val permissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            permissionsToRequest.forEach { permission ->
                when (permission) {
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION -> {
                        isLocationPermissionGranted = checkLocationPermission(activity)
                    }

                    Manifest.permission.POST_NOTIFICATIONS -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            isNotificationPermissionGranted = perms[permission] == true
                        }
                    }
                }
                mapViewModel.onPermissionResult(
                    permission = permission,
                    isGranted = perms[permission] == true,
                )
            }
        },
    )

    val settingsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            // 설정에서 돌아왔을 때 권한 상태를 다시 확인
            isLocationPermissionGranted = checkLocationPermission(activity)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                isNotificationPermissionGranted =
                    activity.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
            }
            mapViewModel.onPermissionResult(
                permission = Manifest.permission.ACCESS_FINE_LOCATION,
                isGranted = isLocationPermissionGranted,
            )
            mapViewModel.onPermissionResult(
                permission = Manifest.permission.ACCESS_COARSE_LOCATION,
                isGranted = isLocationPermissionGranted,
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                mapViewModel.onPermissionResult(
                    permission = Manifest.permission.POST_NOTIFICATIONS,
                    isGranted = isNotificationPermissionGranted,
                )
            }
        },
    )

    LaunchedEffect(key1 = mapUiState.festivalInfo) {
        if (mapUiState.festivalInfo.festivalId != 0L) {
            mapViewModel.getAllBooths(mapUiState.festivalInfo.festivalId)
            mapViewModel.getPopularBooths(mapUiState.festivalInfo.festivalId)
        }
    }

    ObserveAsEvents(flow = mapViewModel.uiEvent) { event ->
        when (event) {
            is MapUiEvent.RequestPermissions -> {
                permissionResultLauncher.launch(permissionsToRequest)
            }

            is MapUiEvent.RequestPermission -> {
                permissionResultLauncher.launch(arrayOf(event.permission))
            }

            is MapUiEvent.NavigateToAppSetting -> {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", activity.packageName, null)
                }
                settingsLauncher.launch(intent)
            }

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
            when (permission) {
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION -> {
                    if (!isLocationPermissionGranted) {
                        PermissionDialog(
                            permissionTextProvider = LocationPermissionTextProvider(),
                            isPermanentlyDeclined = !activity.shouldShowRequestPermissionRationale(permission),
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
                }

                Manifest.permission.POST_NOTIFICATIONS -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !isNotificationPermissionGranted) {
                        PermissionDialog(
                            permissionTextProvider = NotificationPermissionTextProvider(),
                            isPermanentlyDeclined = !activity.shouldShowRequestPermissionRationale(permission),
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
                }
            }
        }

    MapScreen(
        padding = padding,
        mapUiState = mapUiState,
        festivalUiState = festivalUiState,
        onMapUiAction = mapViewModel::onMapUiAction,
        onFestivalUiAction = festivalViewModel::onFestivalUiAction,
        isClusteringEnabled = isClusteringEnabled,
    )
}

private fun checkLocationPermission(activity: Activity): Boolean {
    return activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
        activity.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
}

@ExperimentalNaverMapApi
@ExperimentalFoundationApi
@Composable
internal fun MapScreen(
    padding: PaddingValues,
    mapUiState: MapUiState,
    festivalUiState: FestivalUiState,
    onMapUiAction: (MapUiAction) -> Unit,
    onFestivalUiAction: (FestivalUiAction) -> Unit,
    isClusteringEnabled: Boolean,
) {
    val cameraPositionState = rememberCameraPositionState()
    val rotationState by animateFloatAsState(targetValue = if (mapUiState.isPopularMode) 180f else 0f)
    val pagerState = rememberPagerState(pageCount = { mapUiState.selectedBoothList.size })

    LaunchedEffect(key1 = mapUiState.festivalInfo) {
        if (mapUiState.festivalInfo.latitude != 0.0F && mapUiState.festivalInfo.longitude != 0.0F) {
            cameraPositionState.position = CameraPosition(
                LatLng(mapUiState.festivalInfo.latitude.toDouble(), mapUiState.festivalInfo.longitude.toDouble()), 15.2,
            )
        }
    }

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
            isClusteringEnabled = isClusteringEnabled,
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
                uiState = festivalUiState,
                onFestivalUiAction = onFestivalUiAction,
            )
        }
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
internal fun MapContent(
    uiState: MapUiState,
    cameraPositionState: CameraPositionState,
    rotationState: Float,
    pagerState: PagerState,
    onMapUiAction: (MapUiAction) -> Unit,
    onFestivalUiAction: (FestivalUiAction) -> Unit,
    isClusteringEnabled: Boolean,
) {
    val context = LocalContext.current

    Box {
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

            if (isClusteringEnabled) {
                var clusterManager by remember { mutableStateOf<Clusterer<BoothMapModel>?>(null) }
                DisposableMapEffect(uiState.filteredBoothList) { map ->
                    if (clusterManager == null) {
                        clusterManager = Clusterer.ComplexBuilder<BoothMapModel>()
                            .minClusteringZoom(9)
                            .maxClusteringZoom(16)
                            .maxScreenDistance(200.0)
                            .thresholdStrategy { zoom ->
                                if (zoom <= 11) {
                                    0.0
                                } else {
                                    70.0
                                }
                            }
                            .distanceStrategy(
                                object : DistanceStrategy {
                                    private val defaultDistanceStrategy = DefaultDistanceStrategy()

                                    override fun getDistance(zoom: Int, node1: Node, node2: Node): Double {
                                        return if (zoom <= 9) {
                                            -1.0
                                        } else if ((node1.tag as ItemData).category == (node2.tag as ItemData).category) {
                                            if (zoom <= 11) {
                                                -1.0
                                            } else {
                                                defaultDistanceStrategy.getDistance(zoom, node1, node2)
                                            }
                                        } else {
                                            10000.0
                                        }
                                    }
                                },
                            )
                            .tagMergeStrategy { cluster ->
                                if (cluster.maxZoom <= 9) {
                                    null
                                } else {
                                    ItemData("", (cluster.children.first().tag as ItemData).category)
                                }
                            }
                            .markerManager(
                                object : DefaultMarkerManager() {
                                    override fun createMarker() = super.createMarker().apply {
                                        subCaptionTextSize = 10f
                                        subCaptionColor = android.graphics.Color.WHITE
                                        subCaptionHaloColor = android.graphics.Color.TRANSPARENT
                                    }
                                },
                            )
                            .clusterMarkerUpdater { info, marker ->
                                val size = info.size
                                marker.icon = OverlayImage.fromResource(designR.drawable.ic_cluster)
                                marker.captionText = size.toString()
                                marker.setCaptionAligns(Align.Center)
                                marker.captionColor = android.graphics.Color.WHITE
                                marker.captionHaloColor = android.graphics.Color.TRANSPARENT
                                marker.onClickListener = DefaultClusterOnClickListener(info)
                            }
                            .leafMarkerUpdater { info, marker ->
                                marker.icon = MarkerCategory.fromString((info.key as BoothMapModel).category)
                                    .getMarkerIcon((info.key as BoothMapModel).isSelected)
                                marker.onClickListener = Overlay.OnClickListener {
                                    onMapUiAction(MapUiAction.OnBoothMarkerClick(listOf(info.key as BoothMapModel)))
                                    true
                                }
                            }
                            .build()
                            .apply { this.map = map }
                    }
                    val boothListMap = uiState.filteredBoothList.associateWith { booth -> ItemData(booth.name, booth.category) }
                    clusterManager?.addAll(boothListMap)
                    onDispose {
                        clusterManager?.clear()
                    }
                }

//                var clusterManager by remember { mutableStateOf<TedNaverClustering<BoothMapModel>?>(null) }
//                DisposableMapEffect(uiState.filteredBoothList) { map ->
//                    if (clusterManager == null) {
//                        clusterManager = TedNaverClustering.with<BoothMapModel>(context, map)
//                            .clusterClickListener { booths ->
//                                onMapUiAction(MapUiAction.OnBoothMarkerClick(booths.items.toList()))
//                            }
//                            .customMarker {
//                                Marker().apply {
//                                    icon = MarkerCategory.fromString(it.category).getMarkerIcon(it.isSelected)
//                                }
//                            }
//                            .markerClickListener { booth ->
//                                onMapUiAction(MapUiAction.OnBoothMarkerClick(listOf(booth)))
//                            }
//                            // 마커를 클릭 했을 경우 마커의 위치로 카메라 이동 비활성화
//                            .clickToCenter(false)
//                            .make()
//                    }
//                    clusterManager?.addItems(uiState.filteredBoothList)
//                    onDispose {
//                        clusterManager?.clearItems()
//                    }
//                }
            } else {
                uiState.filteredBoothList.forEach { booth ->
                    ComposeMarker(
                        state = rememberMarkerState(position = LatLng(booth.latitude, booth.longitude)),
                        icon = MarkerCategory.fromString(booth.category).getMarkerIcon(booth.isSelected),
                        onClick = {
                            onMapUiAction(MapUiAction.OnSingleBoothMarkerClick(booth))
                            true
                        },
                    )
                }
            }
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

@OptIn(ExperimentalNaverMapApi::class, ExperimentalFoundationApi::class)
@DevicePreview
@Composable
private fun MapScreenPreview(
    @PreviewParameter(MapPreviewParameterProvider::class)
    mapUiState: MapUiState,
) {
    UnifestTheme {
        MapScreen(
            padding = PaddingValues(),
            mapUiState = mapUiState,
            festivalUiState = FestivalUiState(),
            onMapUiAction = {},
            onFestivalUiAction = {},
            isClusteringEnabled = true,
        )
    }
}
