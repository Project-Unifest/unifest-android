package com.unifest.android.feature.interested_booth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.ui.DevicePreview

@Composable
internal fun InterestedBoothRoute() {
    InterestedBoothScreen()
}

@Composable
internal fun InterestedBoothScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("Interested Booth Screen")
    }
}

@DevicePreview
@Composable
fun InterestedBoothScreenPreview() {
    UnifestTheme {
        InterestedBoothScreen()
    }
}
