package com.unifest.android.feature.festival

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.unifest.android.core.common.extension.noRippleClickable
import com.unifest.android.core.common.utils.formatToString
import com.unifest.android.core.common.utils.toLocalDate
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.DarkComponentPreview
import com.unifest.android.core.designsystem.component.NetworkImage
import com.unifest.android.core.designsystem.component.UnifestOutlinedButton
import com.unifest.android.core.designsystem.theme.Content2
import com.unifest.android.core.designsystem.theme.Content3
import com.unifest.android.core.designsystem.theme.Content4
import com.unifest.android.core.designsystem.theme.Content6
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.feature.festival.viewmodel.FestivalUiAction
import com.unifest.android.core.designsystem.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun FestivalSearchResults(
    searchResults: ImmutableList<FestivalModel>,
    onFestivalUiAction: (FestivalUiAction) -> Unit,
    likedFestivals: ImmutableList<FestivalModel> = persistentListOf(),
) {
    if (searchResults.isEmpty()) {
        Column {
            Spacer(modifier = Modifier.height(212.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(id = R.string.no_result),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = Content3,
                )
            }
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(top = 16.dp, start = 20.dp, end = 20.dp),
        ) {
            item {
                Row {
                    Text(
                        text = stringResource(id = R.string.search_result),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = Content6,
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "총 ${searchResults.size}개",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = Content6,
                    )
                }
            }
            items(
                count = searchResults.size,
                key = { index -> searchResults[index].festivalId },
            ) { index ->
                Column {
                    FestivalSearchResultItem(
                        festival = searchResults[index],
                        onFestivalUiAction = onFestivalUiAction,
                        likedFestivals = likedFestivals,
                    )
                    if (index != searchResults.size - 1) {
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.outline,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FestivalSearchResultItem(
    festival: FestivalModel,
    onFestivalUiAction: (FestivalUiAction) -> Unit,
    likedFestivals: ImmutableList<FestivalModel>,
) {
    val isFavorite = likedFestivals.any { it.festivalId == festival.festivalId }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(start = 8.dp, top = 9.dp, bottom = 14.dp, end = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        NetworkImage(
            imgUrl = festival.thumbnail,
            contentDescription = "Festival Thumbnail",
            modifier = Modifier
                .size(54.dp)
                .clip(CircleShape),
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                text = festival.schoolName,
                color = MaterialTheme.colorScheme.onBackground,
                style = Content2,
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = festival.festivalName,
                color = MaterialTheme.colorScheme.onBackground,
                style = Content4,
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = "${festival.beginDate.toLocalDate().formatToString()} - ${festival.endDate.toLocalDate().formatToString()}",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = Content3,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        if (isFavorite) {
            UnifestOutlinedButton(
                onClick = {},
                cornerRadius = 17.dp,
                borderColor = Color(0xFFDDDDDD),
                contentColor = Color(0xFF666666),
                contentPadding = PaddingValues(horizontal = 17.dp),
                enabled = false,
                modifier = Modifier
                    .defaultMinSize(
                        minWidth = ButtonDefaults.MinWidth,
                        minHeight = 29.dp,
                    )
                    .noRippleClickable {},
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_check),
                    contentDescription = "Checked",
                    tint = Color(0xFF666666),
                )
            }
        } else {
            UnifestOutlinedButton(
                onClick = { onFestivalUiAction(FestivalUiAction.OnAddClick(festival)) },
                cornerRadius = 17.dp,
                borderColor = Color(0xFFDDDDDD),
                contentColor = Color(0xFF666666),
                contentPadding = PaddingValues(horizontal = 17.dp),
                modifier = Modifier.defaultMinSize(
                    minWidth = ButtonDefaults.MinWidth,
                    minHeight = 29.dp,
                ),
            ) {
                Text(
                    text = stringResource(id = R.string.add),
                    style = Content3,
                )
            }
        }
    }
}

@ComponentPreview
@Composable
fun FestivalSearchResultsPreview() {
    FestivalSearchResults(
        searchResults = persistentListOf(
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
            FestivalModel(
                2,
                2,
                "https://picsum.photos/36",
                "연세대학교",
                "서울",
                "연대축제",
                "2024-04-21",
                "2024-04-23",
                126.957f,
                37.460f,
            ),
            FestivalModel(
                3,
                3,
                "https://picsum.photos/36",
                "고려대학교",
                "서울",
                "고대축제",
                "2024-04-21",
                "2024-04-23",
                126.957f,
                37.460f,
            ),
            FestivalModel(
                4,
                4,
                "https://picsum.photos/36",
                "성균관대학교",
                "서울",
                "성대축제",
                "2024-04-21",
                "2024-04-23",
                126.957f,
                37.460f,
            ),
            FestivalModel(
                5,
                5,
                "https://picsum.photos/36",
                "건국대학교",
                "서울",
                "건대축제",
                "2024-04-21",
                "2024-04-23",
                126.957f,
                37.460f,
            ),
        ),
        onFestivalUiAction = {},
    )
}

@DarkComponentPreview
@Composable
fun FestivalSearchResultsDarkPreview() {
    FestivalSearchResults(
        searchResults = persistentListOf(
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
            FestivalModel(
                2,
                2,
                "https://picsum.photos/36",
                "연세대학교",
                "서울",
                "연대축제",
                "2024-04-21",
                "2024-04-23",
                126.957f,
                37.460f,
            ),
            FestivalModel(
                3,
                3,
                "https://picsum.photos/36",
                "고려대학교",
                "서울",
                "고대축제",
                "2024-04-21",
                "2024-04-23",
                126.957f,
                37.460f,
            ),
            FestivalModel(
                4,
                4,
                "https://picsum.photos/36",
                "성균관대학교",
                "서울",
                "성대축제",
                "2024-04-21",
                "2024-04-23",
                126.957f,
                37.460f,
            ),
            FestivalModel(
                5,
                5,
                "https://picsum.photos/36",
                "건국대학교",
                "서울",
                "건대축제",
                "2024-04-21",
                "2024-04-23",
                126.957f,
                37.460f,
            ),
        ),
        onFestivalUiAction = {},
    )
}
