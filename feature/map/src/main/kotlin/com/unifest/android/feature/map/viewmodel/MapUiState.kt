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
    val festivalSearchText: TextFieldValue = TextFieldValue(),
    val likedFestivals: ImmutableList<FestivalModel> = persistentListOf(),
    val festivalSearchResults: ImmutableList<FestivalModel> = persistentListOf(),
    val selectedBoothTypeChips: ImmutableList<String> = persistentListOf("주점", "먹거리", "이벤트", "일반"),
    val filteredBoothsList: ImmutableList<BoothMapModel> = persistentListOf(),
    val deleteSelectedFestival: FestivalModel? = null,
    val isSearchMode: Boolean = false,
    val isEditMode: Boolean = false,
    val isPopularMode: Boolean = false,
    val isBoothSelectionMode: Boolean = false,
    val isMapOnboardingCompleted: Boolean = false,
    val isFestivalOnboardingCompleted: Boolean = true,
    val isFestivalSearchBottomSheetVisible: Boolean = false,
    val isLikedFestivalDeleteDialogVisible: Boolean = false,
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
        LatLng(37.54472, 127.07665),
        LatLng(37.54458, 127.07498),
        LatLng(37.54468, 127.07382),
        LatLng(37.54444, 127.07287),
        LatLng(37.54199, 127.07175),
        LatLng(37.54176, 127.07246),
        LatLng(37.54104, 127.07296),
        LatLng(37.53956, 127.07225),
        LatLng(37.53901, 127.07409),
        LatLng(37.53884, 127.07443),
        LatLng(37.53872, 127.07497),
        LatLng(37.53933, 127.07514),
        LatLng(37.53921, 127.07690),
        LatLng(37.53999, 127.07722),
        LatLng(37.54094, 127.07787),
        LatLng(37.54108, 127.07827),
        LatLng(37.54075, 127.07930),
        LatLng(37.54075, 127.08073),
        LatLng(37.54124, 127.08119),
        LatLng(37.54198, 127.08133),
        LatLng(37.54306, 127.08132),
        LatLng(37.54320, 127.07944),
        LatLng(37.54392, 127.07964),
        LatLng(37.54428, 127.08001),
        LatLng(37.54499, 127.07988),
        LatLng(37.54484, 127.07782),
        LatLng(37.54493, 127.07689),
        LatLng(37.54490, 127.07661),
    ),
)
