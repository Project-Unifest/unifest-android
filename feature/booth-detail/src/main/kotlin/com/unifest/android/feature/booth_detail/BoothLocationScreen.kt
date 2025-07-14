package com.unifest.android.feature.booth_detail

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.PolygonOverlay
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource
import com.naver.maps.map.compose.rememberMarkerState
import com.unifest.android.core.common.ObserveAsEvents
import com.unifest.android.core.common.PermissionDialogButtonType
import com.unifest.android.core.common.extension.checkLocationPermission
import com.unifest.android.core.designsystem.MarkerCategory
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.ui.DevicePreview
import com.unifest.android.core.ui.component.LocationPermissionTextProvider
import com.unifest.android.core.ui.component.PermissionDialog
import com.unifest.android.feature.booth_detail.component.BoothLocationAppBar
import com.unifest.android.feature.booth_detail.preview.BoothDetailPreviewParameterProvider
import com.unifest.android.feature.booth_detail.viewmodel.BoothUiAction
import com.unifest.android.feature.booth_detail.viewmodel.BoothUiEvent
import com.unifest.android.feature.booth_detail.viewmodel.BoothUiState
import com.unifest.android.feature.booth_detail.viewmodel.BoothViewModel
import kotlinx.coroutines.flow.distinctUntilChanged

val permissionsToRequest = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)

@Composable
internal fun BoothLocationRoute(
    popBackStack: () -> Unit,
    viewModel: BoothViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val activity = LocalActivity.current
    var isLocationPermissionsGranted by remember { mutableStateOf(activity?.checkLocationPermission() ?: false) }

    LaunchedEffect(Unit) {
        viewModel.requestLocationPermission()
    }

    LaunchedEffect(Unit) {
        snapshotFlow { activity?.checkLocationPermission() }
            .distinctUntilChanged()
            .collect { isGranted ->
                if (isGranted != null) {
                    isLocationPermissionsGranted = isGranted
                }
            }
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            permissionsToRequest.forEach { permission ->
                when (permission) {
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION -> {
                        isLocationPermissionsGranted = permissions[permission] == true
                    }
                }

                viewModel.onPermissionResult(
                    permission = permission,
                    isGranted = permissions[permission] == true,
                )
            }
        },
    )

    val settingsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            // 설정에서 돌아왔을 때 권한 상태를 다시 확인
            isLocationPermissionsGranted = activity?.checkLocationPermission() ?: false

            viewModel.onPermissionResult(
                permission = Manifest.permission.ACCESS_COARSE_LOCATION,
                isGranted = isLocationPermissionsGranted,
            )

            viewModel.onPermissionResult(
                permission = Manifest.permission.ACCESS_FINE_LOCATION,
                isGranted = isLocationPermissionsGranted,
            )
        },
    )

    ObserveAsEvents(flow = viewModel.uiEvent) { event ->
        when (event) {
            is BoothUiEvent.NavigateBack -> popBackStack()
            is BoothUiEvent.NavigateToAppSetting -> {
                if (activity != null) {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", activity.packageName, null)
                    }
                    settingsLauncher.launch(intent)
                }
            }

            is BoothUiEvent.RequestPermission -> {
                locationPermissionLauncher.launch(permissionsToRequest)
            }

            else -> {}
        }
    }

    if (uiState.isLocationPermissionDialogVisible && !isLocationPermissionsGranted && activity != null) {
        PermissionDialog(
            permissionTextProvider = LocationPermissionTextProvider(),
            isPermanentlyDeclined = !activity.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION),
            onDismiss = {
                viewModel.onAction(
                    BoothUiAction.OnPermissionDialogButtonClick(
                        buttonType = PermissionDialogButtonType.DISMISS,
                        permission = Manifest.permission.ACCESS_FINE_LOCATION,
                    ),
                )
            },
            navigateToAppSetting = {
                viewModel.onAction(
                    BoothUiAction.OnPermissionDialogButtonClick(
                        buttonType = PermissionDialogButtonType.NAVIGATE_TO_APP_SETTING,
                        permission = Manifest.permission.ACCESS_FINE_LOCATION,
                    ),
                )
            },
            onConfirm = {
                viewModel.onAction(
                    BoothUiAction.OnPermissionDialogButtonClick(
                        buttonType = PermissionDialogButtonType.CONFIRM,
                        permission = Manifest.permission.ACCESS_FINE_LOCATION,
                    ),
                )
            },
        )
    }

    BoothLocationScreen(
        uiState = uiState,
        onAction = viewModel::onAction,
    )
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
internal fun BoothLocationScreen(
    uiState: BoothUiState,
    onAction: (BoothUiAction) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition(LatLng(uiState.boothDetailInfo.latitude.toDouble(), uiState.boothDetailInfo.longitude.toDouble()), 15.7)
        }
        NaverMap(
            cameraPositionState = cameraPositionState,
            modifier = Modifier.fillMaxSize(),
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

            Marker(
                state = rememberMarkerState(
                    position = LatLng(
                        uiState.boothDetailInfo.latitude.toDouble(),
                        uiState.boothDetailInfo.longitude.toDouble(),
                    ),
                ),
                icon = MarkerCategory.fromString(uiState.boothDetailInfo.category).getMarkerIcon(false),
                onClick = { true },
            )
        }

        BoothLocationAppBar(
            onBackClick = { onAction(BoothUiAction.OnBackClick) },
            boothName = uiState.boothDetailInfo.name,
            boothLocation = uiState.boothDetailInfo.location,
            modifier = Modifier.align(Alignment.TopCenter),
        )
    }
}

@DevicePreview
@Composable
private fun BoothLocationScreenPreview(
    @PreviewParameter(BoothDetailPreviewParameterProvider::class)
    boothUiState: BoothUiState,
) {
    UnifestTheme {
        BoothLocationScreen(
            uiState = boothUiState,
            onAction = {},
        )
    }
}
