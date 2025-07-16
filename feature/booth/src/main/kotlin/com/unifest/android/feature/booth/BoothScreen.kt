package com.unifest.android.feature.booth

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unifest.android.core.common.ObserveAsEvents
import com.unifest.android.feature.booth.viewmodel.BoothUiEvent
import com.unifest.android.feature.booth.viewmodel.BoothViewModel

@Composable
internal fun BoothRoute(
    padding: PaddingValues,
    navigateToBoothDetail: (Long) -> Unit,
    viewModel: BoothViewModel = hiltViewModel(),
) {
    padding
    navigateToBoothDetail
}
