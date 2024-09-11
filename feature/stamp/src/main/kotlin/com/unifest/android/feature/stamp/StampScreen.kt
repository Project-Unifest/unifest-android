package com.unifest.android.feature.stamp

import android.Manifest
import android.content.Intent
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unifest.android.core.common.ObserveAsEvents
import com.unifest.android.core.common.PermissionDialogButtonType
import com.unifest.android.core.common.extension.findActivity
import com.unifest.android.core.common.extension.navigateToAppSetting
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.theme.BoothTitle2
import com.unifest.android.core.designsystem.theme.Content1
import com.unifest.android.core.designsystem.theme.Content2
import com.unifest.android.core.designsystem.theme.DarkGrey200
import com.unifest.android.core.designsystem.theme.DarkGrey400
import com.unifest.android.core.designsystem.theme.LightGrey100
import com.unifest.android.core.designsystem.theme.MenuTitle
import com.unifest.android.core.designsystem.theme.StampCount
import com.unifest.android.core.designsystem.theme.Title1
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.ui.DevicePreview
import com.unifest.android.core.ui.component.CameraPermissionTextProvider
import com.unifest.android.core.ui.component.PermissionDialog
import com.unifest.android.feature.stamp.component.StampButton
import com.unifest.android.feature.stamp.preview.StampPreviewParameterProvider
import com.unifest.android.feature.stamp.viewmodel.StampUiAction
import com.unifest.android.feature.stamp.viewmodel.StampUiEvent
import com.unifest.android.feature.stamp.viewmodel.StampUiState
import com.unifest.android.feature.stamp.viewmodel.StampViewModel

@Composable
internal fun StampRoute(
    padding: PaddingValues,
    popBackStack: () -> Unit,
    viewModel: StampViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val activity = context.findActivity()

    val permissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
            viewModel.onPermissionResult(it)
        },
    )

    ObserveAsEvents(flow = viewModel.uiEvent) { event ->
        when (event) {
            is StampUiEvent.NavigateBack -> popBackStack()
            is StampUiEvent.NavigateToQRScan -> startActivity(context, Intent(context, QRScanActivity::class.java), null)
            is StampUiEvent.RequestCameraPermission -> permissionResultLauncher.launch(Manifest.permission.CAMERA)
            is StampUiEvent.NavigateToAppSetting -> activity.navigateToAppSetting()
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
            Spacer(modifier = Modifier.height(6.dp))
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
                        Spacer(modifier = Modifier.width(24.dp))
                        Column {
                            Text(
                                text = "한국교통대학교",
                                color = MaterialTheme.colorScheme.onBackground,
                                style = Title1,
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = stringResource(id = R.string.stamp_collection_status),
                                color = MaterialTheme.colorScheme.onSurface,
                                style = Content1,
                            )
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
                    Spacer(modifier = Modifier.height(21.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Spacer(modifier = Modifier.width(24.dp))
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                                    append("${uiState.receivedStamp}")
                                }
                                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onSurface)) {
                                    append(" / ${uiState.stampList.size} 개")
                                }
                            },
                            style = StampCount,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Row(
                            modifier = Modifier.clickable {
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
                        Spacer(modifier = Modifier.width(24.dp))
                    }
                    Spacer(modifier = Modifier.height(44.dp))
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .height(if (uiState.stampList.isEmpty()) 0.dp else (((uiState.stampList.size - 1) / 4 + 1) * 84).dp),
                        verticalArrangement = Arrangement.spacedBy(11.dp),
                        horizontalArrangement = Arrangement.spacedBy(9.dp),
                    ) {
                        items(
                            count = uiState.stampList.size,
                            key = { index -> uiState.stampList[index].boothId },
                        ) { index ->
                            Box {
                                Image(
                                    painter = if (uiState.stampList[index].isChecked) painterResource(id = R.drawable.ic_checked_stamp)
                                    else painterResource(id = R.drawable.ic_unchecked_stamp),
                                    contentDescription = "stamp image",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(10.dp)),
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(54.dp))
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
                            contentDescription = "arrow right icon",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Spacer(modifier = Modifier.width(22.dp))
                    }
                    Spacer(modifier = Modifier.height(21.dp))
                }
            }
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
    }
}

@DevicePreview
@Composable
fun StampScreenPreview(
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
