package com.unifest.android.feature.intro.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.feature.intro.R
import com.unifest.android.core.designsystem.R as designR
import com.unifest.android.core.designsystem.component.LoadingWheel
import com.unifest.android.core.designsystem.theme.Content1
import com.unifest.android.core.designsystem.theme.Content3
import com.unifest.android.core.designsystem.theme.Content6
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.core.ui.component.FestivalItem
import com.unifest.android.feature.intro.viewmodel.IntroUiAction
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch

@Composable
fun AllFestivalsTabRow(
    festivals: ImmutableList<FestivalModel>,
    isSearchLoading: Boolean,
    onAction: (IntroUiAction) -> Unit,
    selectedFestivals: ImmutableList<FestivalModel>,
    modifier: Modifier = Modifier,
) {
    val tabTitles = LocalContext.current.resources.getStringArray(R.array.region_tab_titles).toList()
    val pagerState = rememberPagerState(pageCount = { tabTitles.size })
    val scope = rememberCoroutineScope()
    val selectedTabIndex by remember { derivedStateOf { pagerState.currentPage } }

    Column(modifier) {
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.background,
            edgePadding = 0.dp,
            indicator = {},
            divider = {
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outline,
                    thickness = 1.75.dp,
                )
            },
        ) {
            tabTitles.forEachIndexed { index, title ->
                val isSelected = selectedTabIndex == index
                val tabTitleColor by animateColorAsState(
                    targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                )
                val tabTitleFontWeight by animateFloatAsState(
                    targetValue = (if (isSelected) FontWeight.Bold.weight else FontWeight.Normal.weight).toFloat(),
                )
                Tab(
                    selected = isSelected,
                    onClick = {
                        if (!isSelected) {
                            onAction(IntroUiAction.OnRegionTapClicked(title))
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    },
                    text = {
                        Text(
                            text = title,
                            color = tabTitleColor,
                            style = Content1,
                            fontWeight = FontWeight(weight = tabTitleFontWeight.toInt()),
                        )
                    },
                )
            }
        }
        Text(
            text = stringResource(id = R.string.total_festivals_count, festivals.size),
            modifier = Modifier
                .padding(start = 20.dp, top = 15.dp, bottom = 15.dp)
                .align(Alignment.Start),
            color = MaterialTheme.colorScheme.onBackground,
            style = Content6,
        )
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                if (festivals.isEmpty()) {
                    Text(
                        text = stringResource(id = designR.string.no_result),
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(bottom = 92.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = Content3,
                    )
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .height(if (festivals.isEmpty()) 0.dp else (((festivals.size - 1) / 3 + 1) * 140).dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(bottom = 48.dp),
                    ) {
                        items(
                            count = festivals.size,
                            key = { index -> festivals[index].festivalId },
                        ) { index ->
                            FestivalItem(
                                festival = festivals[index],
                                onFestivalSelected = { festival ->
                                    if (!selectedFestivals.any { it.festivalId == festival.festivalId }) {
                                        onAction(IntroUiAction.OnFestivalSelected(festival))
                                    }
                                },
                            )
                        }
                    }
                }
                if (isSearchLoading) {
                    LoadingWheel(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                            .background(MaterialTheme.colorScheme.background)
                            .padding(bottom = 92.dp),
                    )
                }
            }
        }
    }
}

@ComponentPreview
@Composable
private fun AllFestivalsTabRowPreview() {
    UnifestTheme {
        AllFestivalsTabRow(
            festivals = persistentListOf(
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
                    "서울대학교",
                    "서울",
                    "설대축제",
                    "2024-04-21",
                    "2024-04-23",
                    126.957f,
                    37.460f,
                ),
                FestivalModel(
                    3,
                    3,
                    "https://picsum.photos/36",
                    "서울대학교",
                    "서울",
                    "설대축제",
                    "2024-04-21",
                    "2024-04-23",
                    126.957f,
                    37.460f,
                ),
            ),
            isSearchLoading = false,
            onAction = {},
            selectedFestivals = persistentListOf(
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
            ),
        )
    }
}
