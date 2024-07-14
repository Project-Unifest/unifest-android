package com.unifest.android.feature.booth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.PolygonOverlay
import com.naver.maps.map.compose.rememberCameraPositionState
import com.unifest.android.core.designsystem.MarkerCategory
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.theme.BoothLocation
import com.unifest.android.core.designsystem.theme.Title1
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.model.BoothDetailModel
import com.unifest.android.feature.booth.viewmodel.BoothUiState
import com.unifest.android.feature.booth.viewmodel.BoothViewModel
import kotlinx.collections.immutable.persistentListOf

@Composable
fun BoothLocationRoute(
    onBackClick: () -> Unit,
    viewModel: BoothViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BoothLocationScreen(
        uiState = uiState,
        onBackClick = onBackClick,
    )
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun BoothLocationScreen(
    uiState: BoothUiState,
    onBackClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition(LatLng(37.5420, 127.07673671067072), 14.8)
        }
        NaverMap(
            cameraPositionState = cameraPositionState,
            modifier = Modifier.fillMaxSize(),
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
            Marker(
                state = MarkerState(position = LatLng(uiState.boothDetailInfo.latitude.toDouble(), uiState.boothDetailInfo.longitude.toDouble())),
                icon = MarkerCategory.fromString(uiState.boothDetailInfo.category).getMarkerIcon(false),
                onClick = { true },
            )
        }

        BoothLocationAppBar(
            onBackClick = onBackClick,
            boothName = uiState.boothDetailInfo.name,
            boothLocation = uiState.boothDetailInfo.location,
            modifier = Modifier.align(Alignment.TopCenter),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoothLocationAppBar(
    onBackClick: () -> Unit,
    boothName: String,
    boothLocation: String,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
            )
            .padding(vertical = 8.dp, horizontal = 12.dp),
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back_gray),
                    contentDescription = "뒤로 가기",
                )
            }
        },
        title = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = boothName,
                    style = Title1,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = boothLocation,
                    style = BoothLocation,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(Color.White),
        actions = {
            Spacer(modifier = Modifier.width(48.dp))
        },
    )
}

@Preview
@Composable
fun BoothLocationScreenPreview() {
    UnifestTheme {
        BoothLocationScreen(
            uiState = BoothUiState(
                boothDetailInfo = BoothDetailModel(
                    id = 0L,
                    name = "컴공 주점",
                    category = "컴퓨터공학부 전용 부스",
                    description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                    location = "청심대 앞",
                    latitude = 37.54224856023523f,
                    longitude = 127.07605430700158f,
                ),
            ),
            onBackClick = {},
        )
    }
}

@Preview
@Composable
fun BoothLocationAppBarPreview() {
    UnifestTheme {
        BoothLocationAppBar(
            onBackClick = {},
            boothName = "컴공 주점",
            boothLocation = "청심대 앞",
        )
    }
}
