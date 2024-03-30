package com.unifest.android.feature.map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
//import com.google.android.gms.maps.model.BitmapDescriptorFactory
//import com.google.android.gms.maps.model.CameraPosition
//import com.google.android.gms.maps.model.LatLng
//import com.google.maps.android.compose.GoogleMap
//import com.google.maps.android.compose.MapUiSettings
//import com.google.maps.android.compose.Marker
//import com.google.maps.android.compose.MarkerState
//import com.google.maps.android.compose.rememberCameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.ui.DevicePreview
import com.unifest.android.feature.map.viewmodel.MapUiState
import com.unifest.android.feature.map.viewmodel.MapViewModel

@Composable
internal fun MapRoute(
    padding: PaddingValues,
    viewModel: MapViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MapScreen(
        padding = padding,
        uiState = uiState,
    )
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
internal fun MapScreen(
    padding: PaddingValues,
    uiState: MapUiState,
) {
//    val uiSettings = remember {
//        MapUiSettings(zoomControlsEnabled = false)
//    }

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

        NaverMap(cameraPositionState = cameraPositionState) {
            data class BoothSpot(val latitude: Double, val longitude: Double)

            val boothSpots = listOf(
                BoothSpot(37.54053013863604, 127.07505652524804),
                BoothSpot(37.54111712868565, 127.07839319326257),
                BoothSpot(37.5414744247141, 127.07779237844323),
                BoothSpot(37.54224856023523, 127.07605430700158),
                BoothSpot(37.54003672313541, 127.07653710462426),
                BoothSpot(37.53998567996623, 37.53998567996623),
                BoothSpot(37.54152546686414, 127.07353303052759),
                BoothSpot(37.54047909580466, 127.07398364164209),
            )

            // boothSpots 리스트에서 마커 생성
            boothSpots.forEach { spot ->
                Marker(
                    state = MarkerState(position = LatLng(spot.latitude, spot.longitude)),
                    icon = OverlayImage.fromResource(com.unifest.android.core.designsystem.R.drawable.ic_general)
                )
            }
        }
    }
}

//        GoogleMap(
//            modifier = Modifier.fillMaxSize(),
//            cameraPositionState = cameraPositionState,
//            properties = uiState.properties,
//            uiSettings = uiSettings,
//            onMapClick = {},
//        ) {
//            uiState.boothSpots.forEach { spot ->
//                Marker(
//                    state = MarkerState(
//                        position = LatLng(spot.lat, spot.lng),
//                    ),
//                    title = "Booth spot (${spot.lat}, ${spot.lng})",
//                    snippet = "click",
//                    onClick = { true },
//                    icon = BitmapDescriptorFactory.defaultMarker(
//                        BitmapDescriptorFactory.HUE_GREEN,
//                    ),
//                )
//            }
//        }

@DevicePreview
@Composable
fun MapScreenPreview() {
    UnifestTheme {
        MapScreen(
            padding = PaddingValues(0.dp),
            uiState = MapUiState(),
        )
    }
}
