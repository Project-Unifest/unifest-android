package com.unifest.android.feature.stamp.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.composables.core.ScrollArea
import com.composables.core.Thumb
import com.composables.core.VerticalScrollbar
import com.composables.core.rememberScrollAreaState
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.theme.BoothTitle2
import com.unifest.android.core.designsystem.theme.StampSchools
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.model.StampFestivalModel
import com.unifest.android.feature.stamp.R
import com.unifest.android.feature.stamp.viewmodel.stamp.StampUiAction
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun SchoolsDropDownMenu(
    isDropDownMenuOpened: Boolean,
    festivals: ImmutableList<StampFestivalModel>,
    selectedFestival: StampFestivalModel,
    onAction: (StampUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val lazyListState = rememberLazyListState()
    val state = rememberScrollAreaState(lazyListState)

    val offsetInPx = with(LocalDensity.current) { 64.dp.toPx() }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(color = MaterialTheme.colorScheme.surfaceBright)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(8.dp),
                )
                .clickable {
                    onAction(StampUiAction.OnDropDownMenuClick)
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(25.dp))
            Text(
                text = selectedFestival.schoolName,
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = BoothTitle2,
            )
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_down),
                contentDescription = "DropDownMenu Arrow",
                tint = Color.Unspecified,
            )
            Spacer(modifier = Modifier.width(25.dp))
        }
        if (isDropDownMenuOpened) {
            Popup(
                alignment = Alignment.TopStart,
                offset = IntOffset(0, offsetInPx.toInt()),
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outlineVariant,
                            shape = RoundedCornerShape(8.dp),
                        ),
                    shape = RoundedCornerShape(8.dp),
                    shadowElevation = 4.dp,
                ) {
                    ScrollArea(
                        state = state,
                        modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.inverseSurface)
                            .padding(end = 9.dp),
                    ) {
                        LazyColumn(
                            state = lazyListState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(if (festivals.isEmpty()) 0.dp else (festivals.size * 48).dp)
                                .heightIn(max = 240.dp),
                            contentPadding = PaddingValues(vertical = 5.dp),
                        ) {
                            items(
                                items = festivals,
                                key = { it.festivalId },
                            ) { school ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable(
                                            onClick = { onAction(StampUiAction.OnFestivalSelect(school)) },
                                        )
                                        .background(color = MaterialTheme.colorScheme.inverseSurface)
                                        .padding(horizontal = 25.dp, vertical = 12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        text = school.schoolName,
                                        style = StampSchools,
                                        color = MaterialTheme.colorScheme.inverseOnSurface,
                                    )
                                }
                            }
                        }
                        VerticalScrollbar(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .height(if (festivals.isEmpty()) 0.dp else (festivals.size * 48).dp)
                                .heightIn(max = 240.dp)
                                .width(7.dp),
                        ) {
                            Thumb(
                                modifier = Modifier
                                    .padding(vertical = 9.dp)
                                    .clip(RoundedCornerShape(7.dp))
                                    .background(MaterialTheme.colorScheme.surfaceContainerLow),
                            )
                        }
                    }
                }
            }
        }
    }
}

@ComponentPreview
@Composable
private fun SchoolsDropDownMenuPreview() {
    UnifestTheme {
        SchoolsDropDownMenu(
            isDropDownMenuOpened = true,
            festivals = persistentListOf(
                StampFestivalModel(1, "서울시립대학교", "", ""),
                StampFestivalModel(2, "한국교통대학교", "", ""),
                StampFestivalModel(3, "한양대학교", "", ""),
                StampFestivalModel(4, "고려대학교", "", ""),
                StampFestivalModel(5, "홍익대학교", "", ""),
            ),
            selectedFestival = StampFestivalModel(1, "서울시립대학교", "", ""),
            onAction = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        )
    }
}
