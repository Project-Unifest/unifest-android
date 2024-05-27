package com.unifest.android.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = MainColor,
    onPrimary = Color.White,
    background = Background,
    onBackground = Color.White,
    secondary =  Secondary_dark,
    onSecondary = OnSecondary_dark,
    scrim = Color.Black,

)

private val LightColorScheme = lightColorScheme(
    primary = MainColor,
    onPrimary = Color.White,
    background = Color.White,
    onBackground = Color.Black,
    secondary = Secondary,
    onSecondary = OnSecondary,
    scrim = Color.Black,
)

@Composable
fun UnifestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}
