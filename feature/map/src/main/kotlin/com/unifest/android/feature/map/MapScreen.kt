package com.unifest.android.feature.map

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.PointF
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.compose.LocalActivity
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
import androidx.compose.runtime.snapshotFlow
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
import androidx.core.graphics.createBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.clustering.Clusterer
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
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import com.skydoves.compose.effects.RememberedEffect
import com.unifest.android.core.common.ObserveAsEvents
import com.unifest.android.core.common.PermissionDialogButtonType
import com.unifest.android.core.common.UiText
import com.unifest.android.core.common.extension.checkLocationPermission
import com.unifest.android.core.common.extension.checkNotificationPermission
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
import com.unifest.android.feature.map.preview.MapPreviewParameterProvider
import com.unifest.android.feature.map.viewmodel.ErrorType
import com.unifest.android.feature.map.viewmodel.MapUiAction
import com.unifest.android.feature.map.viewmodel.MapUiEvent
import com.unifest.android.feature.map.viewmodel.MapUiState
import com.unifest.android.feature.map.viewmodel.MapViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
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
    val activity = LocalActivity.current

    var isNotificationPermissionGranted by remember { mutableStateOf(activity?.checkNotificationPermission() ?: false) }
    var isLocationPermissionsGranted by remember { mutableStateOf(activity?.checkLocationPermission() ?: false) }

    LaunchedEffect(Unit) {
        snapshotFlow { activity?.checkLocationPermission() }
            .distinctUntilChanged()
            .collect { isGranted ->
                if (isGranted != null) {
                    isLocationPermissionsGranted = isGranted
                }
            }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { activity?.checkNotificationPermission() }
            .distinctUntilChanged()
            .collect { isGranted ->
                if (isGranted != null) {
                    isNotificationPermissionGranted = isGranted
                }
            }
    }

    val permissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            permissionsToRequest.forEach { permission ->
                when (permission) {
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION -> {
                        isLocationPermissionsGranted = permissions[permission] == true
                    }

                    Manifest.permission.POST_NOTIFICATIONS -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            isNotificationPermissionGranted = permissions[permission] == true
                        }
                    }
                }

                mapViewModel.onPermissionResult(
                    permission = permission,
                    isGranted = permissions[permission] == true,
                )
            }
        },
    )

    val settingsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            isNotificationPermissionGranted = activity?.checkNotificationPermission() ?: false
            isLocationPermissionsGranted = activity?.checkLocationPermission() ?: false

            mapViewModel.onPermissionResult(
                permission = Manifest.permission.ACCESS_COARSE_LOCATION,
                isGranted = isLocationPermissionsGranted,
            )

            mapViewModel.onPermissionResult(
                permission = Manifest.permission.ACCESS_FINE_LOCATION,
                isGranted = isLocationPermissionsGranted,
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                mapViewModel.onPermissionResult(
                    permission = Manifest.permission.POST_NOTIFICATIONS,
                    isGranted = isNotificationPermissionGranted,
                )
            }
        },
    )

    RememberedEffect(key1 = mapUiState.festivalInfo) {
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

            is MapUiEvent.NavigateToAppSetting -> {
                if (activity != null) {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", activity.packageName, null)
                    }
                    settingsLauncher.launch(intent)
                }
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

    if (mapUiState.isNotificationPermissionDialogVisible && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
        !isNotificationPermissionGranted && activity != null
    ) {
        PermissionDialog(
            permissionTextProvider = NotificationPermissionTextProvider(),
            isPermanentlyDeclined = !activity.shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS),
            onDismiss = {
                mapViewModel.onMapUiAction(
                    MapUiAction.OnPermissionDialogButtonClick(
                        buttonType = PermissionDialogButtonType.DISMISS,
                        permission = Manifest.permission.POST_NOTIFICATIONS,
                    ),
                )
            },
            navigateToAppSetting = {
                mapViewModel.onMapUiAction(
                    MapUiAction.OnPermissionDialogButtonClick(
                        buttonType = PermissionDialogButtonType.NAVIGATE_TO_APP_SETTING,
                        permission = Manifest.permission.POST_NOTIFICATIONS,
                    ),
                )
            },
            onConfirm = {
                mapViewModel.onMapUiAction(
                    MapUiAction.OnPermissionDialogButtonClick(
                        buttonType = PermissionDialogButtonType.CONFIRM,
                        permission = Manifest.permission.POST_NOTIFICATIONS,
                    ),
                )
            },
        )
    }

    if (mapUiState.isLocationPermissionDialogVisible && !isLocationPermissionsGranted && activity != null) {
        PermissionDialog(
            permissionTextProvider = LocationPermissionTextProvider(),
            isPermanentlyDeclined = !activity.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION),
            onDismiss = {
                mapViewModel.onMapUiAction(
                    MapUiAction.OnPermissionDialogButtonClick(
                        buttonType = PermissionDialogButtonType.DISMISS,
                        permission = Manifest.permission.ACCESS_FINE_LOCATION,
                    ),
                )
            },
            navigateToAppSetting = {
                mapViewModel.onMapUiAction(
                    MapUiAction.OnPermissionDialogButtonClick(
                        buttonType = PermissionDialogButtonType.NAVIGATE_TO_APP_SETTING,
                        permission = Manifest.permission.ACCESS_FINE_LOCATION,
                    ),
                )
            },
            onConfirm = {
                mapViewModel.onMapUiAction(
                    MapUiAction.OnPermissionDialogButtonClick(
                        buttonType = PermissionDialogButtonType.CONFIRM,
                        permission = Manifest.permission.ACCESS_FINE_LOCATION,
                    ),
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
        isClusteringEnabled = isClusteringEnabled,
    )
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

    RememberedEffect(key1 = mapUiState.festivalInfo) {
        if (mapUiState.festivalInfo.latitude != 0.0F && mapUiState.festivalInfo.longitude != 0.0F) {
            cameraPositionState.position = CameraPosition(
                LatLng(mapUiState.festivalInfo.latitude.toDouble(), mapUiState.festivalInfo.longitude.toDouble()), 15.7,
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
        val (selectedClusterCategory, setSelectedClusterCategory) = remember { mutableStateOf<String?>(null) }

        NaverMap(
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                locationTrackingMode = LocationTrackingMode.NoFollow,
                isNightModeEnabled = isSystemInDarkTheme(),
            ),
            uiSettings = MapUiSettings(
                isZoomControlEnabled = true,
                isScaleBarEnabled = false,
                isLogoClickEnabled = false,
                isLocationButtonEnabled = true,
            ),
            locationSource = rememberFusedLocationSource(),
        ) {
            PolygonOverlay(
                coords = uiState.outerPolygon,
                color = Color.Gray.copy(alpha = 0.3f),
                outlineColor = Color.Gray,
                outlineWidth = 1.dp,
                holes = uiState.innerPolyLines,
            )

            if (isClusteringEnabled) {
                var clusterManager by remember { mutableStateOf<Clusterer<BoothMapModel>?>(null) }
                val boothIds = uiState.filteredBoothList.map { it.id }
                DisposableMapEffect(boothIds, selectedClusterCategory) { map ->
                    clusterManager = null
                    clusterManager = Clusterer.ComplexBuilder<BoothMapModel>()
                        .minClusteringZoom(9)
                        .maxClusteringZoom(16)
                        .maxScreenDistance(200.0)
                        .thresholdStrategy { zoom -> if (zoom <= 11) 0.0 else 70.0 }
                        .distanceStrategy(object : DistanceStrategy {
                            private val defaultDistanceStrategy = DefaultDistanceStrategy()
                            override fun getDistance(zoom: Int, node1: Node, node2: Node): Double {
                                val model1 = when (val tag = node1.tag) {
                                    is BoothMapModel -> tag
                                    is List<*> -> tag.firstOrNull() as? BoothMapModel
                                    else -> null
                                }
                                val model2 = when (val tag = node2.tag) {
                                    is BoothMapModel -> tag
                                    is List<*> -> tag.firstOrNull() as? BoothMapModel
                                    else -> null
                                }
                                if (model1 == null || model2 == null) return -1.0
                                return if (zoom <= 9) -1.0
                                else if (model1.category == model2.category) {
                                    if (zoom <= 11) -1.0 else defaultDistanceStrategy.getDistance(zoom, node1, node2)
                                } else 10000.0
                            }
                        })
                        .tagMergeStrategy { cluster ->
                            val booths = cluster.children.flatMap { child ->
                                when (val tag = child.tag) {
                                    is BoothMapModel -> listOf(tag)
                                    is List<*> -> tag.filterIsInstance<BoothMapModel>()
                                    is Pair<*, *> -> (tag as? Pair<List<BoothMapModel>, String?>)?.first ?: emptyList()
                                    else -> emptyList()
                                }
                            }
                            val uniqueCategories = booths.map { it.category }.distinct()
                            val clusterCategory = uniqueCategories.singleOrNull()
                            Pair(booths, clusterCategory)
                        }
                        .markerManager(object : DefaultMarkerManager() {
                            override fun createMarker() = super.createMarker().apply {
                                subCaptionTextSize = 10f
                                subCaptionColor = android.graphics.Color.WHITE
                                subCaptionHaloColor = android.graphics.Color.TRANSPARENT
                            }
                        })
                        .clusterMarkerUpdater { info, marker ->
                            val tag = info.tag
                            val (booths, cat) = when (tag) {
                                is Pair<*, *> -> (tag.first as? List<BoothMapModel>).orEmpty() to (tag.second as? String)
                                is List<*> -> tag.filterIsInstance<BoothMapModel>() to null
                                else -> emptyList<BoothMapModel>() to null
                            }
                            if (info.size == 1) {
                                val booth = booths.firstOrNull()
                                if (booth != null) {
                                    val iconRes = getMarkerIconResource(booth.category, false)
                                    val markerView = makeClusterMarkerView(context, iconRes, 1, badgeVisible = false)
                                    markerView.measure(
                                        View.MeasureSpec.makeMeasureSpec(markerView.layoutParams.width, View.MeasureSpec.EXACTLY),
                                        View.MeasureSpec.makeMeasureSpec(markerView.layoutParams.height, View.MeasureSpec.EXACTLY),
                                    )
                                    markerView.layout(0, 0, markerView.measuredWidth, markerView.measuredHeight)
                                    val bitmap = createBitmap(markerView.width, markerView.height)
                                    val canvas = Canvas(bitmap)
                                    markerView.draw(canvas)
                                    marker.icon = OverlayImage.fromBitmap(bitmap)
                                    marker.anchor = PointF(1.0f, 0.0f)
                                    marker.captionText = ""
                                    marker.subCaptionText = ""
                                    marker.captionColor = android.graphics.Color.TRANSPARENT
                                    marker.captionHaloColor = android.graphics.Color.TRANSPARENT
                                    marker.subCaptionColor = android.graphics.Color.TRANSPARENT
                                    marker.subCaptionHaloColor = android.graphics.Color.TRANSPARENT
                                    marker.onClickListener = Overlay.OnClickListener {
                                        onMapUiAction(MapUiAction.OnSingleBoothMarkerClick(booth))
                                        true
                                    }
                                    return@clusterMarkerUpdater
                                }
                            }
                            val isSelected = (cat != null && cat == selectedClusterCategory)
                            val iconRes = cat?.let { getMarkerIconResource(it, isSelected) } ?: designR.drawable.ic_cluster
                            val markerView = makeClusterMarkerView(context, iconRes, info.size, badgeVisible = true)
                            markerView.measure(
                                View.MeasureSpec.makeMeasureSpec(markerView.layoutParams.width, View.MeasureSpec.EXACTLY),
                                View.MeasureSpec.makeMeasureSpec(markerView.layoutParams.height, View.MeasureSpec.EXACTLY),
                            )
                            markerView.layout(0, 0, markerView.measuredWidth, markerView.measuredHeight)
                            val bitmap = createBitmap(markerView.width, markerView.height)
                            val canvas = Canvas(bitmap)
                            markerView.draw(canvas)
                            marker.icon = OverlayImage.fromBitmap(bitmap)
                            marker.anchor = PointF(1.0f, 0.0f)
                            marker.captionText = ""
                            marker.subCaptionText = ""
                            marker.captionColor = android.graphics.Color.TRANSPARENT
                            marker.captionHaloColor = android.graphics.Color.TRANSPARENT
                            marker.subCaptionColor = android.graphics.Color.TRANSPARENT
                            marker.subCaptionHaloColor = android.graphics.Color.TRANSPARENT
                            marker.onClickListener = Overlay.OnClickListener {
                                setSelectedClusterCategory(cat)
                                onMapUiAction(MapUiAction.OnBoothMarkerClick(booths))
                                true
                            }
                        }
                        .leafMarkerUpdater { info, marker ->
                            marker.apply {
                                val booth = info.key as BoothMapModel
                                val iconRes = getMarkerIconResource(booth.category, false)
                                val markerView = makeClusterMarkerView(context, iconRes, 1, badgeVisible = false)
                                markerView.measure(
                                    View.MeasureSpec.makeMeasureSpec(markerView.layoutParams.width, View.MeasureSpec.EXACTLY),
                                    View.MeasureSpec.makeMeasureSpec(markerView.layoutParams.height, View.MeasureSpec.EXACTLY),
                                )
                                markerView.layout(0, 0, markerView.measuredWidth, markerView.measuredHeight)
                                val bitmap = createBitmap(markerView.width, markerView.height)
                                val canvas = Canvas(bitmap)
                                markerView.draw(canvas)
                                icon = OverlayImage.fromBitmap(bitmap)
                                captionText = ""
                                subCaptionText = ""
                                captionColor = android.graphics.Color.TRANSPARENT
                                captionHaloColor = android.graphics.Color.TRANSPARENT
                                subCaptionColor = android.graphics.Color.TRANSPARENT
                                subCaptionHaloColor = android.graphics.Color.TRANSPARENT
                                onClickListener = Overlay.OnClickListener {
                                    onMapUiAction(MapUiAction.OnSingleBoothMarkerClick(booth))
                                    true
                                }
                            }
                        }
                        .build()
                        .apply { this.map = map }

                    val boothListMap = uiState.filteredBoothList.associateWith { it }
                    clusterManager?.addAll(boothListMap)
                    onDispose {
                        clusterManager?.clear()
                        clusterManager = null
                    }
                }
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

fun getMarkerIconResource(category: String, isSelected: Boolean): Int {
    return when (MarkerCategory.fromString(category)) {
        MarkerCategory.BAR -> if (isSelected) designR.drawable.ic_marker_bar_selected else designR.drawable.ic_marker_bar
        MarkerCategory.FOOD -> if (isSelected) designR.drawable.ic_marker_food_selected else designR.drawable.ic_marker_food
        MarkerCategory.EVENT -> if (isSelected) designR.drawable.ic_marker_event_selected else designR.drawable.ic_marker_event
        MarkerCategory.NORMAL -> if (isSelected) designR.drawable.ic_marker_normal_selected else designR.drawable.ic_marker_normal
        MarkerCategory.MEDICAL -> if (isSelected) designR.drawable.ic_marker_medical_selected else designR.drawable.ic_marker_medical
        MarkerCategory.TOILET -> if (isSelected) designR.drawable.ic_marker_toilet_selected else designR.drawable.ic_marker_toilet
    }
}

fun makeClusterMarkerView(context: Context, iconRes: Int, count: Int, badgeVisible: Boolean = true): View {
    val sizePx = (context.resources.displayMetrics.density * 44).toInt()
    val badgeSizePx = (context.resources.displayMetrics.density * 21).toInt()

    val frame = FrameLayout(context).apply {
        layoutParams = FrameLayout.LayoutParams(sizePx, sizePx)
        setBackgroundResource(iconRes)
    }
    if (badgeVisible) {
        val badge = FrameLayout(context).apply {
            layoutParams = FrameLayout.LayoutParams(badgeSizePx, badgeSizePx, Gravity.END or Gravity.TOP)
            background = GradientDrawable().apply {
                shape = GradientDrawable.OVAL
                setColor(android.graphics.Color.BLACK)
            }
        }
        val text = TextView(context).apply {
            text = count.toString()
            setTextColor(android.graphics.Color.WHITE)
            gravity = Gravity.CENTER
            textSize = 13f
            layoutParams = FrameLayout.LayoutParams(badgeSizePx, badgeSizePx)
        }
        badge.addView(text)
        frame.addView(badge)
    }
    return frame
}
