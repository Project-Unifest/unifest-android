package com.unifest.android.feature.liked_booth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.component.TopAppBarNavigationType
import com.unifest.android.core.designsystem.component.UnifestTopAppBar
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.domain.entity.BoothDetailEntity
import com.unifest.android.core.domain.entity.MenuEntity
import com.unifest.android.core.ui.DevicePreview
import com.unifest.android.core.ui.component.EmptyLikedBoothItem
import com.unifest.android.core.ui.component.LikedBoothItem
import com.unifest.android.feature.liked_booth.viewmodel.LikedBoothUiState
import com.unifest.android.feature.liked_booth.viewmodel.LikedBoothViewModel
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun LikedBoothRoute(
    padding: PaddingValues,
    onBackClick: () -> Unit,
    viewModel: LikedBoothViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LikedBoothScreen(
        padding = padding,
        uiState = uiState,
        onBackClick = onBackClick,
        deleteLikedBooth = viewModel::deleteLikedBooth,
    )
}

@Composable
internal fun LikedBoothScreen(
    padding: PaddingValues,
    uiState: LikedBoothUiState,
    onBackClick: () -> Unit,
    deleteLikedBooth: (BoothDetailEntity) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
    ) {
        Column {
            UnifestTopAppBar(
                navigationType = TopAppBarNavigationType.Back,
                onNavigationClick = onBackClick,
                title = stringResource(id = R.string.liked_booth_title),
                elevation = 8.dp,
                modifier = Modifier
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp),
                    )
                    .padding(top = 13.dp, bottom = 5.dp),
            )
            if (uiState.likedBoothList.isEmpty()) {
                EmptyLikedBoothItem(modifier = Modifier.fillMaxSize())
            }
            LazyColumn {
                itemsIndexed(
                    uiState.likedBoothList,
                    key = { _, booth -> booth.id },
                ) { index, booth ->
                    LikedBoothItem(
                        booth = booth,
                        index = index,
                        totalCount = uiState.likedBoothList.size,
                        deleteLikedBooth = { deleteLikedBooth(booth) },
                    )
                }
            }
        }
    }
}

@DevicePreview
@Composable
fun LikedBoothScreenPreview() {
    UnifestTheme {
        LikedBoothScreen(
            padding = PaddingValues(),
            uiState = LikedBoothUiState(
                likedBoothList = persistentListOf(
                    BoothDetailEntity(
                        id = 1,
                        name = "부스 이름",
                        category = "음식",
                        description = "부스 설명",
                        warning = "주의사항",
                        location = "부스 위치",
                        latitude = 0.0f,
                        longitude = 0.0f,
                        menus = listOf(
                            MenuEntity(
                                id = 1,
                                name = "메뉴 이름",
                                price = 1000,
                                imgUrl = "",
                            ),
                        ),
                    ),
                    BoothDetailEntity(
                        id = 2,
                        name = "부스 이름",
                        category = "음식",
                        description = "부스 설명",
                        warning = "주의사항",
                        location = "부스 위치",
                        latitude = 0.0f,
                        longitude = 0.0f,
                        menus = listOf(
                            MenuEntity(
                                id = 1,
                                name = "메뉴 이름",
                                price = 1000,
                                imgUrl = "",
                            ),
                        ),
                    ),
                    BoothDetailEntity(
                        id = 3,
                        name = "부스 이름",
                        category = "음식",
                        description = "부스 설명",
                        warning = "주의사항",
                        location = "부스 위치",
                        latitude = 0.0f,
                        longitude = 0.0f,
                        menus = listOf(
                            MenuEntity(
                                id = 1,
                                name = "메뉴 이름",
                                price = 1000,
                                imgUrl = "",
                            ),
                        ),
                    ),
                    BoothDetailEntity(
                        id = 4,
                        name = "부스 이름",
                        category = "음식",
                        description = "부스 설명",
                        warning = "주의사항",
                        location = "부스 위치",
                        latitude = 0.0f,
                        longitude = 0.0f,
                        menus = listOf(
                            MenuEntity(
                                id = 1,
                                name = "메뉴 이름",
                                price = 1000,
                                imgUrl = "",
                            ),
                        ),
                    ),
                    BoothDetailEntity(
                        id = 5,
                        name = "부스 이름",
                        category = "음식",
                        description = "부스 설명",
                        warning = "주의사항",
                        location = "부스 위치",
                        latitude = 0.0f,
                        longitude = 0.0f,
                        menus = listOf(
                            MenuEntity(
                                id = 1,
                                name = "메뉴 이름",
                                price = 1000,
                                imgUrl = "",
                            ),
                        ),
                    ),
                    BoothDetailEntity(
                        id = 6,
                        name = "부스 이름",
                        category = "음식",
                        description = "부스 설명",
                        warning = "주의사항",
                        location = "부스 위치",
                        latitude = 0.0f,
                        longitude = 0.0f,
                        menus = listOf(
                            MenuEntity(
                                id = 1,
                                name = "메뉴 이름",
                                price = 1000,
                                imgUrl = "",
                            ),
                        ),
                    ),
                ),
            ),
            onBackClick = {},
            deleteLikedBooth = {},
        )
    }
}
