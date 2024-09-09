package com.unifest.android.feature.stamp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unifest.android.core.common.ObserveAsEvents
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.component.UnifestButton
import com.unifest.android.core.designsystem.theme.BoothTitle2
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.ui.DevicePreview
import com.unifest.android.feature.stamp.viewmodel.StampUiState
import com.unifest.android.feature.waiting.viewmodel.StampUiAction
import com.unifest.android.feature.stamp.viewmodel.StampUiEvent
import com.unifest.android.feature.waiting.viewmodel.StampViewModel

@Composable
internal fun StampRoute(
    padding: PaddingValues,
    popBackStack: () -> Unit,
    viewModel: StampViewModel = hiltViewModel(),
) {
    val waitingUiState by viewModel.uiState.collectAsStateWithLifecycle()

    ObserveAsEvents(flow = viewModel.uiEvent) { event ->
        when (event) {
            is StampUiEvent.NavigateBack -> popBackStack()
            else -> {}
        }
    }

    StampScreen(
        padding = padding,
        uiState = waitingUiState,
        onAction = viewModel::onAction,
    )
}

@Composable
internal fun StampScreen(
    padding: PaddingValues,
    uiState: StampUiState,
    onAction: (StampUiAction) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(padding),
    ) {
        Column {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .height(if (uiState.stampList.isEmpty()) 0.dp else (((uiState.stampList.size - 1) / 3 + 1) * 140).dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 48.dp),
            ) {
                item {
                    Row {
                        Column {
                            Text(
                                text = "한국교통대학교",
                            )
                            Text(
                                text = "지금까지 모은 스탬프",
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        UnifestButton(
                            onClick = {},
                        ) {
                            Text(
                                text = "스탬프 받기",
                            )
                        }
                    }
                }
                item {
                    Row {
                        Text(
                            text = "1 / 12 개",
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Row {
                            Text(
                                text = "새로고침",
                            )
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_refresh),
                                contentDescription = "refresh icon",
                                tint = Color.Unspecified,
                            )
                        }
                    }
                }
                items(
                    count = uiState.stampList.size,
                    key = { index -> uiState.stampList[index].boothId },
                ) { index ->
                    Box {}
                }
            }
            Text(
                text = stringResource(id = R.string.waiting_title),
                color = MaterialTheme.colorScheme.onBackground,
                style = BoothTitle2,
            )
        }
    }
    if (uiState.isWaitingCancelDialogVisible) {

    }
}

@DevicePreview
@Composable
fun StampScreenPreview() {
    UnifestTheme {
        StampScreen(
            padding = PaddingValues(),
            uiState = StampUiState(),
            onAction = {},
        )
    }
}
