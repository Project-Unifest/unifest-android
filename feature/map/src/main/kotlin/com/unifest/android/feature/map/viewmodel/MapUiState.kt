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
        LatLng(37.54470, 127.07615),
        LatLng(37.54461, 127.07561),
        LatLng(37.54478, 127.07553),
        LatLng(37.54462, 127.07507),
        LatLng(37.54461, 127.07455),
        LatLng(37.54470, 127.07396),
        LatLng(37.54462, 127.07348),
        LatLng(37.54473, 127.07293),
        LatLng(37.54195, 127.07162),
        LatLng(37.54183, 127.07218),
        LatLng(37.54100, 127.07311),
        LatLng(37.53950, 127.07238),
        LatLng(37.53873, 127.07504),
        LatLng(37.53933, 127.07516),
        LatLng(37.53919, 127.07674),
        LatLng(37.53908, 127.07719),
        LatLng(37.53910, 127.07839),
        LatLng(37.53900, 127.07850),
        LatLng(37.53902, 127.07903),
        LatLng(37.53886, 127.07906),
        LatLng(37.53891, 127.07995),
        LatLng(37.53925, 127.07993),
        LatLng(37.53934, 127.07973),
        LatLng(37.53982, 127.07962),
        LatLng(37.54014, 127.07999),
        LatLng(37.54067, 127.08086),
        LatLng(37.54119, 127.08131),
        LatLng(37.54208, 127.08131),
        LatLng(37.54234, 127.08115),
        LatLng(37.54257, 127.07857),
        LatLng(37.54382, 127.07876),
        LatLng(37.54394, 127.07966),
        LatLng(37.54429, 127.08006),
        LatLng(37.54496, 127.07994),
        LatLng(37.54493, 127.07897),
        LatLng(37.54485, 127.07754),
        LatLng(37.54494, 127.07704),
    ),
)
