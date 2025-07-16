package com.unifest.android.feature.booth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unifest.android.core.common.ObserveAsEvents
import com.unifest.android.core.designsystem.component.NetworkErrorDialog
import com.unifest.android.core.designsystem.component.ServerErrorDialog
import com.unifest.android.core.designsystem.theme.BoothTitle2
import com.unifest.android.core.designsystem.theme.Content8
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.ui.DevicePreview
import com.unifest.android.feature.booth.preview.BoothPreviewParameterProvider
import com.unifest.android.feature.booth.viewmodel.BoothUiAction
import com.unifest.android.feature.booth.viewmodel.BoothUiEvent
import com.unifest.android.feature.booth.viewmodel.BoothUiState
import com.unifest.android.feature.booth.viewmodel.BoothViewModel
import com.unifest.android.feature.booth.viewmodel.ErrorType

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

    BoothContent(
        padding = padding,
        uiState = uiState,
        onAction = viewModel::onAction,
    )

}

@Composable
internal fun BoothContent(
    padding: PaddingValues,
    uiState: BoothUiState,
    onAction: (BoothUiAction) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
    ) {
        Column { }


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

@DevicePreview
@Composable
private fun StampScreenPreview(
    @PreviewParameter(BoothPreviewParameterProvider::class)
    boothUiState: BoothUiState,
) {
    UnifestTheme {
        BoothContent(
            padding = PaddingValues(),
            uiState = boothUiState,
            onAction = {},
        )
    }
}

