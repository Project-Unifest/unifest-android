package com.unifest.android.feature.menu.viewmodel

import android.app.Application
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import com.unifest.android.core.domain.entity.BoothDetailEntity
import com.unifest.android.core.domain.entity.MenuEntity
import com.unifest.android.core.domain.entity.Festival
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(private val application: Application) : ViewModel() {
    private val _uiState = MutableStateFlow(MenuUiState())
    val uiState: StateFlow<MenuUiState> = _uiState.asStateFlow()

    init {

        val appVersion = try {
            application.packageManager.getPackageInfo(application.packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            "Unknown"
        }

        _uiState.update {currentState ->
            currentState.copy(
                appVersion = appVersion,
                // 임시 데이터
                festivals = persistentListOf(
                    Festival("school_image_url_1", "서울대학교", "설대축제", "05.06-05.08"),
                    Festival("school_image_url_2", "연세대학교", "연대축제", "05.06-05.08"),
                    Festival("school_image_url_3", "고려대학교", "고대축제", "05.06-05.08"),
                    Festival("school_image_url_4", "건국대학교", "녹색지대", "05.06-05.08"),
                    Festival("school_image_url_5", "성균관대", "성대축제", "05.06-05.08"),
                ),

                interestedBooths = persistentListOf(
                    BoothDetailEntity(
                        id = 1,
                        name = "부스1",
                        category = "카페",
                        description = "부스1 설명",
                        warning = "부스1 주의사항",
                        location = "부스1 위치",
                        latitude = 37.5665f,
                        longitude = 126.9780f,
                        menus = listOf(),
                    ),
                    BoothDetailEntity(
                        id = 2,
                        name = "부스2",
                        category = "카페",
                        description = "부스2 설명",
                        warning = "부스2 주의사항",
                        location = "부스2 위치",
                        latitude = 37.5665f,
                        longitude = 126.9780f,
                        menus = listOf(),
                    ),
                    BoothDetailEntity(
                        id = 3,
                        name = "부스3",
                        category = "카페",
                        description = "부스3 설명",
                        warning = "부스3 주의사항",
                        location = "부스3 위치",
                        latitude = 37.5665f,
                        longitude = 126.9780f,
                        menus = listOf(),
                    ),
                ),
            )
        }
    }

}
