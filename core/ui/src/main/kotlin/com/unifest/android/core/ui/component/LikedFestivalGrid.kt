package com.unifest.android.core.ui.component

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.model.FestivalModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LikedFestivalsGrid(
    selectedFestivals: ImmutableList<FestivalModel>,
    onFestivalSelected: (FestivalModel) -> Unit,
    onDeleteLikedFestivalClick: (FestivalModel) -> Unit,
    isEditMode: Boolean = false,
) {
    Column {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .wrapContentHeight()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                count = selectedFestivals.size,
                key = { index -> selectedFestivals[index].festivalId },
            ) { index ->
                FestivalItem(
                    festival = selectedFestivals[index],
                    onFestivalSelected = {
                        onFestivalSelected(it)
                    },
                    isEditMode = isEditMode,
                    setLikedFestivalDeleteDialogVisible = onDeleteLikedFestivalClick,
                    modifier = Modifier.animateItemPlacement(
                        animationSpec = tween(
                            durationMillis = 500,
                            easing = LinearOutSlowInEasing,
                        ),
                    ),
                )
            }
        }
    }
}

@ComponentPreview
@Composable
private fun LikedFestivalsGridPreview() {
    val selectedFestivals = persistentListOf<FestivalModel>()
    repeat(5) {
        selectedFestivals.add(
            FestivalModel(
                1,
                1,
                "https://picsum.photos/36",
                "서울대학교",
                "서울",
                "설대축제",
                "2024-04-21",
                "2024-04-23",
                126.957f,
                37.460f,
            ),
        )
    }
    UnifestTheme {
        LikedFestivalsGrid(
            selectedFestivals = selectedFestivals,
            onFestivalSelected = {},
            onDeleteLikedFestivalClick = {},
        )
    }
}

@ComponentPreview
@Composable
private fun LikedFestivalsGridEditModePreview() {
    val selectedFestivals = persistentListOf<FestivalModel>()
    repeat(5) {
        selectedFestivals.add(
            FestivalModel(
                1,
                1,
                "https://picsum.photos/36",
                "서울대학교",
                "서울",
                "설대축제",
                "2024-04-21",
                "2024-04-23",
                126.957f,
                37.460f,
            ),
        )
    }
    UnifestTheme {
        LikedFestivalsGrid(
            selectedFestivals = selectedFestivals,
            onFestivalSelected = {},
            onDeleteLikedFestivalClick = {},
            isEditMode = true,
        )
    }
}
