package com.unifest.android.feature.waiting.component

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.component.UnifestOutlinedButton
import com.unifest.android.core.designsystem.theme.Content1
import com.unifest.android.core.designsystem.theme.DarkGrey100
import com.unifest.android.core.designsystem.theme.DarkRed
import com.unifest.android.core.designsystem.theme.LightGrey100
import com.unifest.android.core.designsystem.theme.LightGrey700
import com.unifest.android.core.designsystem.theme.LightRed
import com.unifest.android.core.designsystem.theme.Title3
import com.unifest.android.core.designsystem.theme.Title5
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.designsystem.theme.WaitingNumber
import com.unifest.android.core.designsystem.theme.WaitingNumber2
import com.unifest.android.core.designsystem.theme.WaitingNumber5
import com.unifest.android.core.model.MyWaitingModel
import com.unifest.android.feature.waiting.viewmodel.WaitingUiAction

@Composable
fun WaitingInfoItem(
    myWaitingModel: MyWaitingModel,
    onWaitingUiAction: (WaitingUiAction) -> Unit,
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
                        text = if (myWaitingModel.waitingOrder.toInt() == 1) {
                            stringResource(id = R.string.waiting_my_turn)
                        } else {
                            myWaitingModel.waitingOrder.toString()
                        },
                        style = if (myWaitingModel.waitingOrder.toInt() == 1) WaitingNumber5 else WaitingNumber,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.alignByBaseline(),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    if (myWaitingModel.waitingOrder.toInt() != 1) {
                        Text(
                            text = stringResource(id = R.string.waiting_nth),
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.alignByBaseline(),
                        )
                    }
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
                    containerColor = if (isSystemInDarkTheme()) DarkGrey100 else LightGrey100,
                    borderColor = if (isSystemInDarkTheme()) DarkGrey100 else LightGrey100,
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

@ComponentPreview
@Composable
fun WaitingInfoItemPreview() {
    UnifestTheme {
        WaitingInfoItem(
            myWaitingModel = MyWaitingModel(
                boothId = 1L,
                waitingId = 1L,
                partySize = 2L,
                tel = "010-1234-5678",
                deviceId = "1234567890",
                createdAt = "2024-05-23",
                updatedAt = "2024-05-23",
                status = "waiting",
                waitingOrder = 1L,
                boothName = "부스 이름",
            ),
            onWaitingUiAction = {},
        )
    }
}