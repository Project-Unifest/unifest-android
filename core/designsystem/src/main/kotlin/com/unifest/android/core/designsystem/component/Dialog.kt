package com.unifest.android.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.unifest.android.core.common.utils.PhoneNumberVisualTransformation
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.DarkComponentPreview
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.theme.BoothLocation
import com.unifest.android.core.designsystem.theme.BoothTitle2
import com.unifest.android.core.designsystem.theme.BoothTitle3
import com.unifest.android.core.designsystem.theme.Content2
import com.unifest.android.core.designsystem.theme.Content6
import com.unifest.android.core.designsystem.theme.Title1
import com.unifest.android.core.designsystem.theme.Title2
import com.unifest.android.core.designsystem.theme.Title3
import com.unifest.android.core.designsystem.theme.Title4
import com.unifest.android.core.designsystem.theme.Title5
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.designsystem.theme.WaitingNumber3
import com.unifest.android.core.designsystem.theme.WaitingTeam

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
                        containerColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    ) {
                        Text(
                            text = stringResource(id = cancelTextResId),
                            color = MaterialTheme.colorScheme.onBackground,
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaitingPinDialog(
    boothName: String,
    pinNumber: String,
    isWrongPinInserted: Boolean,
    onDismissRequest: () -> Unit,
    onPinNumberUpdated: (String) -> Unit,
    onDialogPinButtonClick: () -> Unit,
) {

    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(color = MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(14.dp))
            Row {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_location_green),
                    contentDescription = "location icon",
                    tint = Color.Unspecified,
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = boothName,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = Title3,
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = "부스 PIN 입력",
                color = MaterialTheme.colorScheme.onBackground,
                style = WaitingTeam,
            )
            Spacer(modifier = Modifier.height(30.dp))
            Column {
                BasicTextField(
                    value = pinNumber,
                    onValueChange = { newPin -> onPinNumberUpdated(newPin) },
                    //todo: String과 textfield
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .border(1.dp, MaterialTheme.colorScheme.onSecondaryContainer, RoundedCornerShape(5.dp))
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(5.dp),
                        )
                        .padding(horizontal = 10.dp, vertical = 19.dp),
                    decorationBox = { innerTextField ->
                        if (pinNumber.isEmpty()) {
                            Text(
                                text = "4자리 PIN을 입력해주세요",
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                style = BoothTitle2,
                            )
                        }
                        innerTextField()
                    },
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(modifier = Modifier.width(14.dp))
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = if (isWrongPinInserted) R.drawable.ic_warning else R.drawable.ic_booth_info
                        ),
                        contentDescription = if (isWrongPinInserted) "warning icon" else "booth info icon",
                        tint = Color.Unspecified,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (isWrongPinInserted) {
                            "올바르지 않은 PIN입니다. 부스 운영자에 문의바랍니다."
                        } else {
                            "웨이팅 PIN은 부스 운영자에게 문의해주세요!"
                        },
                        color = if (isWrongPinInserted) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onBackground,
                        style = Content6,
                    )
                }
            }
            Spacer(modifier = Modifier.height(35.dp))
            UnifestButton(
                onClick = onDialogPinButtonClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) {
                Text(
                    text = "PIN 입력",
                    style = Title4,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaitingDialog(
    boothName: String,
    waitingCount: Long,
    phoneNumber: String,
    partySize: Long,
    isPrivacyClicked: Boolean,
    onDismissRequest: () -> Unit,
    onWaitingMinusClick: () -> Unit,
    onWaitingPlusClick: () -> Unit,
    onWaitingTelUpdated: (String) -> Unit,
    onDialogWaitingButtonClick: () -> Unit,
    onPolicyCheckBoxClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onThirdPartyPolicyClick: () -> Unit,
) {

    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(color = MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(14.dp))
            Row {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_location_green),
                    contentDescription = "location icon",
                    tint = Color.Unspecified,
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = boothName,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = Title3,
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "현재 내 앞 웨이팅",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = BoothLocation,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$waitingCount 팀",
                //todo: 구현
                color = MaterialTheme.colorScheme.onBackground,
                style = WaitingTeam,
            )
            Spacer(modifier = Modifier.height(9.dp))
            UnifestHorizontalDivider(thickness = 1.dp)
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) {
                Text(
                    text = "인원 수",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = Title5,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CircularOutlineButton(
                        icon = Icons.Default.Remove,
                        contentDescription = "Minus Button",
                        onClick = onWaitingMinusClick,
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = partySize.toString(),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = WaitingNumber3,
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    CircularOutlineButton(
                        icon = Icons.Default.Add,
                        contentDescription = "Plus Button",
                        onClick = onWaitingPlusClick,
                    )
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
            BasicTextField(
                value = phoneNumber,
                onValueChange = {
                    if (it.length <= 11) {
                        onWaitingTelUpdated(it)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .border(1.dp, MaterialTheme.colorScheme.onSecondaryContainer, RoundedCornerShape(5.dp))
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(5.dp),
                    )
                    .padding(11.dp),
                decorationBox = { innerTextField ->
                    if (phoneNumber.isEmpty()) {
                        Text(
                            text = "전화번호를 입력해주세요",
                            color = MaterialTheme.colorScheme.onSecondary,
                            style = BoothLocation,
                        )
                    }
                    innerTextField()
                },
                visualTransformation = PhoneNumberVisualTransformation(),
            )
            //TODO: 전화번호 입력 시,
            Spacer(modifier = Modifier.height(12.dp))
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(horizontal = 16.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = if (isPrivacyClicked) R.drawable.ic_checkbox else R.drawable.ic_checkbox_unchecked
                        ),
                        contentDescription = "check box icon",
                        tint = Color.Unspecified,
                        modifier = Modifier.clickable {
                            onPolicyCheckBoxClick()
                        }
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "개인정보 처리방침",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = Content6.copy(
                            textDecoration = TextDecoration.Underline,
                        ),
                        modifier = Modifier.clickable {
                            onPrivacyPolicyClick()
                        },
                    )
                    Text(
                        text = " 및 ",
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        style = Content6,
                    )
                    Text(
                        text = "제 3자 제공방침",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = Content6.copy(
                            textDecoration = TextDecoration.Underline,
                        ),
                        modifier = Modifier.clickable {
                            onThirdPartyPolicyClick()
                        },
                    )
                    Text(
                        text = "에 동의합니다",
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        style = Content6,
                    )
                }
            }
            Spacer(modifier = Modifier.height(11.dp))
            UnifestButton(
                onClick = onDialogWaitingButtonClick,
                containerColor = if (isPrivacyClicked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                enabled = isPrivacyClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) {
                Text(
                    text = "웨이팅 신청",
                    style = Title4,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaitingConfirmDialog(
    boothName: String,
    onDismissRequest: () -> Unit,
    waitingId: Long,
    waitingPartySize: Long,
    waitingTeamNumber: Long,
    onConfirmClick: () -> Unit,
) {
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(color = MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(28.dp))
            Row {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_location_green),
                    contentDescription = "location icon",
                    tint = Color.Unspecified,
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = boothName,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = Title3,
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "웨이팅 등록 완료!",
                color = MaterialTheme.colorScheme.onBackground,
                style = Title1,
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = "입장 순서가 되면 안내 해드릴게요.",
                style = Content2,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.height(12.dp))
            UnifestHorizontalDivider(thickness = 1.dp)
            Spacer(modifier = Modifier.height(22.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "웨이팅 번호",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = Title5,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$waitingId 번",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = BoothTitle3,
                    )
                }
                Spacer(modifier = Modifier.width(25.dp))
                Box(
                    modifier = Modifier
                        .size(3.dp)
                        .background(
                            color = MaterialTheme.colorScheme.onBackground,
                            shape = CircleShape,
                        ),
                )
                Spacer(modifier = Modifier.width(25.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "인원수",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = Title5,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$waitingPartySize 명",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = BoothTitle3,
                    )
                }
                Spacer(modifier = Modifier.width(25.dp))
                Box(
                    modifier = Modifier
                        .size(3.dp)
                        .background(
                            color = MaterialTheme.colorScheme.onBackground,
                            shape = CircleShape,
                        ),
                )
                Spacer(modifier = Modifier.width(25.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "내 앞 웨이팅",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = Title5,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$waitingTeamNumber 팀",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = BoothTitle3,
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                UnifestButton(
                    onClick = onConfirmClick,
                    contentPadding = PaddingValues(vertical = 16.dp),
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = "완료",
                        style = Title5,
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ClickableText(
    text: String,
    style: TextStyle = TextStyle.Default,
    onClick: () -> Unit,
) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onBackground,
        style = style.copy(
            textDecoration = TextDecoration.Underline,
        ),
        modifier = Modifier.clickable(onClick = onClick),
    )
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


@ComponentPreview
@Composable
fun WaitingPinDialogPreview() {
    UnifestTheme {
        WaitingPinDialog(
            boothName = "컴공 주점",
            pinNumber = "",
            onDismissRequest = {},
            onDialogPinButtonClick = { },
            onPinNumberUpdated = { },
            isWrongPinInserted = true,
        )
    }
}

@DarkComponentPreview
@Composable
fun WaitingPinDialogDarkPreview() {
    UnifestTheme {
        WaitingPinDialog(
            boothName = "컴공 주점",
            pinNumber = "",
            onDismissRequest = {},
            onDialogPinButtonClick = { },
            onPinNumberUpdated = { },
            isWrongPinInserted = true,
        )
    }
}


@ComponentPreview
@Composable
fun WaitingDialogPreview() {
    UnifestTheme {
        WaitingDialog(
            boothName = "컴공 주점",
            onDismissRequest = {},
            phoneNumber = "",
            waitingCount = 3,
            partySize = 3,
            onDialogWaitingButtonClick = { },
            onWaitingMinusClick = { },
            onWaitingPlusClick = { },
            onWaitingTelUpdated = { },
            isPrivacyClicked = false,
            onPolicyCheckBoxClick = { },
            onPrivacyPolicyClick = { },
            onThirdPartyPolicyClick = { },
        )
    }
}

@DarkComponentPreview
@Composable
fun WaitingDialogDarkPreview() {
    UnifestTheme {
        WaitingDialog(
            boothName = "컴공 주점",
            onDismissRequest = {},
            phoneNumber = "",
            waitingCount = 3,
            partySize = 3,
            onDialogWaitingButtonClick = { },
            onWaitingMinusClick = { },
            onWaitingPlusClick = { },
            onWaitingTelUpdated = { },
            isPrivacyClicked = false,
            onPolicyCheckBoxClick = { },
            onPrivacyPolicyClick = { },
            onThirdPartyPolicyClick = { },
        )
    }
}

@ComponentPreview
@Composable
fun WaitingConfirmDialogPreview() {
    UnifestTheme {
        WaitingConfirmDialog(
            boothName = "컴공 주점",
            onDismissRequest = {},
            waitingId = 1,
            waitingPartySize = 3,
            waitingTeamNumber = 3,
            onConfirmClick = { },
        )
    }
}

@DarkComponentPreview
@Composable
fun WaitingConfirmDialogDarkPreview() {
    UnifestTheme {
        WaitingConfirmDialog(
            boothName = "컴공 주점",
            onDismissRequest = {},
            waitingId = 1,
            waitingPartySize = 3,
            waitingTeamNumber = 3,
            onConfirmClick = { },
        )
    }
}
