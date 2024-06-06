package com.unifest.android.feature.liked_booth

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unifest.android.core.common.ObserveAsEvents
import com.unifest.android.core.common.UiText
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.component.NetworkErrorDialog
import com.unifest.android.core.designsystem.component.ServerErrorDialog
import com.unifest.android.core.designsystem.component.TopAppBarNavigationType
import com.unifest.android.core.designsystem.component.UnifestTopAppBar
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.model.LikedBoothModel
import com.unifest.android.core.ui.DevicePreview
import com.unifest.android.core.ui.component.EmptyLikedBoothItem
import com.unifest.android.core.ui.component.LikedBoothItem
import com.unifest.android.feature.liked_booth.viewmodel.ErrorType
import com.unifest.android.feature.liked_booth.viewmodel.LikedBoothUiAction
import com.unifest.android.feature.liked_booth.viewmodel.LikedBoothUiEvent
import com.unifest.android.feature.liked_booth.viewmodel.LikedBoothUiState
import com.unifest.android.feature.liked_booth.viewmodel.LikedBoothViewModel
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun LikedBoothRoute(
    padding: PaddingValues,
    popBackStack: () -> Unit,
    navigateToBoothDetail: (Long) -> Unit,
    onShowSnackBar: (UiText) -> Unit,
    viewModel: LikedBoothViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ObserveAsEvents(flow = viewModel.uiEvent) { event ->
        when (event) {
            is LikedBoothUiEvent.NavigateBack -> popBackStack()
            is LikedBoothUiEvent.NavigateToBoothDetail -> navigateToBoothDetail(event.boothId)
            is LikedBoothUiEvent.ShowSnackBar -> onShowSnackBar(event.message)
        }
    }

    LikedBoothScreen(
        padding = padding,
        uiState = uiState,
        onAction = viewModel::onAction,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun LikedBoothScreen(
    padding: PaddingValues,
    uiState: LikedBoothUiState,
    onAction: (LikedBoothUiAction) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
    ) {
        Column {
            UnifestTopAppBar(
                navigationType = TopAppBarNavigationType.Back,
                onNavigationClick = { onAction(LikedBoothUiAction.OnBackClick) },
                title = stringResource(id = R.string.liked_booth_title),
                elevation = 8.dp,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp),
                    )
                    .padding(top = 13.dp, bottom = 5.dp),
            )
            if (uiState.likedBooths.isEmpty()) {
                EmptyLikedBoothItem(modifier = Modifier.fillMaxSize())
            }
            LazyColumn {
                itemsIndexed(
                    uiState.likedBooths,
                    key = { _, booth -> booth.id },
                ) { index, booth ->
                    LikedBoothItem(
                        booth = booth,
                        index = index,
                        totalCount = uiState.likedBooths.size,
                        deleteLikedBooth = { onAction(LikedBoothUiAction.OnToggleBookmark(booth)) },
                        modifier = Modifier
                            .clickable {
                                onAction(LikedBoothUiAction.OnLikedBoothItemClick(booth.id))
                            }
                            .animateItemPlacement(
                                animationSpec = tween(
                                    durationMillis = 500,
                                    easing = LinearOutSlowInEasing,
                                ),
                            ),
                    )
                }
            }
        }
        if (uiState.isServerErrorDialogVisible) {
            ServerErrorDialog(
                onRetryClick = { onAction(LikedBoothUiAction.OnRetryClick(ErrorType.SERVER)) },
            )
        }

        if (uiState.isNetworkErrorDialogVisible) {
            NetworkErrorDialog(
                onRetryClick = { onAction(LikedBoothUiAction.OnRetryClick(ErrorType.NETWORK)) },
            )
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
                likedBooths = persistentListOf(
                    LikedBoothModel(
                        id = 1,
                        name = "부스 이름",
                        category = "음식",
                        description = "부스 설명",
                        location = "부스 위치",
                        warning = "학과 전용 부스",
                    ),
                    LikedBoothModel(
                        id = 2,
                        name = "부스 이름",
                        category = "음식",
                        description = "부스 설명",
                        location = "부스 위치",
                        warning = "학과 전용 부스",
                    ),
                    LikedBoothModel(
                        id = 3,
                        name = "부스 이름",
                        category = "음식",
                        description = "부스 설명",
                        location = "부스 위치",
                        warning = "학과 전용 부스",
                    ),
                    LikedBoothModel(
                        id = 4,
                        name = "부스 이름",
                        category = "음식",
                        description = "부스 설명",
                        location = "부스 위치",
                        warning = "학과 전용 부스",
                    ),
                    LikedBoothModel(
                        id = 5,
                        name = "부스 이름",
                        category = "음식",
                        description = "부스 설명",
                        location = "부스 위치",
                        warning = "학과 전용 부스",
                    ),
                    LikedBoothModel(
                        id = 6,
                        name = "부스 이름",
                        category = "음식",
                        description = "부스 설명",
                        location = "부스 위치",
                        warning = "학과 전용 부스",
                    ),
                ),
            ),
            onAction = {},
        )
    }
}
