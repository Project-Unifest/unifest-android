package com.unifest.android.core.ui.component

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.unifest.android.core.common.utils.formatToString
import com.unifest.android.core.common.utils.toLocalDate
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.component.AutoResizedText
import com.unifest.android.core.designsystem.R as designR
import com.unifest.android.core.designsystem.component.NetworkImage
import com.unifest.android.core.designsystem.theme.Content2
import com.unifest.android.core.designsystem.theme.Content3
import com.unifest.android.core.designsystem.theme.Content4
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

@Composable
fun FestivalItem(
    festival: FestivalModel,
    onFestivalSelected: (FestivalModel) -> Unit,
    modifier: Modifier = Modifier,
    isEditMode: Boolean = false,
    setLikedFestivalDeleteDialogVisible: (FestivalModel) -> Unit = {},
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.scrim),
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .clickable {
                    if (isEditMode) {
                        setLikedFestivalDeleteDialogVisible(festival)
                    } else {
                        onFestivalSelected(festival)
                    }
                },
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                NetworkImage(
                    imgUrl = festival.thumbnail,
                    contentDescription = "Festival Thumbnail",
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape),
                    placeholder = painterResource(id = designR.drawable.item_placeholder),
                )
                Spacer(modifier = Modifier.height(8.dp))
                AutoResizedText(
                    text = festival.schoolName,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    style = Content2,
                )
                Spacer(modifier = Modifier.height(2.dp))
                AutoResizedText(
                    text = festival.festivalName,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    style = Content4,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${festival.beginDate.toLocalDate().formatToString()} - ${festival.endDate.toLocalDate().formatToString()}",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = Content3,
                )
            }
            if (isEditMode) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = designR.drawable.ic_delete_red),
                    contentDescription = "Delete Icon",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 6.dp, end = 6.dp),
                )
            }
        }
    }
}

@ComponentPreview
@Composable
private fun FestivalItemPreview() {
    UnifestTheme {
        FestivalItem(
            festival = FestivalModel(
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
            onFestivalSelected = {},
        )
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
