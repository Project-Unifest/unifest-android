package com.unifest.android.feature.booth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unifest.android.core.common.ObserveAsEvents
import com.unifest.android.core.common.extension.findActivity
import com.unifest.android.core.common.extension.navigateToAppSetting
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.component.LoadingWheel
import com.unifest.android.core.designsystem.component.NetworkErrorDialog
import com.unifest.android.core.designsystem.component.NetworkImage
import com.unifest.android.core.designsystem.component.ServerErrorDialog
import com.unifest.android.core.designsystem.component.TopAppBarNavigationType
import com.unifest.android.core.designsystem.component.UnifestHorizontalDivider
import com.unifest.android.core.designsystem.component.UnifestSnackBar
import com.unifest.android.core.designsystem.component.UnifestTopAppBar
import com.unifest.android.core.designsystem.component.WaitingConfirmDialog
import com.unifest.android.core.designsystem.component.WaitingDialog
import com.unifest.android.core.designsystem.component.WaitingPinDialog
import com.unifest.android.core.designsystem.theme.Content3
import com.unifest.android.core.designsystem.theme.DarkGrey100
import com.unifest.android.core.designsystem.theme.Title2
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.model.BoothDetailModel
import com.unifest.android.core.ui.DevicePreview
import com.unifest.android.feature.booth.component.BoothBottomBar
import com.unifest.android.feature.booth.component.BoothDescription
import com.unifest.android.feature.booth.component.MenuItem
import com.unifest.android.feature.booth.preview.BoothDetailUiStatePreviewParameterProvider
import com.unifest.android.feature.booth.viewmodel.BoothUiAction
import com.unifest.android.feature.booth.viewmodel.BoothUiEvent
import com.unifest.android.feature.booth.viewmodel.BoothUiState
import com.unifest.android.feature.booth.viewmodel.BoothViewModel
import com.unifest.android.feature.booth.viewmodel.ErrorType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tech.thdev.compose.exteions.system.ui.controller.rememberExSystemUiController

private const val SnackBarDuration = 1000L

@Composable
internal fun BoothDetailRoute(
    padding: PaddingValues,
    onBackClick: () -> Unit,
    navigateToBoothLocation: () -> Unit,
    viewModel: BoothViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val systemUiController = rememberExSystemUiController()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackBarState = remember { SnackbarHostState() }
    val isDarkTheme = isSystemInDarkTheme()
    val uriHandler = LocalUriHandler.current
    val activity = context.findActivity()

    DisposableEffect(systemUiController) {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = false,
        )
        onDispose {
            systemUiController.setStatusBarColor(
                color = if (isDarkTheme) DarkGrey100 else Color.White,
                darkIcons = !isDarkTheme,
            )
        }
    }

    ObserveAsEvents(flow = viewModel.uiEvent) { event ->
        when (event) {
            is BoothUiEvent.NavigateBack -> onBackClick()
            is BoothUiEvent.NavigateToBoothLocation -> navigateToBoothLocation()
            is BoothUiEvent.NavigateToPrivatePolicy -> uriHandler.openUri(BuildConfig.UNIFEST_PRIVATE_POLICY_URL)
            is BoothUiEvent.NavigateToThirdPartyPolicy -> uriHandler.openUri(BuildConfig.UNIFEST_THIRD_PARTY_POLICY_URL)
            is BoothUiEvent.ShowSnackBar -> {
                scope.launch {
                    val job = launch {
                        snackBarState.showSnackbar(
                            message = event.message.asString(context),
                            duration = SnackbarDuration.Short,
                        )
                    }
                    delay(SnackBarDuration)
                    job.cancel()
                }
            }

            is BoothUiEvent.ShowToast -> Toast.makeText(context, event.message.asString(context), Toast.LENGTH_SHORT).show()
            is BoothUiEvent.NavigateToAppSetting -> activity.navigateToAppSetting()
        }
    }

    BoothDetailScreen(
        padding = padding,
        uiState = uiState,
        snackBarState = snackBarState,
        onAction = viewModel::onAction,
    )
}

