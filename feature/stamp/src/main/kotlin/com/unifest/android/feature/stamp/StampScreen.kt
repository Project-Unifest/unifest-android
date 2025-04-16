package com.unifest.android.feature.stamp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skydoves.compose.effects.RememberedEffect
import com.unifest.android.core.common.ObserveAsEvents
import com.unifest.android.core.common.PermissionDialogButtonType
import com.unifest.android.core.common.extension.clickableSingle
import com.unifest.android.core.common.extension.findActivity
import com.unifest.android.core.designsystem.component.LoadingWheel
import com.unifest.android.core.designsystem.component.NetworkErrorDialog
import com.unifest.android.core.designsystem.component.NetworkImage
import com.unifest.android.core.designsystem.component.ServerErrorDialog
import com.unifest.android.core.designsystem.theme.BoothTitle2
import com.unifest.android.core.designsystem.theme.Content2
import com.unifest.android.core.designsystem.theme.DarkGrey200
import com.unifest.android.core.designsystem.theme.DarkGrey300
import com.unifest.android.core.designsystem.theme.DarkGrey400
import com.unifest.android.core.designsystem.theme.DarkGrey700
import com.unifest.android.core.designsystem.theme.LightGrey100
import com.unifest.android.core.designsystem.theme.MenuTitle
import com.unifest.android.core.designsystem.theme.StampCount
import com.unifest.android.core.designsystem.theme.Title0
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.ui.DevicePreview
import com.unifest.android.core.ui.component.CameraPermissionTextProvider
import com.unifest.android.core.ui.component.PermissionDialog
import com.unifest.android.feature.stamp.component.SchoolsDropDownMenu
import com.unifest.android.feature.stamp.component.StampBoothBottomSheet
import com.unifest.android.feature.stamp.component.StampButton
import com.unifest.android.feature.stamp.preview.StampPreviewParameterProvider
import com.unifest.android.feature.stamp.viewmodel.stamp.ErrorType
import com.unifest.android.feature.stamp.viewmodel.stamp.StampUiAction
import com.unifest.android.feature.stamp.viewmodel.stamp.StampUiEvent
import com.unifest.android.feature.stamp.viewmodel.stamp.StampUiState
import com.unifest.android.feature.stamp.viewmodel.stamp.StampViewModel

@Composable
internal fun StampRoute(
    padding: PaddingValues,
    popBackStack: () -> Unit,
    navigateToBoothDetail: (Long) -> Unit,
    viewModel: StampViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val activity = context.findActivity()

    var isCameraPermissionGranted by remember {
        mutableStateOf(
            activity.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED,
        )
    }

    val permissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            isCameraPermissionGranted = isGranted
            viewModel.onPermissionResult(isGranted)
        },
    )

    val settingsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            // 설정에서 돌아왔을 때 권한 상태를 다시 확인
            isCameraPermissionGranted = activity.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
            viewModel.onPermissionResult(isCameraPermissionGranted)
        },
    )

    val qrScanLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.getCollectedStamps(uiState.selectedFestival.festivalId)
        }
    }

    ObserveAsEvents(flow = viewModel.uiEvent) { event ->
        when (event) {
            is StampUiEvent.NavigateBack -> popBackStack()
            is StampUiEvent.NavigateToQRScan -> qrScanLauncher.launch(Intent(context, QRScanActivity::class.java))
            is StampUiEvent.RequestCameraPermission -> permissionResultLauncher.launch(Manifest.permission.CAMERA)
            is StampUiEvent.NavigateToAppSetting -> {
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", activity.packageName, null),
                )
                settingsLauncher.launch(intent)
            }

            is StampUiEvent.NavigateToBoothDetail -> navigateToBoothDetail(event.boothId)
        }
    }

    RememberedEffect(uiState.selectedFestival) {
        if (uiState.selectedFestival.festivalId != 0L) {
            viewModel.getCollectedStamps(uiState.selectedFestival.festivalId)
            viewModel.getStampEnabledBooths(uiState.selectedFestival.festivalId)
        }
    }

    StampScreen(
        padding = padding,
        uiState = uiState,
        onAction = viewModel::onAction,
    )
}

@Composable
internal fun StampScreen(
    padding: PaddingValues,
    uiState: StampUiState,
    onAction: (StampUiAction) -> Unit,
) {
    val activity = LocalContext.current.findActivity()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(padding),
    ) {
        StampContent(
            uiState = uiState,
            onAction = onAction,
        )

        if (uiState.isLoading) {
            LoadingWheel(modifier = Modifier.fillMaxSize())
        }

        if (uiState.isPermissionDialogVisible) {
            PermissionDialog(
                permissionTextProvider = CameraPermissionTextProvider(),
                isPermanentlyDeclined = !activity.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA),
                onDismiss = { onAction(StampUiAction.OnPermissionDialogButtonClick(PermissionDialogButtonType.DISMISS)) },
                navigateToAppSetting = { onAction(StampUiAction.OnPermissionDialogButtonClick(PermissionDialogButtonType.NAVIGATE_TO_APP_SETTING)) },
                onConfirm = { onAction(StampUiAction.OnPermissionDialogButtonClick(PermissionDialogButtonType.CONFIRM)) },
            )
        }

        if (uiState.isStampBoothDialogVisible) {
            StampBoothBottomSheet(
                schoolName = uiState.selectedFestival.name,
                stampBoothList = uiState.stampBoothList,
                onAction = onAction,
            )
        }

        if (uiState.isServerErrorDialogVisible) {
            ServerErrorDialog(
                onRetryClick = { onAction(StampUiAction.OnRetryClick(ErrorType.SERVER)) },
            )
        }

        if (uiState.isNetworkErrorDialogVisible) {
            NetworkErrorDialog(
                onRetryClick = { onAction(StampUiAction.OnRetryClick(ErrorType.NETWORK)) },
            )
        }
    }
}

