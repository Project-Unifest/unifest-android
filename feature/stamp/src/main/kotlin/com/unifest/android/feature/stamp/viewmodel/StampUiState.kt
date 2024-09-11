package com.unifest.android.feature.stamp.viewmodel

import com.unifest.android.core.model.MyWaitingModel
import com.unifest.android.core.model.StampModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class StampUiState(
    val isLoading: Boolean = false,
    val receivedStamp: Int = 9,
    val stampList: ImmutableList<StampModel> = persistentListOf(
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
        ),StampModel(
            boothId = 7,
            isChecked = false,
        ),StampModel(
            boothId = 8,
            isChecked = true,
        ),StampModel(
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
    val isStampBoothDialogVisible: Boolean = false,
    val isServerErrorDialogVisible: Boolean = false,
    val isNetworkErrorDialogVisible: Boolean = false,
)
