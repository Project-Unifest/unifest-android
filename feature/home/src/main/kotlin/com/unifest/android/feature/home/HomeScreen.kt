package com.unifest.android.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.ui.DevicePreview

@Composable
internal fun HomeRoute(
    padding: PaddingValues,
    onNavigateToIntro: () -> Unit,
) {
    HomeScreen(
        padding = padding,
        onNavigateToIntro = onNavigateToIntro,
    )
}

@Composable
internal fun HomeScreen(
    padding: PaddingValues,
    onNavigateToIntro: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = padding.calculateBottomPadding()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("Home Screen")
        Button(
            onClick = onNavigateToIntro,
        ) {
            Text("관심 축제 추가하기")
        }
    }
}

@DevicePreview
@Composable
fun HomeScreenPreview() {
    UnifestTheme {
        HomeScreen(
            padding = PaddingValues(0.dp),
            onNavigateToIntro = {},
        )
    }
}
