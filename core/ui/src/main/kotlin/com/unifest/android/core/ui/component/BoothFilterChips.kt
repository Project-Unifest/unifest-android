package com.unifest.android.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.component.BoothFilterChip
import com.unifest.android.core.designsystem.theme.UnifestTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

val boothFilters = persistentListOf("주점", "먹거리", "이벤트", "일반", "의무실", "화장실")

@Composable
fun BoothFilterChips(
    onChipClick: (String) -> Unit,
    selectedChips: ImmutableList<String>,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
    ) {
        items(
            items = boothFilters,
            key = { it.hashCode() },
        ) {
            BoothFilterChip(
                filterName = it,
                onChipClick = onChipClick,
                isSelected = selectedChips.contains(it),
            )
        }
    }
}

@ComponentPreview
@Composable
fun BoothFilterChipsPreview() {
    UnifestTheme {
        BoothFilterChips(
            onChipClick = {},
            selectedChips = persistentListOf("주점", "먹거리"),
        )
    }
}
