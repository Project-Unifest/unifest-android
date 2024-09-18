package com.unifest.android.feature.intro.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.theme.Content6
import com.unifest.android.core.designsystem.theme.Title3
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.feature.intro.R
import com.unifest.android.core.designsystem.R as designR
import com.unifest.android.feature.intro.viewmodel.IntroUiAction
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun LikedFestivalsRow(
    selectedFestivals: ImmutableList<FestivalModel>,
    onAction: (IntroUiAction) -> Unit,
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        ) {
            if (selectedFestivals.isNotEmpty()) {
                Text(
                    text = stringResource(id = designR.string.liked_festivals_title),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = Title3,
                )
                TextButton(
                    onClick = { onAction(IntroUiAction.OnClearSelectionClick) },
                ) {
                    Text(
                        text = stringResource(id = R.string.clear_item_button_text),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textDecoration = TextDecoration.Underline,
                        style = Content6,
                    )
                }
            }
        }
        LazyRow(
            modifier = Modifier
                .padding(8.dp)
                .height(if (selectedFestivals.isEmpty()) 0.dp else 130.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                count = selectedFestivals.size,
                key = { index -> selectedFestivals[index].festivalId },
            ) { index ->
                FestivalRowItem(
                    festival = selectedFestivals[index],
                    onAction = onAction,
                )
            }
        }
    }
}

@ComponentPreview
@Composable
private fun LikedFestivalRowPreview() {
    UnifestTheme {
        LikedFestivalsRow(
            selectedFestivals = persistentListOf(
                FestivalModel(
                    festivalId = 0L,
                    schoolName = "건국대",
                    festivalName = "녹색지대",
                    beginDate = "2024-05-21",
                    endDate = "2024-05-23",
                    thumbnail = "",
                ),
            ),
            onAction = {},
        )
    }
}