@Composable
internal fun StampContent(
    uiState: StampUiState,
    onAction: (StampUiAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = stringResource(id = R.string.stamp_title),
                modifier = Modifier.padding(vertical = 18.dp),
                color = MaterialTheme.colorScheme.onBackground,
                style = BoothTitle2,
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        SchoolsDropDownMenu(
            isDropDownMenuOpened = uiState.isDropDownMenuOpened,
            festivals = uiState.stampEnabledFestivalList,
            selectedFestival = uiState.selectedFestival,
            onAction = onAction,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        )
        Spacer(modifier = Modifier.height(12.dp))
        if (uiState.stampEnabledFestivalList.contains(uiState.selectedFestival)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 20.dp)
                    .background(color = if (isSystemInDarkTheme()) DarkGrey200 else LightGrey100, shape = RoundedCornerShape(10.dp)),
            ) {
                Column {
                    Spacer(modifier = Modifier.height(26.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Spacer(modifier = Modifier.width(25.dp))
                        Column {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                                        append("${uiState.collectedStampCount}")
                                    }
                                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onSurface)) {
                                        append(" / ${uiState.stampBoothList.size}개")
                                    }
                                },
                                style = StampCount,
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier.clickableSingle {
                                    onAction(StampUiAction.OnRefreshClick)
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
                        Spacer(modifier = Modifier.weight(1f))
                        StampButton(
                            onClick = {
                                onAction(StampUiAction.OnReceiveStampClick)
                            },
                            text = stringResource(id = R.string.receive_stamp),
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .height(if (uiState.stampBoothList.isEmpty()) 0.dp else (((uiState.stampBoothList.size - 1) / 4 + 1) * 84).dp),
                        verticalArrangement = Arrangement.spacedBy(11.dp),
                        horizontalArrangement = Arrangement.spacedBy(9.dp),
                    ) {
                        items(
                            count = uiState.stampBoothList.size,
                            key = { index -> uiState.stampBoothList[index].id },
                        ) { index ->
                            Box {
                                if (index < uiState.collectedStampCount) {
                                    if (uiState.stampEnabledFestivalList[0].defaultImgUrl.isNotEmpty()) {
                                        NetworkImage(
                                            imgUrl = uiState.stampEnabledFestivalList[0].defaultImgUrl,
                                            contentDescription = "Stamp Default Image",
                                            modifier = Modifier
                                                .size(62.dp)
                                                .clip(CircleShape),
                                            painterResource(id = R.drawable.ic_unchecked_stamp),
                                        )
                                    } else {
                                        Image(
                                            painter = painterResource(id = R.drawable.ic_unchecked_stamp),
                                            contentDescription = "Stamp Default Image",
                                            modifier = Modifier.size(62.dp)
                                        )
                                    }
                                } else {
                                    if (uiState.stampEnabledFestivalList[0].usedImgUrl.isNotEmpty()) {
                                        NetworkImage(
                                            imgUrl = uiState.stampEnabledFestivalList[0].usedImgUrl,
                                            contentDescription = "Stamp Used Image",
                                            modifier = Modifier
                                                .size(62.dp)
                                                .clip(CircleShape),
                                            placeholder = painterResource(id = R.drawable.ic_checked_stamp),
                                        )
                                    } else {
                                        Image(
                                            painter = painterResource(id = R.drawable.ic_checked_stamp),
                                            contentDescription = "Stamp Used Image",
                                            modifier = Modifier.size(62.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(28.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .padding(horizontal = 24.dp)
                            .background(color = if (isSystemInDarkTheme()) DarkGrey400 else Color.White, shape = RoundedCornerShape(7.dp))
                            .clickable {
                                onAction(StampUiAction.OnFindStampBoothClick)
                            },
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Spacer(modifier = Modifier.width(24.dp))
                        Text(
                            text = stringResource(id = R.string.find_stamp_booth),
                            color = MaterialTheme.colorScheme.onSecondary,
                            style = MenuTitle,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_right),
                            contentDescription = "Arrow Right Icon",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Spacer(modifier = Modifier.width(21.dp))
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(454.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceBright,
                        shape = RoundedCornerShape(10.dp),
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_stamp_not_support),
                    contentDescription = "Stamp Not Supported Icon",
                    tint = if (isSystemInDarkTheme()) DarkGrey300 else DarkGrey700,
                )
                Spacer(modifier = Modifier.height(26.dp))
                Text(
                    text = stringResource(id = R.string.stamp_not_support_school),
                    color = DarkGrey400,
                    style = Title0,
                )
            }
        }
    }
}

@DevicePreview
@Composable
private fun StampScreenPreview(
    @PreviewParameter(StampPreviewParameterProvider::class)
    stampUiState: StampUiState,
) {
    UnifestTheme {
        StampScreen(
            padding = PaddingValues(),
            uiState = stampUiState,
            onAction = {},
        )
    }
}
