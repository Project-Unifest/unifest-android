package com.unifest.android.core.ui.component

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.component.NetworkImage
import com.unifest.android.core.designsystem.component.UnifestOutlinedButton
import com.unifest.android.core.designsystem.theme.Content2
import com.unifest.android.core.designsystem.theme.Content3
import com.unifest.android.core.designsystem.theme.Content4
import com.unifest.android.core.designsystem.theme.Content6
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.model.FestivalModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun FestivalSearchResults(
    searchResults: ImmutableList<FestivalModel>,
    addLikeFestivalAtBottomSheetSearch: (FestivalModel) -> Unit,
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
                    color = Color(0xFF7E7E7E),
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
                        color = Color(0xFFABABAB),
                        style = Content6,
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "총 ${searchResults.size}개",
                        color = Color(0xFF191919),
                        style = Content6,
                    )
                }
            }
            items(
                count = searchResults.size,
                key = { index -> searchResults[index].festivalId },
            ) {
                Column {
                    FestivalSearchResultItem(
                        festival = searchResults[it],
                        addLikeFestivalAtBottomSheetSearch = addLikeFestivalAtBottomSheetSearch,
                    )
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color(0xFFDFDFDF),
                    )
                }
            }
        }
    }
}

@Composable
fun FestivalSearchResultItem(
    festival: FestivalModel,
    addLikeFestivalAtBottomSheetSearch: (FestivalModel) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, top = 9.dp, bottom = 14.dp, end = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        NetworkImage(
            imageUrl = "https://picsum.photos/54",
            modifier = Modifier
                .size(54.dp)
                .clip(CircleShape),
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                text = festival.schoolName,
                style = Content2,
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = festival.festivalName,
                style = Content4,
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = festival.beginDate + " - " + festival.endDate,
                color = Color(0xFF4D4D4D),
                style = Content3,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        UnifestOutlinedButton(
            onClick = { addLikeFestivalAtBottomSheetSearch(festival) },
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
                "설대축제",
                "05.06",
                "05.08",
                126.957f,
                37.460f,
            ),
            FestivalModel(
                2,
                2,
                "https://picsum.photos/36",
                "연세대학교",
                "연대축제",
                "05.06",
                "05.08",
                126.957f,
                37.460f,
            ),
            FestivalModel(
                3,
                3,
                "https://picsum.photos/36",
                "고려대학교",
                "고대축제",
                "05.06",
                "05.08",
                126.957f,
                37.460f,
            ),
            FestivalModel(
                4,
                4,
                "https://picsum.photos/36",
                "성균관대학교",
                "성대축제",
                "05.06",
                "05.08",
                126.957f,
                37.460f,
            ),
            FestivalModel(
                5,
                5,
                "https://picsum.photos/36",
                "건국대학교",
                "건대축제",
                "05.06",
                "05.08",
                126.957f,
                37.460f,
            ),
        ),
        addLikeFestivalAtBottomSheetSearch = {},
    )
}
