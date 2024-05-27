package com.unifest.android.feature.waiting

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
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
import com.unifest.android.core.designsystem.theme.BoothTitle0
import com.unifest.android.core.designsystem.theme.BoothTitle2
import com.unifest.android.core.designsystem.theme.MainColor
import com.unifest.android.core.designsystem.theme.Title4
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
        LazyColumn {
            item { Text("웨이팅", style = BoothTitle2) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(29.dp),
                    border = BorderStroke(1.dp, Color(0xFFF5687E)),
                    colors = CardColors(
                        containerColor = Color(0xFFFFF0F3),
                        contentColor = Color(0xFFF5687E),
                        disabledContainerColor = Color.White,
                        disabledContentColor = Color(0xFF585858),
                    ),
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "나의 웨이팅",
                            style = Title4,
                            color = Color(0xFFF5687E),
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 50.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "신청한 웨이팅이 없어요",
                style = Title4.copy(fontSize = 16.sp),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "주점/부스 구경하러 가기>",
                style = Title4.copy(fontSize = 14.sp),
                color = Color.Gray
            )
        }

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
