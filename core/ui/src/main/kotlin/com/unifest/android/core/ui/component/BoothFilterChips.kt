package com.unifest.android.core.ui.component

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.component.BoothFilterChip
import com.unifest.android.core.designsystem.theme.UnifestTheme
import kotlinx.collections.immutable.persistentListOf

val boothFilters = persistentListOf("주점", "먹거리", "이벤트", "일반", "의무실", "화장실")

@Composable
fun BoothFilterChips(
    onChipClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier,
    ) {
        items(
            items = boothFilters,
            key = { it.hashCode() },
        ) {
            BoothFilterChip(
                filterName = it,
                onChipClick = onChipClick,
            )
        }
    }
}

@ComponentPreview
@Composable
fun BoothFilterChipsPreview() {
    UnifestTheme {
        BoothFilterChips(onChipClick = {})
    }
}
