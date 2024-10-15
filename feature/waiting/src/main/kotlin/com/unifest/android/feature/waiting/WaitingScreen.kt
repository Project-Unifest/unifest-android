package com.unifest.android.feature.waiting

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unifest.android.core.common.ObserveAsEvents
import com.unifest.android.core.common.extension.clickableSingle
import com.unifest.android.core.designsystem.theme.BoothTitle2
import com.unifest.android.core.designsystem.theme.Content2
import com.unifest.android.core.designsystem.theme.Content7
import com.unifest.android.core.designsystem.theme.Title4
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.designsystem.theme.WaitingNumber4
import com.unifest.android.core.ui.DevicePreview
import com.unifest.android.core.ui.component.NoShowWaitingCancelDialog
import com.unifest.android.core.ui.component.WaitingCancelDialog
import com.unifest.android.feature.waiting.component.WaitingInfoItem
import com.unifest.android.feature.waiting.preview.WaitingPreviewParameterProvider
import com.unifest.android.feature.waiting.viewmodel.WaitingUiAction
import com.unifest.android.feature.waiting.viewmodel.WaitingUiEvent
import com.unifest.android.feature.waiting.viewmodel.WaitingUiState
import com.unifest.android.feature.waiting.viewmodel.WaitingViewModel
import kotlinx.coroutines.delay

@Composable
internal fun WaitingRoute(
    padding: PaddingValues,
    popBackStack: () -> Unit,
    navigateToBoothDetail: (Long) -> Unit,
    viewModel: WaitingViewModel = hiltViewModel(),
) {
    val waitingUiState by viewModel.uiState.collectAsStateWithLifecycle()

    ObserveAsEvents(flow = viewModel.uiEvent) { event ->
        when (event) {
            is WaitingUiEvent.NavigateBack -> popBackStack()
            is WaitingUiEvent.NavigateToMap -> popBackStack()
            is WaitingUiEvent.NavigateToBoothDetail -> navigateToBoothDetail(event.boothId)
        }
    }
    LaunchedEffect(key1 = Unit) {
        viewModel.getMyWaitingList()
    }

    WaitingScreen(
        padding = padding,
        waitingUiState = waitingUiState,
        onWaitingUiAction = viewModel::onWaitingUiAction,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WaitingScreen(
    padding: PaddingValues,
    waitingUiState: WaitingUiState,
    onWaitingUiAction: (WaitingUiAction) -> Unit,
) {
    val pullToRefreshState = rememberPullToRefreshState()

    LaunchedEffect(key1 = pullToRefreshState.isRefreshing) {
        if (pullToRefreshState.isRefreshing) {
            delay(1000)
            onWaitingUiAction(WaitingUiAction.OnRefresh)
            pullToRefreshState.endRefresh()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
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
                        text = stringResource(id = R.string.waiting_total_cases, waitingUiState.myWaitingList.size),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = Content7,
                    )
                    Row(
                        modifier = Modifier.clickableSingle {
                            onWaitingUiAction(WaitingUiAction.OnRefresh)
                        },
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = stringResource(id = R.string.refresh),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = Content2,
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_refresh),
                            contentDescription = "refresh icon",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(8.dp)) }
            itemsIndexed(
                items = waitingUiState.myWaitingList.sortedBy { it.waitingId },
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
        if (pullToRefreshState.isRefreshing) {
            PullToRefreshContainer(
                modifier = Modifier.align(Alignment.TopCenter),
                state = pullToRefreshState,
            )
        }
    }
    if (waitingUiState.myWaitingList.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = stringResource(id = R.string.waiting_no_waiting),
                    style = WaitingNumber4,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.waiting_no_waiting_description),
                    style = Content2.copy(
                        textDecoration = TextDecoration.Underline,
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.clickable {
                        onWaitingUiAction(WaitingUiAction.OnLookForBoothClick)
                    },
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

    if (waitingUiState.isNoShowWaitingCancelDialogVisible) {
        NoShowWaitingCancelDialog(
            onCancelClick = { onWaitingUiAction(WaitingUiAction.OnNoShowWaitingCancelDialogCancelClick) },
            onConfirmClick = { onWaitingUiAction(WaitingUiAction.OnNoShowWaitingCancelDialogConfirmClick) },
        )
    }
}

@DevicePreview
@Composable
fun WaitingScreenPreview(
    @PreviewParameter(WaitingPreviewParameterProvider::class)
    waitingUiState: WaitingUiState,
) {
    UnifestTheme {
        WaitingScreen(
            padding = PaddingValues(),
            waitingUiState = waitingUiState,
            onWaitingUiAction = {},
        )
    }
}
