package com.unifest.android.feature.waiting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unifest.android.core.common.ObserveAsEvents
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.ui.DevicePreview
import com.unifest.android.feature.waiting.viewmodel.WaitingUiAction
import com.unifest.android.feature.waiting.viewmodel.WaitingUiEvent
import com.unifest.android.feature.waiting.viewmodel.WaitingUiState
import com.unifest.android.feature.waiting.viewmodel.WaitingViewModel

@Composable
internal fun WaitingRoute(
    padding: PaddingValues,
    popBackStack: () -> Unit,
    viewModel: WaitingViewModel = hiltViewModel(),
) {
    val waitingUiState by viewModel.uiState.collectAsStateWithLifecycle()

    ObserveAsEvents(flow = viewModel.uiEvent) { event ->
        when (event) {
            is WaitingUiEvent.NavigateBack -> popBackStack()
        }
    }

    WaitingScreen(
        padding = padding,
        waitingUiState = waitingUiState,
        onWaitingUiAction = viewModel::onWaitingUiAction,
    )
}

@Composable
internal fun WaitingScreen(
    padding: PaddingValues,
    waitingUiState: WaitingUiState,
    onWaitingUiAction: (WaitingUiAction) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
    ) {

    }
}

@DevicePreview
@Composable
fun WaitingScreenPreview() {
    UnifestTheme {
        WaitingScreen(
            padding = PaddingValues(16.dp),
            waitingUiState = WaitingUiState(),
            onWaitingUiAction = {},
        )
    }
}
