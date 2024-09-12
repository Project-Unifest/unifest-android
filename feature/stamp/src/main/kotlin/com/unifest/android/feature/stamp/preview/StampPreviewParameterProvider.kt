package com.unifest.android.feature.stamp.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.unifest.android.core.model.StampBoothModel
import com.unifest.android.feature.stamp.viewmodel.StampUiState
import kotlinx.collections.immutable.persistentListOf

internal class StampPreviewParameterProvider : PreviewParameterProvider<StampUiState> {
    override val values = sequenceOf(
        StampUiState(
            stampBoothList = persistentListOf(
                StampBoothModel(
                    id = 0,
                    isChecked = true,
                ),
                StampBoothModel(
                    id = 1,
                    isChecked = true,
                ),
                StampBoothModel(
                    id = 2,
                    isChecked = true,
                ),
                StampBoothModel(
                    id = 3,
                    isChecked = true,
                ),
                StampBoothModel(
                    id = 4,
                    isChecked = false,
                ),
                StampBoothModel(
                    id = 5,
                    isChecked = true,
                ),
                StampBoothModel(
                    id = 6,
                    isChecked = true,
                ),
                StampBoothModel(
                    id = 7,
                    isChecked = false,
                ),
                StampBoothModel(
                    id = 8,
                    isChecked = true,
                ),
                StampBoothModel(
                    id = 9,
                    isChecked = true,
                ),
                StampBoothModel(
                    id = 10,
                    isChecked = false,
                ),
                StampBoothModel(
                    id = 11,
                    isChecked = true,
                ),
            ),
        ),
    )
}
