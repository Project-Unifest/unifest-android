package com.unifest.android.feature.map.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.naver.maps.geometry.LatLng
import com.unifest.android.core.common.HANKYONG_UNIVERSITY_POLYLINE
import com.unifest.android.core.common.KONKUK_UNIVERSITY_POLYLINE
import com.unifest.android.core.common.KOREA_NATIONAL_UNIVERSITY_OF_TRANSPORTATION_POLYLINE
import com.unifest.android.core.common.KOREA_UNIVERSITY_POLYLINE
import com.unifest.android.core.common.SANGMYUNG_UNIVERSITY_POLYLINE
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
    val outerPolygon: ImmutableList<LatLng> = persistentListOf(
        LatLng(50.0, 150.0),
        LatLng(50.0, 100.0),
        LatLng(30.0, 100.0),
        LatLng(30.0, 150.0),
    ),
    val innerPolylines: ImmutableList<ImmutableList<LatLng>> = persistentListOf(
        KONKUK_UNIVERSITY_POLYLINE,
        HANKYONG_UNIVERSITY_POLYLINE,
        KOREA_NATIONAL_UNIVERSITY_OF_TRANSPORTATION_POLYLINE,
        KOREA_UNIVERSITY_POLYLINE,
        SANGMYUNG_UNIVERSITY_POLYLINE,
    ),
)
