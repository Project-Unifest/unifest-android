package com.unifest.android.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density

private val LightColorScheme = lightColorScheme(
    // 앱의 주요 브랜드 색상, 주요 액션 버튼 등에 사용
    primary = LightPrimary500,
    // primary 색상 위의 텍스트나 아이콘 색상
    onPrimary = Color.White,
    // primary 색상의 더 연한 버전, 백그라운드나 강조 영역에 사용
    primaryContainer = LightPrimary100,
    // primaryContainer 위의 텍스트나 아이콘 색상
    onPrimaryContainer = LightPrimary900,
    // 보조 액션이나 정보를 위한 색상
    secondary = LightBlueGreen,
    // secondary 색상 위의 텍스트나 아이콘 색상
    onSecondary = LightGrey700,
    // secondary 색상의 더 연한 버전, 보조 컨테이너에 사용
    secondaryContainer = LightGrey400,
    // secondaryContainer 위의 텍스트나 아이콘 색상
    onSecondaryContainer = LightGrey400,
    // 대비를 위한 액센트 색상
    tertiary = LightPrimary50,
    // tertiary 색상 위의 텍스트나 아이콘 색상
    onTertiary = Color.White,
    // tertiary 색상의 더 연한 버전
    tertiaryContainer = Color.White,
    // tertiaryContainer 위의 텍스트나 아이콘 색상
    onTertiaryContainer = Color.White,
    // 오류 표시를 위한 색상
    error = LightRed,
    // error 색상 위의 텍스트나 아이콘 색상
    onError = Color.White,
    // error 색상의 더 연한 버전, 오류 메시지 배경 등에 사용
    errorContainer = LightPrimary100,
    // errorContainer 위의 텍스트나 아이콘 색상
    onErrorContainer = LightPrimary900,
    // 앱의 배경색
    background = Color.White,
    // background 위의 텍스트나 아이콘 색상
    onBackground = LightGrey900,
    // 카드, 시트 등 표면 요소의 색상
    surface = Color.White,
    // surface 위의 텍스트나 아이콘 색상
    onSurface = LightGrey500,
    // surface의 변형, 비활성화된 요소나 구분선에 사용
    surfaceVariant = LightGrey600,
    // surface 색상에 적용되는 색조
    surfaceTint = LightRed,
    // surfaceVariant 위의 텍스트나 아이콘 색상
    onSurfaceVariant = LightGrey600,
    // 경계선이나 구분선 등에 사용되는 색상
    outline = LightGrey200,
    // outline의 변형, 덜 강조된 경계선이나 구분선에 사용
    outlineVariant = LightGrey300,
    // 배경을 어둡게 하는 반투명 오버레이, 모달 다이얼로그에 사용
    scrim = LightGrey300,
    // 높은 강조도의 표면 색상, 강조 요소나 카드에 사용
    surfaceBright = LightGrey100,
    // 기본 표면 컨테이너, 카드나 다이얼로그 배경에 사용
    surfaceContainer = Color.White,
    // 높은 대비의 표면 컨테이너, 중요 정보나 액션을 담는 컨테이너
    surfaceContainerHigh = LightPrimary50,
    // 역전된 표면 색상, 어두운 배경 위 밝은 요소를 위해 사용
    inverseSurface = LightGrey100,
    // inverseSurface 위의 텍스트나 밝은 강조에 사용
    inverseOnSurface = DarkGrey300,
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary500,
    onPrimary = Color.White,
    primaryContainer = DarkPrimary700,
    onPrimaryContainer = DarkPrimary50,
    secondary = DarkBlueGreen,
    onSecondary = DarkGrey700,
    secondaryContainer = DarkGrey400,
    onSecondaryContainer = DarkGrey400,
    tertiary = DarkPrimary50,
    onTertiary = Color.White,
    tertiaryContainer = DarkGrey300,
    onTertiaryContainer = DarkGrey300,
    error = DarkRed,
    onError = Color.White,
    errorContainer = DarkPrimary300,
    onErrorContainer = DarkPrimary50,
    background = DarkGrey100,
    onBackground = DarkGrey900,
    surface = DarkGrey200,
    onSurface = DarkGrey500,
    surfaceVariant = DarkGrey600,
    onSurfaceVariant = DarkGrey600,
    surfaceTint = DarkRed,
    outline = DarkGrey200,
    outlineVariant = DarkGrey500,
    scrim = DarkGrey300,
    surfaceBright = DarkGrey200,
    surfaceContainer = DarkGrey100,
    surfaceContainerHigh = DarkGrey300,
    inverseSurface = DarkGrey300,
    inverseOnSurface = LightGrey100,
)

@Composable
fun UnifestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    CompositionLocalProvider(
        LocalDensity provides Density(density = LocalDensity.current.density, fontScale = 1f),
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content,
        )
    }
}
