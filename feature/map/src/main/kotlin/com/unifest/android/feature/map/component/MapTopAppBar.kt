package com.unifest.android.feature.map.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.component.SearchTextField
import com.unifest.android.core.designsystem.component.TopAppBarNavigationType
import com.unifest.android.core.designsystem.component.UnifestTopAppBar
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.ui.component.BoothFilterChips
import com.unifest.android.feature.festival.viewmodel.FestivalUiAction
import com.unifest.android.feature.map.R
import com.unifest.android.feature.map.viewmodel.MapUiAction
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun MapTopAppBar(
    title: String,
    boothSearchText: TextFieldValue,
    isOnboardingCompleted: Boolean,
    onMapUiAction: (MapUiAction) -> Unit,
    onFestivalUiAction: (FestivalUiAction) -> Unit,
    selectedChips: ImmutableList<String>,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(
            bottomStart = 32.dp,
            bottomEnd = 32.dp,
        ),
    ) {
        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
        ) {
            UnifestTopAppBar(
                navigationType = TopAppBarNavigationType.Search,
                title = title,
                onTitleClick = { onFestivalUiAction(FestivalUiAction.OnAddLikedFestivalClick) },
                isOnboardingCompleted = isOnboardingCompleted,
                onTooltipClick = { onMapUiAction(MapUiAction.OnTooltipClick) },
            )
            SearchTextField(
                searchText = boothSearchText,
                updateSearchText = { text -> onMapUiAction(MapUiAction.OnSearchTextUpdated(text)) },
                searchTextHintRes = R.string.map_booth_search_text_field_hint,
                onSearch = { onMapUiAction(MapUiAction.OnSearch(boothSearchText)) },
                clearSearchText = { onMapUiAction(MapUiAction.OnSearchTextCleared) },
                modifier = Modifier
                    .height(46.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
            )
            Spacer(modifier = Modifier.height(10.dp))
            BoothFilterChips(
                onChipClick = { chip -> onMapUiAction(MapUiAction.OnBoothTypeChipClick(chip)) },
                selectedChips = selectedChips,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .clip(RoundedCornerShape(16.dp)),
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@ComponentPreview
@Composable
private fun MapTopAppBarPreview() {
    UnifestTheme {
        MapTopAppBar(
            title = "건국대학교",
            boothSearchText = TextFieldValue(),
            isOnboardingCompleted = false,
            onMapUiAction = {},
            onFestivalUiAction = {},
            selectedChips = persistentListOf("주점", "먹거리"),
        )
    }
}
