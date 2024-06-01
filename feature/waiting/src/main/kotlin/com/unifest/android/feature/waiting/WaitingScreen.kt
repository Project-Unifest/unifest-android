package com.unifest.android.feature.waiting

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
//  import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unifest.android.core.common.ObserveAsEvents
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.component.UnifestOutlinedButton
import com.unifest.android.core.designsystem.theme.BoothTitle2
import com.unifest.android.core.designsystem.theme.Content1
import com.unifest.android.core.designsystem.theme.Content7
import com.unifest.android.core.designsystem.theme.Title3
import com.unifest.android.core.designsystem.theme.Title4
import com.unifest.android.core.designsystem.theme.Title5
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.designsystem.theme.WaitingNumber
import com.unifest.android.core.designsystem.theme.WaitingNumber2
import com.unifest.android.core.ui.DevicePreview
import com.unifest.android.feature.waiting.viewmodel.WaitingUiEvent
//  import com.unifest.android.feature.waiting.viewmodel.WaitingUiState
import com.unifest.android.feature.waiting.viewmodel.WaitingViewModel

@Composable
internal fun WaitingRoute(
    padding: PaddingValues,
    popBackStack: () -> Unit,
    viewModel: WaitingViewModel = hiltViewModel(),
) {
//    val waitingUiState by viewModel.uiState.collectAsStateWithLifecycle()

    ObserveAsEvents(flow = viewModel.uiEvent) { event ->
        when (event) {
            is WaitingUiEvent.NavigateBack -> popBackStack()
        }
    }

    WaitingScreen(
        padding = padding,
//        waitingUiState = waitingUiState,
//        onWaitingUiAction = viewModel::onWaitingUiAction,
    )
}

@Composable
internal fun WaitingScreen(
    padding: PaddingValues,
//    waitingUiState: WaitingUiState,
//    onWaitingUiAction: (WaitingUiAction) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
        ) {
            item { Text("웨이팅", style = BoothTitle2) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(29.dp),
                    border = BorderStroke(1.dp, Color(0xFFF5687E)),
                    colors = CardColors(
                        containerColor = Color(0xFFFFF0F3),
                        contentColor = Color(0xFFF5687E),
                        disabledContainerColor = Color.White,
                        disabledContentColor = Color(0xFF585858),
                    ),
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = "나의 웨이팅",
                            style = Title4,
                            color = Color(0xFFF5687E),
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
                        text = "총 2건",
                        style = Content7,
                        color = Color(0xFF545454),
                    )
                    Row(
                        modifier = Modifier
                            .clickable {},
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_below_waiting),
                            contentDescription = "filter icon",
                            modifier = Modifier
                                .align(Alignment.CenterVertically),
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "정렬",
                            style = Content7,
                            color = Color(0xFF545454),
                        )
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(8.dp)) }
            item {
                WaitInfoCard(location = "컴공 주점", order = 35, waitingNumber = 112, people = 3)
                Spacer(modifier = Modifier.height(8.dp))
                WaitInfoCard(location = "학생회 부스", order = 13, waitingNumber = 134, people = 3)
            }
        }

//        if (waitingUiState.waitingLists.isEmpty()) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(bottom = 50.dp),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally,
//        ) {
//            Text(
//                text = "신청한 웨이팅이 없어요",
//                style = Title4.copy(fontSize = 16.sp),
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = "주점/부스 구경하러 가기>",
//                style = Title4.copy(fontSize = 14.sp),
//                color = Color.Gray,
//            )
//        }
//    }
    }
}

@Composable
fun WaitInfoCard(location: String, order: Int, waitingNumber: Int, people: Int) {
    Card(
        border = BorderStroke(1.dp, Color(0xFFA4A4A4)),
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContainerColor = Color.White,
            disabledContentColor = Color(0xFF585858),
        ),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "현재 내 순서",
                    style = Content1,
                )
                Row {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_location_black),
                        contentDescription = "Location Icon",
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        tint = Color.Unspecified,
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = location,
                        style = Title3,
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Text(
                        text = order.toString(),
                        style = WaitingNumber,
                        color = Color(0xFFF5687E),
                        modifier = Modifier.alignByBaseline(),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "번째",
                        fontSize = 18.sp,
                        color = Color(0xFF545454),
                        modifier = Modifier.alignByBaseline(),
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "웨이팅번호",
                        style = Content1,
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "$waitingNumber",
                        style = WaitingNumber2,
                    )
                    Spacer(modifier = Modifier.width(13.dp))
                    VerticalDivider(
                        thickness = 1.dp,
                        color = Color(0xFFB6B6B6),
                        modifier = Modifier.height(13.dp),
                    )
                    Spacer(modifier = Modifier.width(13.dp))
                    Text(
                        text = "인원",
                        style = Content1,
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "$people",
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
                    onClick = { },
                    borderColor = Color(0xFFCFCFCF),
                    contentColor = Color(0xFF7D7D7D),
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = "웨이팅 취소",
                        style = Title5,
                    )
                }
            }
        }
    }
}

@DevicePreview
@Composable
fun WaitingScreenPreview() {
    UnifestTheme {
        WaitingScreen(
            padding = PaddingValues(),
//            waitingUiState = WaitingUiState(),
//            onWaitingUiAction = {},
        )
    }
}
