package com.unifest.android.feature.booth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unifest.android.core.common.ObserveAsEvents
import com.unifest.android.core.designsystem.component.NetworkErrorDialog
import com.unifest.android.core.designsystem.component.ServerErrorDialog
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
        Column {  }


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
