package com.unifest.android.feature.waiting.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.component.UnifestButton
import com.unifest.android.core.designsystem.theme.Content1
import com.unifest.android.core.designsystem.theme.LightPrimary700
import com.unifest.android.core.designsystem.theme.Title3
import com.unifest.android.core.designsystem.theme.Title5
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.designsystem.theme.WaitingNumber
import com.unifest.android.core.designsystem.theme.WaitingNumber2
import com.unifest.android.core.designsystem.theme.WaitingNumber5
import com.unifest.android.core.model.MyWaitingModel
import com.unifest.android.feature.waiting.R
import com.unifest.android.feature.waiting.viewmodel.WaitingUiAction
import com.unifest.android.core.designsystem.R as designR

@Composable
fun WaitingInfoItem(
    myWaitingModel: MyWaitingModel,
    onWaitingUiAction: (WaitingUiAction) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(8.dp))
            .background(
                color = if (myWaitingModel.status != "NOSHOW") {
                    MaterialTheme.colorScheme.surfaceBright
                } else {
                    MaterialTheme.colorScheme.surfaceContainerHigh
                },
                shape = RoundedCornerShape(8.dp),
            ),
    ) {
        Column(
            modifier = Modifier
                .then(
                    if (myWaitingModel.status != "NOSHOW") {
                        Modifier.background(MaterialTheme.colorScheme.surfaceBright)
                    } else {
                        Modifier
                    },
                )
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
                        imageVector = ImageVector.vectorResource(id = designR.drawable.ic_location_green),
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
                        text = if (myWaitingModel.status == "NOSHOW") {
                            stringResource(id = R.string.waiting_no_show)
                        } else {
                            if (myWaitingModel.waitingOrder.toInt() == 1) {
                                stringResource(id = R.string.waiting_my_turn)
                            } else {
                                myWaitingModel.waitingOrder.toString()
                            }
                        },
                        style = if (myWaitingModel.waitingOrder.toInt() == 1) WaitingNumber5 else WaitingNumber,
                        color = if (myWaitingModel.status == "NOSHOW") {
                            LightPrimary700
                        } else {
                            MaterialTheme.colorScheme.primary
                        },
                        modifier = Modifier.alignByBaseline(),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    if (myWaitingModel.waitingOrder.toInt() != 1 && myWaitingModel.status != "NOSHOW") {
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
                    Spacer(modifier = Modifier.width(8.dp))
                    VerticalDivider(
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.height(13.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
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
                UnifestButton(
                    onClick = {
                        if (myWaitingModel.status == "NOSHOW") {
                            onWaitingUiAction(WaitingUiAction.OnCancelWaitingClick(myWaitingModel.waitingId))
                        } else {
                            onWaitingUiAction(WaitingUiAction.OnCancelWaitingClick(myWaitingModel.waitingId))
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = if (myWaitingModel.status == "NOSHOW") {
                            stringResource(id = R.string.waiting_no_show_description)
                        } else {
                            stringResource(id = R.string.waiting_cancel_waiting)
                        },
                        color = MaterialTheme.colorScheme.surfaceTint,
                        style = Title5,
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                UnifestButton(
                    onClick = { onWaitingUiAction(WaitingUiAction.OnCheckBoothDetailClick(myWaitingModel.boothId)) },
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = stringResource(id = R.string.waiting_booth_check),
                        color = MaterialTheme.colorScheme.onSurface,
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

@ComponentPreview
@Composable
fun WaitingInfoItemPreviewNOSHOW() {
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
                status = "NOSHOW",
                waitingOrder = 1L,
                boothName = "부스 이름",
            ),
            onWaitingUiAction = {},
        )
    }
}