@Composable
fun BoothDetailScreen(
    padding: PaddingValues,
    uiState: BoothUiState,
    snackBarState: SnackbarHostState,
    onAction: (BoothUiAction) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        BoothDetailContent(
            uiState = uiState,
            onAction = onAction,
            modifier = Modifier.padding(bottom = 116.dp),
        )
        UnifestTopAppBar(
            navigationType = TopAppBarNavigationType.Back,
            navigationIconRes = R.drawable.ic_arrow_back_gray,
            containerColor = Color.Transparent,
            onNavigationClick = { onAction(BoothUiAction.OnBackClick) },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(padding),
        )
        BoothBottomBar(
            isBookmarked = uiState.isLiked,
            bookmarkCount = uiState.boothDetailInfo.likes,
            isWaitingEnable = uiState.boothDetailInfo.waitingEnabled,
            onAction = onAction,
            modifier = Modifier.align(Alignment.BottomCenter),
        )
        SnackbarHost(
            hostState = snackBarState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 112.dp),
            snackbar = {
                UnifestSnackBar(snackBarData = it)
            },
        )

        if (uiState.isMenuImageDialogVisible && uiState.selectedMenu != null) {
            MenuImageDialog(
                onDismissRequest = { onAction(BoothUiAction.OnMenuImageDialogDismiss) },
                menu = uiState.selectedMenu,
            )
        }

        if (uiState.isLoading) {
            LoadingWheel(modifier = Modifier.fillMaxSize())
        }

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

        if (uiState.isPinCheckDialogVisible) {
            WaitingPinDialog(
                boothName = uiState.boothDetailInfo.name,
                pinNumber = uiState.boothPinNumber,
                onPinNumberUpdated = { onAction(BoothUiAction.OnPinNumberUpdated(it)) },
                onDialogPinButtonClick = { onAction(BoothUiAction.OnDialogPinButtonClick) },
                onDismissRequest = { onAction(BoothUiAction.OnPinDialogDismiss) },
                isWrongPinInserted = uiState.isWrongPinInserted,
            )
        }

        if (uiState.isWaitingDialogVisible) {
            WaitingDialog(
                boothName = uiState.boothDetailInfo.name,
                waitingCount = uiState.waitingTeamNumber,
                phoneNumber = uiState.waitingTel,
                partySize = uiState.waitingPartySize,
                isPrivacyClicked = uiState.privacyConsentChecked,
                onDismissRequest = { onAction(BoothUiAction.OnWaitingDialogDismiss) },
                onWaitingMinusClick = { onAction(BoothUiAction.OnWaitingMinusClick) },
                onWaitingPlusClick = { onAction(BoothUiAction.OnWaitingPlusClick) },
                onDialogWaitingButtonClick = { onAction(BoothUiAction.OnDialogWaitingButtonClick) },
                onWaitingTelUpdated = { onAction(BoothUiAction.OnWaitingTelUpdated(it)) },
                onPolicyCheckBoxClick = { onAction(BoothUiAction.OnPolicyCheckBoxClick) },
                onPrivacyPolicyClick = { onAction(BoothUiAction.OnPrivatePolicyClick) },
                onThirdPartyPolicyClick = { onAction(BoothUiAction.OnThirdPartyPolicyClick) },
            )
        }

        if (uiState.isConfirmDialogVisible) {
            WaitingConfirmDialog(
                boothName = uiState.boothDetailInfo.name,
                waitingId = uiState.waitingId,
                waitingPartySize = uiState.waitingPartySize,
                waitingTeamNumber = uiState.waitingTeamNumber,
                onConfirmClick = { onAction(BoothUiAction.OnConfirmDialogDismiss) },
            )
        }
    }
}

@Composable
fun BoothDetailContent(
    uiState: BoothUiState,
    onAction: (BoothUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        item {
            NetworkImage(
                imgUrl = uiState.boothDetailInfo.thumbnail,
                contentDescription = "Booth Image",
                modifier = Modifier
                    .height(260.dp)
                    .fillMaxWidth(),
                placeholder = painterResource(id = R.drawable.image_placeholder),
            )
        }
        item { Spacer(modifier = Modifier.height(30.dp)) }
        item {
            BoothDescription(
                name = uiState.boothDetailInfo.name,
                warning = uiState.boothDetailInfo.warning,
                description = uiState.boothDetailInfo.description,
                location = uiState.boothDetailInfo.location,
                onAction = onAction,
            )
        }
        item { Spacer(modifier = Modifier.height(32.dp)) }
        item { UnifestHorizontalDivider() }
        item { Spacer(modifier = Modifier.height(22.dp)) }
        item {
            Text(
                text = stringResource(id = R.string.booth_menu),
                modifier = Modifier.padding(start = 20.dp),
                color = MaterialTheme.colorScheme.onBackground,
                style = Title2,
            )
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        if (uiState.boothDetailInfo.menus.isEmpty()) {
            item {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = stringResource(id = R.string.booth_empty_menu),
                        modifier = Modifier.padding(top = 76.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = Content3,
                    )
                }
            }
        } else {
            items(
                items = uiState.boothDetailInfo.menus,
                key = { it.id },
            ) { menu ->
                MenuItem(
                    menu = menu,
                    onAction = onAction,
                )
            }
        }
    }
}

@DevicePreview
@Composable
private fun BoothScreenPreview(
    @PreviewParameter(BoothDetailUiStatePreviewParameterProvider::class)
    boothDetailInfo: BoothDetailModel,
) {
    UnifestTheme {
        BoothDetailScreen(
            padding = PaddingValues(),
            uiState = BoothUiState(boothDetailInfo = boothDetailInfo),
            snackBarState = SnackbarHostState(),
            onAction = {},
        )
    }
}
