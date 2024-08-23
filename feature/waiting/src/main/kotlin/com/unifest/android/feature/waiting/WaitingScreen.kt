package com.unifest.android.feature.waiting

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unifest.android.core.common.ObserveAsEvents
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.component.UnifestOutlinedButton
import com.unifest.android.core.designsystem.component.WaitingCancelDialog
import com.unifest.android.core.designsystem.theme.BoothTitle2
import com.unifest.android.core.designsystem.theme.Content1
import com.unifest.android.core.designsystem.theme.Content2
import com.unifest.android.core.designsystem.theme.Content7
import com.unifest.android.core.designsystem.theme.DarkRed
import com.unifest.android.core.designsystem.theme.LightGrey100
import com.unifest.android.core.designsystem.theme.LightGrey700
import com.unifest.android.core.designsystem.theme.LightRed
import com.unifest.android.core.designsystem.theme.Title3
import com.unifest.android.core.designsystem.theme.Title4
import com.unifest.android.core.designsystem.theme.Title5
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.designsystem.theme.WaitingNumber
import com.unifest.android.core.designsystem.theme.WaitingNumber2
import com.unifest.android.core.designsystem.theme.WaitingNumber4
import com.unifest.android.core.model.MyWaitingModel
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
            .background(MaterialTheme.colorScheme.background)
            .padding(padding),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
        ) {
            item {
                Text(
                    text = stringResource(id = R.string.waiting_title),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = BoothTitle2,
                )
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(29.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                    colors = CardColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.primary,
                        disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = stringResource(id = R.string.waiting_my_waiting),
                            style = Title4,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(10.dp)) }
            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = stringResource(id = R.string.waiting_total_cases, 2),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = Content7,
                    )
                }
            }
            item { Spacer(modifier = Modifier.height(8.dp)) }

                itemsIndexed(
                    items = waitingUiState.myWaitingList,
                    key = { _, waitingItem -> waitingItem.waitingId },
                ) { _, waitingItem ->
                    Column {
                        Spacer(modifier = Modifier.height(16.dp))
                        WaitingInfoItem(
                            myWaitingModel = waitingItem,
                            onWaitingUiAction = onWaitingUiAction,
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    if (waitingUiState.myWaitingList.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "신청한 웨이팅이 없어요",
                    style = WaitingNumber4,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "주점/부스 구경하러 가기>",
                    style = Content2.copy(
                        textDecoration = TextDecoration.Underline
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }

    if (waitingUiState.isWaitingCancelDialogVisible) {
        WaitingCancelDialog(
            onCancelClick = { onWaitingUiAction(WaitingUiAction.OnWaitingCancelDialogCancelClick) },
            onConfirmClick = { onWaitingUiAction(WaitingUiAction.OnWaitingCancelDialogConfirmClick) },
        )
    }
}


@Composable
fun WaitingInfoItem(
    myWaitingModel: MyWaitingModel,
    onWaitingUiAction: (WaitingUiAction) -> Unit
) {
    Card(
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onBackground,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(id = R.string.waiting_current_order),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = Content1,
                )
                Row {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_location_green),
                        contentDescription = "Location Icon",
                        modifier = Modifier.align(Alignment.CenterVertically),
                        tint = Color.Unspecified,
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = myWaitingModel.boothName,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = Title3,
                    )
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Text(
                        text = myWaitingModel.waitingOrder.toString(),
                        style = WaitingNumber,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.alignByBaseline(),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(id = R.string.waiting_nth),
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.alignByBaseline(),
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(id = R.string.waiting_waiting_number),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = Content1,
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = myWaitingModel.waitingId.toString(),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = WaitingNumber2,
                    )
                    Spacer(modifier = Modifier.width(13.dp))
                    VerticalDivider(
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.height(13.dp),
                    )
                    Spacer(modifier = Modifier.width(13.dp))
                    Text(
                        text = stringResource(id = R.string.waiting_people),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = Content1,
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = myWaitingModel.partySize.toString(),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = WaitingNumber2,
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                UnifestOutlinedButton(
                    onClick = { onWaitingUiAction(WaitingUiAction.OnCancelWaitingClick(myWaitingModel.waitingId)) },
                    containerColor = LightGrey100,
                    borderColor = LightGrey100,
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = stringResource(id = R.string.waiting_cancel_waiting),
                        color = if (isSystemInDarkTheme()) DarkRed else LightRed,
                        style = Title5,
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                UnifestOutlinedButton(
                    onClick = { onWaitingUiAction(WaitingUiAction.OnCheckBoothDetailClick(myWaitingModel.boothId)) },
                    containerColor = LightGrey100,
                    borderColor = LightGrey100,
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = stringResource(id = R.string.waiting_booth_check),
                        color = LightGrey700,
                        style = Title5,
                    )
                }
            }
        }
    }
}

@DevicePreview
@Composable
fun WaitingScreenPreview() {
    UnifestTheme {
        WaitingScreen(
            padding = PaddingValues(),
            waitingUiState = WaitingUiState(),
            onWaitingUiAction = {},
        )
    }
}

@ComponentPreview
@Composable
fun WaitingScreenComponentPreview() {
    UnifestTheme {
        WaitingInfoItem(
            myWaitingModel = MyWaitingModel(
                boothId = 1L,
                waitingId = 1L,
                partySize = 2L,
                tel = "010-1234-5678",
                deviceId = "1234567890",
                createdAt = "2021-09-01T00:00:00",
                updatedAt = "2021-09-01T00:00:00",
                status = "waiting",
                waitingOrder = 1L,
                boothName = "Booth Name",
            ),
            onWaitingUiAction = {},
        )
    }
}
