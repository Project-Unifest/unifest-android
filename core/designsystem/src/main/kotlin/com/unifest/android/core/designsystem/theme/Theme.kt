package com.unifest.android.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = LightAccent1,
    background = LightBackground,
    onBackground = LightText,
    secondary = LightAccent2,
    tertiary = LightBottomBar,
    onTertiary = LightBottomBarIcon,
    surfaceVariant = LightDisabledComponent,
    error = LightError,

)

private val DarkColorScheme = darkColorScheme(
    primary = DarkAccent1,
    background = DarkBackground,
    onBackground = DarkText,
    secondary =  DarkAccent2,
    tertiary = DarkBottomBar,
    onTertiary = DarkBottomBarIcon,
    surfaceVariant = DarkDisabledComponent,
    error = DarkError,
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
