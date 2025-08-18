package com.unifest.android.feature.booth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unifest.android.core.common.ObserveAsEvents
import com.unifest.android.core.designsystem.component.NetworkErrorDialog
import com.unifest.android.core.designsystem.component.ServerErrorDialog
import com.unifest.android.core.designsystem.theme.BoothTitle2
import com.unifest.android.core.designsystem.theme.Content1
import com.unifest.android.core.designsystem.theme.Content2
import com.unifest.android.core.designsystem.theme.Content8
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.ui.DevicePreview
import com.unifest.android.feature.booth.component.BoothItem
import com.unifest.android.feature.booth.preview.BoothPreviewParameterProvider
import com.unifest.android.feature.booth.viewmodel.BoothUiAction
import com.unifest.android.feature.booth.viewmodel.BoothUiEvent
import com.unifest.android.feature.booth.viewmodel.BoothUiState
import com.unifest.android.feature.booth.viewmodel.BoothViewModel
import com.unifest.android.feature.booth.viewmodel.ErrorType
import com.unifest.android.core.designsystem.R as designR

@Composable
internal fun BoothRoute(
    padding: PaddingValues,
    navigateToBoothDetail: (Long) -> Unit,
    viewModel: BoothViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ObserveAsEvents(flow = viewModel.uiEvent) { event ->
        when (event) {
            is BoothUiEvent.NavigateToBoothDetail -> navigateToBoothDetail(event.boothId)
        }
    }

    BoothScreen(
        padding = padding,
        uiState = uiState,
        onAction = viewModel::onAction,
    )
}

@Composable
internal fun BoothScreen(
    padding: PaddingValues,
    uiState: BoothUiState,
    onAction: (BoothUiAction) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(padding),
    ) {
        BoothContent(
            uiState = uiState,
            onAction = onAction,
            modifier = Modifier.padding(horizontal = 20.dp),
        )

        if (uiState.isServerErrorDialogVisible) {
            ServerErrorDialog(
                onRetryClick = { onAction(BoothUiAction.OnRetryClick(ErrorType.SERVER)) },
            )
        }

        if (uiState.isNetworkErrorDialogVisible) {
            NetworkErrorDialog(
                onRetryClick = { onAction(BoothUiAction.OnRetryClick(ErrorType.NETWORK)) },
            )
        }
    }
}

@Composable
internal fun BoothContent(
    uiState: BoothUiState,
    onAction: (BoothUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        item {
            Text(
                text = uiState.campusName,
                color = MaterialTheme.colorScheme.onBackground,
                style = Content8,
            )
        }
        item {
            Text(
                text = stringResource(R.string.booth_list),
                color = MaterialTheme.colorScheme.onBackground,
                style = BoothTitle2,
            )
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(id = R.string.total_booth_count, uiState.totalBoothCount),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = Content2,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = if (uiState.waitingAvailabilityChecked) designR.drawable.ic_check_waiting else designR.drawable.ic_check_waiting_unchecked,
                        ),
                        contentDescription = "check box icon",
                        tint = Color.Unspecified,
                        modifier = Modifier.clickable {
                            onAction(BoothUiAction.OnWaitingCheckBoxClick)
                        },
                    )
                    Text(
                        text = stringResource(id = R.string.waiting_availability),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = Content1,
                    )
                }
            }
        }
        items(
            items = uiState.showingBoothList,
            key = { booth -> booth.id },
        ) { booth ->
            BoothItem(
                booth = booth,
                modifier = Modifier
                    .padding(vertical = 1.dp)
                    .clickable {
                        onAction(BoothUiAction.OnBoothItemClick(booth.id))
                    },
            )
        }
    }
}

@DevicePreview
@Composable
private fun StampScreenPreview(
    @PreviewParameter(BoothPreviewParameterProvider::class)
    boothUiState: BoothUiState,
) {
    UnifestTheme {
        BoothScreen(
            padding = PaddingValues(),
            uiState = boothUiState,
            onAction = {},
        )
    }
}
