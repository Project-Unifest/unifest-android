package com.unifest.android.feature.waiting

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
internal fun WaitingRoute() {
    WaitingScreen()
}

@Composable
internal fun WaitingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("Waiting Screen")
    }
}

@DevicePreview
@Composable
fun WaitingScreenPreview() {
    UnifestTheme {
        WaitingScreen()
    }
}
