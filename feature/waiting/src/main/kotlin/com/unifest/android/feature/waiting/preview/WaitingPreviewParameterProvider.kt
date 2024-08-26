package com.unifest.android.feature.waiting.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.unifest.android.core.model.MyWaitingModel
import com.unifest.android.feature.waiting.viewmodel.WaitingUiState
import kotlinx.collections.immutable.persistentListOf

internal class WaitingPreviewParameterProvider : PreviewParameterProvider<WaitingUiState> {
    override val values = sequenceOf(
        WaitingUiState(
            myWaitingList = persistentListOf(
                MyWaitingModel(
                    boothId = 1L,
                    waitingId = 1L,
                    partySize = 4,
                    tel = "010-1234-5678",
                    waitingOrder = 1,
                ),
                MyWaitingModel(
                    boothId = 2L,
                    waitingId = 2L,
                    partySize = 4,
                    tel = "010-1234-5678",
                    waitingOrder = 2,
                ),
                MyWaitingModel(
                    boothId = 3L,
                    waitingId = 3L,
                    partySize = 4,
                    tel = "010-1234-5678",
                    waitingOrder = 3,
                ),
                MyWaitingModel(
                    boothId = 4L,
                    waitingId = 4L,
                    partySize = 4,
                    tel = "010-1234-5678",
                    waitingOrder = 4,
                ),
                MyWaitingModel(
                    boothId = 5L,
                    waitingId = 5L,
                    partySize = 4,
                    tel = "010-1234-5678",
                    waitingOrder = 5,
                ),
            ),
        ),
    )
}
