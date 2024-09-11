package com.unifest.android.feature.stamp.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.unifest.android.core.model.StampModel
import com.unifest.android.feature.stamp.viewmodel.StampUiState
import kotlinx.collections.immutable.persistentListOf

internal class StampPreviewParameterProvider : PreviewParameterProvider<StampUiState> {
    override val values = sequenceOf(
        StampUiState(
            stampList = persistentListOf(
                StampModel(
                    boothId = 0,
                    isChecked = true,
                ),
                StampModel(
                    boothId = 1,
                    isChecked = true,
                ),
                StampModel(
                    boothId = 2,
                    isChecked = true,
                ),
                StampModel(
                    boothId = 3,
                    isChecked = true,
                ),
                StampModel(
                    boothId = 4,
                    isChecked = false,
                ),
                StampModel(
                    boothId = 5,
                    isChecked = true,
                ),
                StampModel(
                    boothId = 6,
                    isChecked = true,
                ),
                StampModel(
                    boothId = 7,
                    isChecked = false,
                ),
                StampModel(
                    boothId = 8,
                    isChecked = true,
                ),
                StampModel(
                    boothId = 9,
                    isChecked = true,
                ),
                StampModel(
                    boothId = 10,
                    isChecked = false,
                ),
                StampModel(
                    boothId = 11,
                    isChecked = true,
                ),
            ),
        ),
    )
}
