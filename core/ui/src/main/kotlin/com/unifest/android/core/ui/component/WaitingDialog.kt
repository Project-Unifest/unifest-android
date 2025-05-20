package com.unifest.android.core.ui.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.unifest.android.core.common.utils.PhoneNumberVisualTransformation
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.component.CircularOutlineButton
import com.unifest.android.core.designsystem.component.UnifestButton
import com.unifest.android.core.designsystem.component.UnifestDialog
import com.unifest.android.core.designsystem.theme.BoothLocation
import com.unifest.android.core.designsystem.theme.BoothTitle2
import com.unifest.android.core.designsystem.theme.BoothTitle3
import com.unifest.android.core.designsystem.theme.Content2
import com.unifest.android.core.designsystem.theme.Content6
import com.unifest.android.core.designsystem.theme.Title1
import com.unifest.android.core.designsystem.theme.Title3
import com.unifest.android.core.designsystem.theme.Title4
import com.unifest.android.core.designsystem.theme.Title5
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.designsystem.theme.WaitingNumber3
import com.unifest.android.core.designsystem.theme.WaitingTeam
import com.unifest.android.core.ui.R
import com.unifest.android.core.designsystem.R as designR

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
                    imageVector = ImageVector.vectorResource(id = designR.drawable.ic_location_green),
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
                text = stringResource(id = R.string.waiting_dialog_enter_booth_pin),
                color = MaterialTheme.colorScheme.onBackground,
                style = WaitingTeam,
            )
            Spacer(modifier = Modifier.height(30.dp))
            Column {
                BasicTextField(
                    value = pinNumber,
                    onValueChange = { input ->
                        if (input.matches(Regex("^\\d{0,4}\$"))) {
                            onPinNumberUpdated(input)
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
                        .padding(horizontal = 10.dp, vertical = 19.dp),
                    textStyle = BoothTitle2.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                    ),
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                    ),
                    decorationBox = { innerTextField ->
                        if (pinNumber.isEmpty()) {
                            Text(
                                text = stringResource(id = R.string.waiting_dialog_enter_booth_pin_hint),
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                style = BoothTitle2,
                            )
                        }
                        innerTextField()
                    },
                )
                Spacer(modifier = Modifier.height(10.dp))
                AnimatedContent(targetState = isWrongPinInserted) { isWrongPinInserted ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Spacer(modifier = Modifier.width(14.dp))
                        Icon(
                            imageVector = ImageVector.vectorResource(
                                id = if (isWrongPinInserted) designR.drawable.ic_warning else designR.drawable.ic_booth_info,
                            ),
                            contentDescription = if (isWrongPinInserted) "warning icon" else "booth info icon",
                            tint = if (isWrongPinInserted) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onBackground,
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isWrongPinInserted) {
                                stringResource(id = R.string.waiting_dialog_enter_booth_pin_description_error)
                            } else {
                                stringResource(id = R.string.waiting_dialog_enter_booth_pin_description)
                            },
                            color = if (isWrongPinInserted) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onBackground,
                            style = Content6,
                        )
                    }
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
                    text = stringResource(id = R.string.waiting_dialog_enter_booth_pin_button),
                    style = Title4,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
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
                    imageVector = ImageVector.vectorResource(id = designR.drawable.ic_location_green),
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
                text = stringResource(id = R.string.waiting_dialog_telephone_waiting_number),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = BoothLocation,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(id = R.string.waiting_dialog_telephone_waiting_team, waitingCount),
                color = MaterialTheme.colorScheme.onBackground,
                style = WaitingTeam,
            )
            Spacer(modifier = Modifier.height(9.dp))
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outline,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.waiting_dialog_telephone_people),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = Title5,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CircularOutlineButton(
                        icon = ImageVector.vectorResource(id = designR.drawable.ic_minus),
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
                        icon = ImageVector.vectorResource(id = designR.drawable.ic_plus),
                        contentDescription = "Plus Button",
                        onClick = onWaitingPlusClick,
                    )
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
            BasicTextField(
                value = phoneNumber,
                onValueChange = { input ->
                    if (input.matches(Regex("^\\d{0,11}\$"))) {
                        onWaitingTelUpdated(input)
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
                textStyle = Title4.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                ),
                decorationBox = { innerTextField ->
                    if (phoneNumber.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.waiting_dialog_telephone_waiting_number_hint),
                            color = MaterialTheme.colorScheme.onSecondary,
                            style = BoothLocation,
                        )
                    }
                    innerTextField()
                },
                visualTransformation = PhoneNumberVisualTransformation(),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(horizontal = 16.dp),
            ) {
                FlowRow(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = if (isPrivacyClicked) designR.drawable.ic_checkbox else designR.drawable.ic_checkbox_unchecked,
                        ),
                        contentDescription = "check box icon",
                        tint = Color.Unspecified,
                        modifier = Modifier.clickable {
                            onPolicyCheckBoxClick()
                        },
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = stringResource(id = R.string.waiting_dialog_telephone_privacy),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = Content6.copy(
                            textDecoration = TextDecoration.Underline,
                        ),
                        modifier = Modifier.clickable {
                            onPrivacyPolicyClick()
                        },
                    )
                    Text(
                        text = stringResource(id = R.string.waiting_dialog_telephone_agree),
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
                    text = stringResource(id = R.string.waiting_dialog_telephone_button),
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
    waitingId: Long,
    waitingPartySize: Long,
    waitingTeamNumber: Long,
    onConfirmClick: () -> Unit,
) {
    BasicAlertDialog(
        onDismissRequest = {},
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
                    imageVector = ImageVector.vectorResource(id = designR.drawable.ic_location_green),
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
                text = stringResource(id = R.string.waiting_dialog_waiting_confirm_complete),
                color = MaterialTheme.colorScheme.onBackground,
                style = Title1,
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = stringResource(id = R.string.waiting_dialog_waiting_confirm_description),
                style = Content2,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outline,
            )
            Spacer(modifier = Modifier.height(22.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(id = R.string.waiting_dialog_waiting_confirm_waiting_number),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = Title5,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(id = R.string.waiting_dialog_waiting_confirm_id, waitingId),
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
                        text = stringResource(id = R.string.waiting_dialog_waiting_confirm_people),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = Title5,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(id = R.string.waiting_dialog_waiting_confirm_people_number, waitingPartySize),
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
                        text = stringResource(id = R.string.waiting_dialog_waiting_confirm_previous_waiting),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = Title5,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(id = R.string.waiting_dialog_waiting_confirm_previous_waiting_number, waitingTeamNumber),
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
                        text = stringResource(id = R.string.waiting_dialog_waiting_confirm_button),
                        style = Title5,
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun WaitingCancelDialog(
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit,
) {
    UnifestTheme {
        UnifestDialog(
            onDismissRequest = {},
            titleResId = R.string.waiting_cancel_dialog_title,
            iconResId = designR.drawable.ic_caution,
            iconDescription = "Caution Icon",
            descriptionResId = R.string.waiting_cancel_dialog_description,
            confirmTextResId = designR.string.confirm,
            cancelTextResId = designR.string.cancel,
            onCancelClick = onCancelClick,
            onConfirmClick = onConfirmClick,
        )
    }
}

@Composable
fun NoShowWaitingCancelDialog(
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit,
) {
    UnifestTheme {
        UnifestDialog(
            onDismissRequest = {},
            titleResId = R.string.waiting_no_show_dialog_title,
            iconResId = designR.drawable.ic_caution,
            iconDescription = "Caution Icon",
            descriptionResId = R.string.waiting_no_show_dialog_description,
            confirmTextResId = designR.string.confirm,
            cancelTextResId = designR.string.cancel,
            onCancelClick = onCancelClick,
            onConfirmClick = onConfirmClick,
        )
    }
}

@Composable
fun NoShowAlertDialog(
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit,
) {
    UnifestTheme {
        UnifestDialog(
            onDismissRequest = {},
            titleResId = R.string.waiting_no_show_title,
            iconResId = designR.drawable.ic_caution,
            iconDescription = "Caution Icon",
            descriptionResId = R.string.waiting_no_show_alert_description,
            confirmTextResId = R.string.waiting_no_show_button,
            cancelTextResId = R.string.waiting_no_show_cancel,
            onCancelClick = onCancelClick,
            onConfirmClick = onConfirmClick,
        )
    }
}

@ComponentPreview
@Composable
private fun WaitingPinDialogPreview() {
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
private fun WaitingDialogPreview() {
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
        )
    }
}

@ComponentPreview
@Composable
private fun WaitingConfirmDialogPreview() {
    UnifestTheme {
        WaitingConfirmDialog(
            boothName = "컴공 주점",
            waitingId = 1,
            waitingPartySize = 3,
            waitingTeamNumber = 3,
            onConfirmClick = { },
        )
    }
}

@ComponentPreview
@Composable
private fun NoShowAlertDialogPreview() {
    UnifestTheme {
        NoShowAlertDialog(
            onCancelClick = { },
            onConfirmClick = { },
        )
    }
}
