package com.unifest.android.feature.map.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.naver.maps.geometry.LatLng
import com.unifest.android.core.model.BoothModel
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.feature.map.model.BoothMapModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MapUiState(
    val festivalInfo: FestivalModel = FestivalModel(),
    val festivals: ImmutableList<FestivalModel> = persistentListOf(),
    val boothList: ImmutableList<BoothMapModel> = persistentListOf(),
    val popularBoothList: ImmutableList<BoothModel> = persistentListOf(),
    val selectedBoothList: ImmutableList<BoothMapModel> = persistentListOf(),
    val boothSearchText: TextFieldValue = TextFieldValue(),
    val festivalSearchResults: ImmutableList<FestivalModel> = persistentListOf(),
    val selectedBoothTypeChips: ImmutableList<String> = persistentListOf("주점", "먹거리", "이벤트", "일반"),
    val filteredBoothsList: ImmutableList<BoothMapModel> = persistentListOf(),
    val isPopularMode: Boolean = false,
    val isBoothSelectionMode: Boolean = false,
    val isMapOnboardingCompleted: Boolean = false,
    val isServerErrorDialogVisible: Boolean = false,
    val isNetworkErrorDialogVisible: Boolean = false,
    val isPermissionDialogVisible: Boolean = false,
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
