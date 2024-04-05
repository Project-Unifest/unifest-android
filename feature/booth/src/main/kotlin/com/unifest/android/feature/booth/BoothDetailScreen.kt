package com.unifest.android.feature.booth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.component.TopAppBarNavigationType
import com.unifest.android.core.designsystem.component.UnifestOutlinedButton
import com.unifest.android.core.designsystem.component.UnifestTopAppBar
import com.unifest.android.core.designsystem.theme.BoothCaution
import com.unifest.android.core.designsystem.theme.BoothTitle1
import com.unifest.android.core.designsystem.theme.Content2
import com.unifest.android.core.designsystem.theme.MenuPrice
import com.unifest.android.core.designsystem.theme.MenuTitle
import com.unifest.android.core.designsystem.theme.Title2
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.domain.entity.BoothDetailEntity
import com.unifest.android.core.domain.entity.MenuEntity
import com.unifest.android.core.ui.DevicePreview
import com.unifest.android.feature.booth.viewmodel.BoothUiState
import com.unifest.android.feature.booth.viewmodel.BoothViewModel
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun BoothDetailRoute(
    onBackClick: () -> Unit,
    onNavigateToBoothLocation: () -> Unit,
    viewModel: BoothViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BoothDetailScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onNavigateToBoothLocation = onNavigateToBoothLocation,
    )
}

@Composable
fun BoothDetailScreen(
    uiState: BoothUiState,
    onBackClick: () -> Unit,
    onNavigateToBoothLocation: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        item {
            Box {
                BoothImage()
                UnifestTopAppBar(
                    navigationType = TopAppBarNavigationType.Back,
                    navigationIcon = R.drawable.ic_arrow_back_gray,
                    onNavigationClick = onBackClick,
                    containerColor = Color.Transparent,
                )
            }
        }
        item { Spacer(modifier = Modifier.height(30.dp)) }
        item {
            BoothDescription(
                name = uiState.boothDetailInfo.name,
                category = uiState.boothDetailInfo.category,
                description = uiState.boothDetailInfo.description,
                location = uiState.boothDetailInfo.location,
                onNavigateToBoothLocation = onNavigateToBoothLocation,
            )
        }
        item { Spacer(modifier = Modifier.height(20.dp)) }
        item { MenuText() }
        item { Spacer(modifier = Modifier.height(8.dp)) }
        items(
            items = uiState.boothDetailInfo.menus.toImmutableList(),
            key = { menu -> menu.id },
        ) { menu ->
            MenuItem(menu = menu)
        }
    }
}

@Composable
fun BoothImage() {
    Image(
        painter = painterResource(id = R.drawable.booth_image_example),
        modifier = Modifier
            .height(237.dp)
            .fillMaxWidth()
            .width(394.dp)
            .fillMaxWidth(),
        contentDescription = "Booth",
    )
}

@Composable
fun BoothDescription(
    name: String,
    category: String,
    description: String,
    location: String,
    onNavigateToBoothLocation: () -> Unit,
) {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = name,
                style = BoothTitle1,
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = category,
                style = BoothCaution,
                color = Color(0xFFF5687E),
                modifier = Modifier.align(Alignment.Bottom),
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = description,
            modifier = Modifier.padding(top = 8.dp),
            style = Content2.copy(lineHeight = 18.sp),
            // todo: 줄간격 논의
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 8.dp),
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_location_green),
                contentDescription = "location icon",
                tint = Color.Unspecified,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = location,
                style = Content2,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        UnifestOutlinedButton(
            onClick = onNavigateToBoothLocation,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = stringResource(id = R.string.booth_check_locaiton))
        }
    }
}

@Composable
fun MenuText() {
    Text(
        text = stringResource(id = R.string.booth_menu),
        modifier = Modifier.padding(start = 20.dp),
        style = Title2,
    )
}

@Composable
fun MenuItem(menu: MenuEntity) {
    Row(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.booth_menu_image_example),
            contentDescription = menu.name,
            modifier = Modifier.size(88.dp),
        )
        Spacer(modifier = Modifier.width(13.dp))
        Column(
            modifier = Modifier.align(Alignment.CenterVertically),
        ) {
            Text(
                text = menu.name,
                style = MenuTitle,
                color = Color(0xFF545454),
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = "${menu.price}원",
                style = MenuPrice,
            )
        }
    }
}

@DevicePreview
@Composable
fun BoothScreenPreview() {
    UnifestTheme {
        BoothDetailScreen(
            uiState = BoothUiState(
                boothDetailInfo = BoothDetailEntity(
                    id = 0L,
                    name = "컴공 주점",
                    category = "컴퓨터공학부 전용 부스",
                    description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                    location = "청심대 앞",
                    latitude = 37.54224856023523f,
                    longitude = 127.07605430700158f,
                    menus = listOf(
                        MenuEntity(1L, "모둠 사시미", 45000, ""),
                        MenuEntity(2L, "모둠 사시미", 45000, ""),
                        MenuEntity(3L, "모둠 사시미", 45000, ""),
                        MenuEntity(4L, "모둠 사시미", 45000, ""),
                    ),
                ),
            ),
            onBackClick = {},
            onNavigateToBoothLocation = {},
        )
    }
}
