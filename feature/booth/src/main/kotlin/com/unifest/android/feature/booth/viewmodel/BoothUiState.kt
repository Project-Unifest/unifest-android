package com.unifest.android.feature.booth.viewmodel

import com.naver.maps.geometry.LatLng
import com.unifest.android.core.model.BoothDetailModel
import com.unifest.android.core.model.LikedBoothModel
import com.unifest.android.core.model.MenuModel
import com.unifest.android.core.model.MyWaitingModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class BoothUiState(
    val isLoading: Boolean = false,
    val boothDetailInfo: BoothDetailModel = BoothDetailModel(),
    val likedBooths: ImmutableList<LikedBoothModel> = persistentListOf(),
    val isLiked: Boolean = false,
    val isServerErrorDialogVisible: Boolean = false,
    val isNetworkErrorDialogVisible: Boolean = false,
    val isPinCheckDialogVisible: Boolean = false,
    val isWaitingDialogVisible: Boolean = false,
    val isConfirmDialogVisible: Boolean = false,
    val isMenuImageDialogVisible: Boolean = false,
    val isWrongPinInserted: Boolean = false,
    val selectedMenu: MenuModel? = null,
    val boothPinNumber: String = "",
    val boothPinNumberError: Boolean = false,
    val waitingPartySize: Long = 1,
    val waitingTel: String = "",
    val waitingTeamNumber: Long = 0,
    val waitingId: Long = 0,
    val privacyConsentChecked: Boolean = false,
    val isRunning: Boolean = false,
    val myWaitingList: ImmutableList<MyWaitingModel> = persistentListOf(),
    val outerCords: ImmutableList<LatLng> = persistentListOf(
        LatLng(50.0, 150.0),
        LatLng(50.0, 100.0),
        LatLng(30.0, 100.0),
        LatLng(30.0, 150.0),
    ),
    val innerHole: ImmutableList<LatLng> = persistentListOf(
        LatLng(37.0125281, 127.2598307),
        LatLng(37.0101251, 127.2631513),
        LatLng(37.0095553, 127.2639023),
        LatLng(37.0094097, 127.2642242),
        LatLng(37.0096453, 127.264546),
        LatLng(37.0098295, 127.2649537),
        LatLng(37.0100737, 127.2654687),
        LatLng(37.0102578, 127.2656458),
        LatLng(37.010502, 127.2659032),
        LatLng(37.0112431, 127.2661661),
        LatLng(37.0113802, 127.2661862),
        LatLng(37.0114947, 127.2661782),
        LatLng(37.0122818, 127.2659301),
        LatLng(37.0125174, 127.2658979),
        LatLng(37.0127305, 127.2659019),
        LatLng(37.0130015, 127.2659703),
        LatLng(37.0130464, 127.2657544),
        LatLng(37.0131846, 127.2654915),
        LatLng(37.0132178, 127.2654245),
        LatLng(37.0133816, 127.2649189),
        LatLng(37.0135626, 127.2649269),
        LatLng(37.0136301, 127.2645246),
        LatLng(37.013629, 127.2643958),
        LatLng(37.0134652, 127.2634343),
        LatLng(37.0141269, 127.2632143),
        LatLng(37.0140359, 127.262694),
        LatLng(37.0139781, 127.2626028),
        LatLng(37.013266, 127.2617921),
        LatLng(37.0133602, 127.2616184),
        LatLng(37.0133709, 127.2615789),
        LatLng(37.0128826, 127.2611242),
        LatLng(37.0128044, 127.2610478),
        LatLng(37.0131423, 127.2604087),
        LatLng(37.0125265, 127.2598301)
    ),
)
