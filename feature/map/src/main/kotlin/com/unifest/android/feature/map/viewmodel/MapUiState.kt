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
    val filteredBoothList: ImmutableList<BoothMapModel> = persistentListOf(),
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
    // TODO 지원하는 학교들의 폴리콘 좌표 전부 추가 팔요
    val innerHole: ImmutableList<LatLng> = persistentListOf(
        LatLng(36.9665013, 127.8746701),
        LatLng(36.9697908, 127.8750296),
        LatLng(36.970273, 127.8755821),
        LatLng(36.9710187, 127.8758047),
        LatLng(36.9712394, 127.8763143),
        LatLng(36.9718673, 127.8762848),
        LatLng(36.9723623, 127.8760488),
        LatLng(36.9730073, 127.8750725),
        LatLng(36.9735752, 127.8749035),
        LatLng(36.9745373, 127.8718216),
        LatLng(36.9749209, 127.8718511),
        LatLng(36.9749573, 127.871717),
        LatLng(36.9749423, 127.8714327),
        LatLng(36.9748866, 127.8712932),
        LatLng(36.9748759, 127.8710518),
        LatLng(36.9748266, 127.8709875),
        LatLng(36.9747516, 127.8709794),
        LatLng(36.974503, 127.871202),
        LatLng(36.9744816, 127.8713871),
        LatLng(36.9742309, 127.871481),
        LatLng(36.9738688, 127.8714676),
        LatLng(36.9734873, 127.8712637),
        LatLng(36.9734595, 127.8712047),
        LatLng(36.9732323, 127.8709392),
        LatLng(36.9727823, 127.870899),
        LatLng(36.9726773, 127.8708185),
        LatLng(36.9709823, 127.8689517),
        LatLng(36.9708559, 127.8688551),
        LatLng(36.9696494, 127.8683455),
        LatLng(36.9691351, 127.8682275),
        LatLng(36.9687129, 127.8682677),
        LatLng(36.9678493, 127.867742),
        LatLng(36.9675857, 127.8676401),
        LatLng(36.9674914, 127.8676428),
        LatLng(36.9667563, 127.8711806),
        LatLng(36.9664756, 127.87246),
        LatLng(36.9664906, 127.8746728),
    ),
)
