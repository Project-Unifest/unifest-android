package com.unifest.android.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.unifest.android.core.common.utils.formatWithDayOfWeek
import com.unifest.android.core.common.utils.toLocalDate
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.theme.Content4
import com.unifest.android.core.designsystem.theme.Content5
import com.unifest.android.core.designsystem.theme.DarkBlueGreen
import com.unifest.android.core.designsystem.theme.LightBlueGreen
import com.unifest.android.core.designsystem.theme.Title2
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.model.FestivalTodayModel
import com.unifest.android.core.ui.component.StarImage
import com.unifest.android.feature.home.viewmodel.HomeUiAction
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import com.unifest.android.core.designsystem.R as designR

@Composable
internal fun FestivalScheduleItem(
    festival: FestivalTodayModel,
    scheduleIndex: Int,
//    likedFestivals: ImmutableList<FestivalModel>,
    selectedDate: LocalDate,
    // isStarImageClicked: ImmutableList<Boolean>,
    isDataReady: Boolean,
    onHomeUiAction: (HomeUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .height(72.dp)
                    .background(if (isSystemInDarkTheme()) DarkBlueGreen else LightBlueGreen)
                    .align(Alignment.CenterVertically),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.width(172.dp),
            ) {
                Text(
                    text = "${festival.beginDate.toLocalDate().formatWithDayOfWeek()} - ${festival.endDate.toLocalDate().formatWithDayOfWeek()}",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = Content4,
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row {
                    Text(
                        text = festival.festivalName + " Day ",
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = Title2,
                    )
                    if (isDataReady) {
                        Text(
                            text = (ChronoUnit.DAYS.between(festival.beginDate.toLocalDate(), selectedDate) + 1).toString(),
                            color = MaterialTheme.colorScheme.onBackground,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = Title2,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(7.dp))
                Row {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = designR.drawable.ic_location_grey),
                        contentDescription = "Location Icon",
                        modifier = Modifier
                            .size(10.dp)
                            .align(Alignment.CenterVertically),
                        tint = Color.Unspecified,
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = festival.schoolName,
                        style = Content5,
                        color = MaterialTheme.colorScheme.onSecondary,
                    )
                }
            }
            if (festival.starInfo.isNotEmpty()) {
                LazyRow {
                    itemsIndexed(
                        items = festival.starInfo,
                        key = { _, starInfo -> starInfo.starId },
                    ) { starIndex, starInfo ->
                        StarImage(
                            imgUrl = starInfo.imgUrl,
                            onClick = {
                                onHomeUiAction(HomeUiAction.OnStarImageClick(scheduleIndex, starIndex))
                            },
//                            onLongClick = {
//                                onHomeUiAction(HomeUiAction.OnStarImageLongClick(scheduleIndex, starIndex))
//                            },
//                            isClicked = isStarImageClicked[starIndex],
//                            label = starInfo.name,
                            modifier = Modifier
                                .size(72.dp)
                                .clip(CircleShape),
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
            }
        }
//        if (!likedFestivals.any { it.festivalId == festival.festivalId }) {
//            UnifestOutlinedButton(
//                onClick = {
//                    onHomeUiAction(HomeUiAction.OnAddAsLikedFestivalClick(festival))
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(48.dp)
//                    .padding(top = 16.dp, start = 20.dp, end = 20.dp),
//                contentPadding = PaddingValues(6.dp),
//            ) {
//                Text(
//                    text = stringResource(id = R.string.home_add_interest_festival_in_item_button),
//                    style = BoothLocation,
//                )
//            }
//        }
    }
}

@ComponentPreview
@Composable
private fun FestivalScheduleItemPreview() {
    UnifestTheme {
        FestivalScheduleItem(
            festival = FestivalTodayModel(
                festivalId = 1,
                schoolId = 1,
                festivalName = "대동제",
                schoolName = "건국대",
                beginDate = "2024-05-21",
                endDate = "2024-05-23",
                starInfo = emptyList(),
                thumbnail = "",
            ),
            scheduleIndex = 0,
//            likedFestivals = persistentListOf(),
            selectedDate = LocalDate.now(),
//            isStarImageClicked = persistentListOf(),
            isDataReady = true,
            onHomeUiAction = {},
        )
    }
}
