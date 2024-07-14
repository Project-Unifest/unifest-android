package com.unifest.android.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.DarkComponentPreview
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.theme.BoothLocation
import com.unifest.android.core.designsystem.theme.Title2
import com.unifest.android.core.designsystem.theme.Title5
import com.unifest.android.core.designsystem.theme.UnifestTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnifestDialog(
    onDismissRequest: () -> Unit,
    @StringRes titleResId: Int,
    iconResId: Int?,
    iconDescription: String?,
    @StringRes descriptionResId: Int,
    cancelTextResId: Int?,
    confirmTextResId: Int,
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier,
    properties: DialogProperties = DialogProperties(),
) {
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        properties = properties,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(color = MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(27.dp))
            if (iconResId != null && iconDescription != null) {
                Icon(
                    imageVector = ImageVector.vectorResource(iconResId),
                    contentDescription = iconDescription,
                    tint = Color.Unspecified,
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = stringResource(id = titleResId),
                style = Title2,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = descriptionResId),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                style = BoothLocation,
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
            ) {
                UnifestButton(
                    onClick = onConfirmClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(45.dp)
                        .then(
                            if (cancelTextResId != null) {
                                Modifier.padding(end = 4.dp)
                            } else {
                                Modifier
                            },
                        ),
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White,
                ) {
                    Text(
                        text = stringResource(id = confirmTextResId),
                        color = Color.White,
                        style = Title5,
                    )
                }
                if (cancelTextResId != null) {
                    UnifestButton(
                        onClick = onCancelClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(45.dp)
                            .padding(start = 4.dp),
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ) {
                        Text(
                            text = stringResource(id = cancelTextResId),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = Title5,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ServerErrorDialog(
    onRetryClick: () -> Unit,
) {
    UnifestTheme {
        UnifestDialog(
            onDismissRequest = {},
            titleResId = R.string.server_error_title,
            iconResId = R.drawable.ic_caution,
            iconDescription = "Caution Icon",
            descriptionResId = R.string.server_error_description,
            confirmTextResId = R.string.retry,
            cancelTextResId = null,
            onCancelClick = {},
            onConfirmClick = onRetryClick,
        )
    }
}

@Composable
fun NetworkErrorDialog(
    onRetryClick: () -> Unit,
) {
    UnifestTheme {
        UnifestDialog(
            onDismissRequest = {},
            titleResId = R.string.network_error_title,
            iconResId = R.drawable.ic_network,
            iconDescription = "Network Error Icon",
            descriptionResId = R.string.network_error_description,
            confirmTextResId = R.string.retry,
            cancelTextResId = null,
            onCancelClick = {},
            onConfirmClick = onRetryClick,
        )
    }
}

@Composable
fun LikedFestivalDeleteDialog(
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit,
) {
    UnifestTheme {
        UnifestDialog(
            onDismissRequest = {},
            titleResId = R.string.liked_festival_delete_title,
            iconResId = R.drawable.ic_caution,
            iconDescription = "Caution Icon",
            descriptionResId = R.string.liked_festival_delete_description,
            confirmTextResId = R.string.confirm,
            cancelTextResId = R.string.cancel,
            onCancelClick = onCancelClick,
            onConfirmClick = onConfirmClick,
        )
    }
}

@Composable
fun AppUpdateDialog(
    onDismissRequest: () -> Unit,
    onUpdateClick: () -> Unit,
    properties: DialogProperties = DialogProperties(
        dismissOnBackPress = true,
        dismissOnClickOutside = false,
    ),
) {
    UnifestTheme {
        UnifestDialog(
            onDismissRequest = onDismissRequest,
            titleResId = R.string.app_update_title,
            iconResId = R.drawable.ic_caution,
            iconDescription = "Caution Icon",
            descriptionResId = R.string.app_update_description,
            confirmTextResId = R.string.app_update_confirm,
            cancelTextResId = null,
            onCancelClick = {},
            onConfirmClick = onUpdateClick,
            properties = properties,
        )
    }
}

@ComponentPreview
@Composable
fun ServerErrorDialogPreview() {
    UnifestTheme {
        ServerErrorDialog(onRetryClick = {})
    }
}

@DarkComponentPreview
@Composable
fun ServerErrorDialogDarkPreview() {
    UnifestTheme {
        ServerErrorDialog(onRetryClick = {})
    }
}

@ComponentPreview
@Composable
fun NetworkErrorDialogPreview() {
    UnifestTheme {
        NetworkErrorDialog(onRetryClick = {})
    }
}

@DarkComponentPreview
@Composable
fun NetworkErrorDialogDarkPreview() {
    UnifestTheme {
        NetworkErrorDialog(onRetryClick = {})
    }
}

@ComponentPreview
@Composable
fun LikedFestivalDeleteDialogPreview() {
    UnifestTheme {
        LikedFestivalDeleteDialog(
            onCancelClick = {},
            onConfirmClick = {},
        )
    }
}

@DarkComponentPreview
@Composable
fun LikedFestivalDeleteDialogDarkPreview() {
    UnifestTheme {
        LikedFestivalDeleteDialog(
            onCancelClick = {},
            onConfirmClick = {},
        )
    }
}

@ComponentPreview
@Composable
fun AppUpdateDialogPreview() {
    UnifestTheme {
        AppUpdateDialog(
            onDismissRequest = {},
            onUpdateClick = {},
        )
    }
}

@DarkComponentPreview
@Composable
fun AppUpdateDialogDarkPreview() {
    UnifestTheme {
        AppUpdateDialog(
            onDismissRequest = {},
            onUpdateClick = {},
        )
    }
}
