package com.unifest.android.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.unifest.android.core.common.extension.noRippleClickable
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.DarkComponentPreview
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.theme.Content2
import com.unifest.android.core.designsystem.theme.Title3
import com.unifest.android.core.designsystem.theme.UnifestTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    navigateToAppSetting: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.surface)
                    .padding(24.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.permission_required),
                    style = Title3,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = permissionTextProvider.getDescription(
                        isPermanentlyDeclined = isPermanentlyDeclined,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = Content2,
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                Text(
                    text = if (isPermanentlyDeclined) {
                        stringResource(id = R.string.go_to_app_setting)
                    } else {
                        stringResource(id = R.string.check)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .noRippleClickable {
                            if (isPermanentlyDeclined) {
                                navigateToAppSetting()
                            } else {
                                onConfirm()
                            }
                        },
                    textAlign = TextAlign.Center,
                    style = Title3,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        },
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color = MaterialTheme.colorScheme.background),
    )
}

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined: Boolean): String
}

class LocationPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "위치 정보 접근 권한 요청을 거부하였습니다.\n앱 설정으로 이동하여 권한을 부여할 수 있습니다."
        } else {
            "내 위치를 확인 하기 위해서는 위치 정보 접근 권한이 필요합니다."
        }
    }
}

@ComponentPreview
@Composable
fun PermissionDialogPreview() {
    UnifestTheme {
        PermissionDialog(
            permissionTextProvider = LocationPermissionTextProvider(),
            isPermanentlyDeclined = false,
            onDismiss = {},
            navigateToAppSetting = {},
            onConfirm = {},
        )
    }
}

@DarkComponentPreview
@Composable
fun PermissionDialogDarkPreview() {
    UnifestTheme {
        PermissionDialog(
            permissionTextProvider = LocationPermissionTextProvider(),
            isPermanentlyDeclined = false,
            onDismiss = {},
            navigateToAppSetting = {},
            onConfirm = {},
        )
    }
}
