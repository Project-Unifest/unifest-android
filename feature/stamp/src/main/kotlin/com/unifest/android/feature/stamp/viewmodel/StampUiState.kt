package com.unifest.android.feature.stamp.viewmodel

import com.unifest.android.core.model.BoothModel
import com.unifest.android.core.model.StampBoothModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class StampUiState(
    val isLoading: Boolean = false,
    val schoolName: String = "한국교통대학교",
    val receivedStamp: Int = 9,
    val stampBoothList: ImmutableList<StampBoothModel> = persistentListOf(
        StampBoothModel(
            id = 0,
            name = "컴공 주점 부스",
            category = "주점",
            description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다.",
            location = "학생회관 옆",
            isChecked = true
        ),
        StampBoothModel(
            id = 1,
            name = "컴공 주점 부스",
            category = "주점",
            description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다.",
            location = "학생회관 옆",
            isChecked = true,
        ),
        StampBoothModel(
            id = 2,
            name = "컴공 주점 부스",
            category = "주점",
            description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다.",
            location = "학생회관 옆",
            isChecked = true,
        ),
        StampBoothModel(
            id = 3,
            name = "컴공 주점 부스",
            category = "주점",
            description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다.",
            location = "학생회관 옆",
            isChecked = true,
        ),
        StampBoothModel(
            id = 4,
            name = "컴공 주점 부스",
            category = "주점",
            description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다.",
            location = "학생회관 옆",
            isChecked = true,
        ),
        StampBoothModel(
            id = 5,
            name = "컴공 주점 부스",
            category = "주점",
            description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다.",
            location = "학생회관 옆",
            isChecked = false,
        ),
        StampBoothModel(
            id = 6,
            name = "컴공 주점 부스",
            category = "주점",
            description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다.",
            location = "학생회관 옆",
            isChecked = true,
        ),
        StampBoothModel(
            id = 7,
            name = "컴공 주점 부스",
            category = "주점",
            description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다.",
            location = "학생회관 옆",
            isChecked = true,
        ),
        StampBoothModel(
            id = 8,
            name = "컴공 주점 부스",
            category = "주점",
            description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다.",
            location = "학생회관 옆",
            isChecked = false,
        ),
        StampBoothModel(
            id = 9,
            name = "컴공 주점 부스",
            category = "주점",
            description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다.",
            location = "학생회관 옆",
            isChecked = true,
        ),
        StampBoothModel(
            id = 10,
            name = "컴공 주점 부스",
            category = "주점",
            description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다.",
            location = "학생회관 옆",
            isChecked = true,
        ),
        StampBoothModel(
            id = 11,
            name = "컴공 주점 부스",
            category = "주점",
            description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다.",
            location = "학생회관 옆",
            isChecked = false,
        ),
    ),
    val isStampBoothDialogVisible: Boolean = false,
    val isPermissionDialogVisible: Boolean = false,
    val isServerErrorDialogVisible: Boolean = false,
    val isNetworkErrorDialogVisible: Boolean = false,
)
