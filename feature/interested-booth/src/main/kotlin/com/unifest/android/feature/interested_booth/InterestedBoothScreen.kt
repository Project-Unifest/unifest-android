package com.unifest.android.feature.interested_booth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.domain.entity.BoothDetailEntity
import com.unifest.android.core.ui.DevicePreview
import com.unifest.android.feature.interested_booth.viewmodel.InterestedBoothUiState
import com.unifest.android.feature.interested_booth.viewmodel.InterestedBoothViewModel
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun InterestedBoothRoute(
    viewModel: InterestedBoothViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    InterestedBoothScreen(uiState = uiState)
}

@Composable
internal fun InterestedBoothScreen(
    uiState: InterestedBoothUiState,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(uiState.interestedBooths.toString())
    }
}

@DevicePreview
@Composable
fun InterestedBoothScreenPreview() {
    UnifestTheme {
        InterestedBoothScreen(
            uiState = InterestedBoothUiState(
                interestedBooths = persistentListOf(
                    BoothDetailEntity(1L, "컴공 주점", "컴퓨터공학부 전용 부스"),
                    BoothDetailEntity(2L, "학생회 부스", "건국대학교 학생회 부스"),
                    BoothDetailEntity(3L, "컴공 주점", "컴퓨터공학부 전용 부스"),
                    BoothDetailEntity(4L, "학생회 부스", "건국대학교 학생회 부스"),
                    BoothDetailEntity(5L, "컴공 주점", "컴퓨터공학부 전용 부스"),
                ),
            ),
        )
    }
}
