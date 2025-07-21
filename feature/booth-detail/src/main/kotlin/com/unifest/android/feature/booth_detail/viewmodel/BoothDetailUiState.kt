package com.unifest.android.feature.booth_detail.viewmodel

import androidx.compose.foundation.text.input.TextFieldState
import com.naver.maps.geometry.LatLng
import com.unifest.android.core.common.HANKYONG_UNIVERSITY_POLYLINE
import com.unifest.android.core.common.KONKUK_UNIVERSITY_POLYLINE
import com.unifest.android.core.common.KOREA_NATIONAL_UNIVERSITY_OF_TRANSPORTATION_POLYLINE
import com.unifest.android.core.common.KOREA_UNIVERSITY_POLYLINE
import com.unifest.android.core.common.SANGMYUNG_UNIVERSITY_POLYLINE
import com.unifest.android.core.model.BoothDetailModel
import com.unifest.android.core.model.LikedBoothModel
import com.unifest.android.core.model.MenuModel
import com.unifest.android.core.model.MyWaitingModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class BoothDetailUiState(
    val isLoading: Boolean = false,
    val boothDetailInfo: BoothDetailModel = BoothDetailModel(),
    val likedBooths: ImmutableList<LikedBoothModel> = persistentListOf(),
    val isLiked: Boolean = false,
    val isServerErrorDialogVisible: Boolean = false,
    val isNetworkErrorDialogVisible: Boolean = false,
    val isPinCheckDialogVisible: Boolean = false,
    val isWaitingDialogVisible: Boolean = false,
    val isConfirmDialogVisible: Boolean = false,
    val isNoShowDialogVisible: Boolean = false,
    val isMenuImageDialogVisible: Boolean = false,
    val isWrongPinInserted: Boolean = false,
    val selectedMenu: MenuModel? = null,
    val boothPinNumber: TextFieldState = TextFieldState(),
    val boothPinNumberError: Boolean = false,
    val waitingPartySize: Long = 1,
    val waitingTel: TextFieldState = TextFieldState(),
    val waitingTeamNumber: Long = 0,
    val waitingId: Long = 0,
    val privacyConsentChecked: Boolean = false,
    val isScheduleExpanded: Boolean = false,
    val myWaitingList: ImmutableList<MyWaitingModel> = persistentListOf(),
    val isNotificationPermissionDialogVisible: Boolean = false,
    val isLocationPermissionDialogVisible: Boolean = false,
    val outerPolygon: ImmutableList<LatLng> = persistentListOf(
        LatLng(50.0, 150.0),
        LatLng(50.0, 100.0),
        LatLng(30.0, 100.0),
        LatLng(30.0, 150.0),
    ),
    val innerPolyLines: ImmutableList<ImmutableList<LatLng>> = persistentListOf(
        KONKUK_UNIVERSITY_POLYLINE,
        HANKYONG_UNIVERSITY_POLYLINE,
        KOREA_NATIONAL_UNIVERSITY_OF_TRANSPORTATION_POLYLINE,
        KOREA_UNIVERSITY_POLYLINE,
        SANGMYUNG_UNIVERSITY_POLYLINE,
    ),
)
