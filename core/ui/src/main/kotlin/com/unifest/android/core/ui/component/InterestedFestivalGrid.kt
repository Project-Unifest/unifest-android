package com.unifest.android.core.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.component.NetworkImage
import com.unifest.android.core.designsystem.theme.Content2
import com.unifest.android.core.designsystem.theme.Content3
import com.unifest.android.core.designsystem.theme.Content4
import com.unifest.android.core.designsystem.theme.Title3
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.domain.entity.Festival

@Composable
fun InterestedFestivalsGrid(
    selectedFestivals: MutableList<Festival>,
    onFestivalSelected: (Festival) -> Unit,
    isEditMode: Boolean = false,
    setInterestedFestivalDeleteDialogVisible: (Boolean) -> Unit = {},
    optionTextButton: @Composable () -> Unit,
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        ) {
            Text(
                text = stringResource(id = R.string.intro_interested_festivals_title),
                style = Title3,
            )
            optionTextButton()
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .padding(8.dp)
                // TODO 높이 조정 로직 수정 필요
                .height(
                    when {
                        selectedFestivals.isEmpty() -> 0.dp
                        else -> {
                            val rows = ((selectedFestivals.size - 1) / 3 + 1) * 180
                            rows.dp
                        }
                    },
                ),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                count = selectedFestivals.size,
                key = { index -> selectedFestivals[index].schoolName },
            ) { index ->
                FestivalItem(
                    festival = selectedFestivals[index],
                    onFestivalSelected = {
                        onFestivalSelected(it)
                    },
                    isEditMode = isEditMode,
                    setInterestedFestivalDeleteDialogVisible = setInterestedFestivalDeleteDialogVisible,
                )
            }
        }
    }
}

@Composable
fun FestivalItem(
    festival: Festival,
    onFestivalSelected: (Festival) -> Unit,
    isEditMode: Boolean = false,
    setInterestedFestivalDeleteDialogVisible: (Boolean) -> Unit = {},
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color.Black),
        border = BorderStroke(1.dp, Color(0xFFD9D9D9)),
        modifier = Modifier,
    ) {
        Box(
            modifier = Modifier
                .clickable {
                    if (isEditMode) {
                        setInterestedFestivalDeleteDialogVisible(true)
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
                    imageUrl = festival.imgUrl,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = festival.schoolName,
                    color = Color.Black,
                    style = Content2,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = festival.festivalName,
                    color = Color.Black,
                    style = Content4,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    festival.festivalDate,
                    color = Color(0xFF979797),
                    style = Content3,
                )
            }
            if (isEditMode) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_delete_red),
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
fun InterestedFestivalsGridPreview() {
    val selectedFestivals = mutableListOf<Festival>()
    repeat(5) {
        selectedFestivals.add(
            Festival(
                schoolName = "건국대학교",
                festivalName = "녹색지대",
                festivalDate = "05.06-05.08",
                imgUrl = "https://picsum.photos/36",
            ),
        )
    }
    UnifestTheme {
        InterestedFestivalsGrid(
            selectedFestivals = mutableListOf(
                Festival(
                    schoolName = "건국대학교",
                    festivalName = "녹색지대",
                    festivalDate = "05.06-05.08",
                    imgUrl = "https://picsum.photos/36",
                ),
                Festival(
                    schoolName = "홍익대학교",
                    festivalName = "녹색지대",
                    festivalDate = "05.06-05.08",
                    imgUrl = "https://picsum.photos/36",
                ),
                Festival(
                    schoolName = "중앙대학교",
                    festivalName = "녹색지대",
                    festivalDate = "05.06-05.08",
                    imgUrl = "https://picsum.photos/36",
                ),
                Festival(
                    schoolName = "서울과기대학교",
                    festivalName = "녹색지대",
                    festivalDate = "05.06-05.08",
                    imgUrl = "https://picsum.photos/36",
                ),
                Festival(
                    schoolName = "서울시립대학교",
                    festivalName = "녹색지대",
                    festivalDate = "05.06-05.08",
                    imgUrl = "https://picsum.photos/36",
                ),
            ),
            onFestivalSelected = {},
            optionTextButton = {
                Text(
                    text = "편집",
                    style = Content3,
                )
            },
        )
    }
}

@ComponentPreview
@Composable
fun InterestedFestivalsGridEditModePreview() {
    val selectedFestivals = mutableListOf<Festival>()
    repeat(5) {
        selectedFestivals.add(
            Festival(
                schoolName = "건국대학교",
                festivalName = "녹색지대",
                festivalDate = "05.06-05.08",
                imgUrl = "https://picsum.photos/36",
            ),
        )
    }
    UnifestTheme {
        InterestedFestivalsGrid(
            selectedFestivals = mutableListOf(
                Festival(
                    schoolName = "건국대학교",
                    festivalName = "녹색지대",
                    festivalDate = "05.06-05.08",
                    imgUrl = "https://picsum.photos/36",
                ),
                Festival(
                    schoolName = "홍익대학교",
                    festivalName = "녹색지대",
                    festivalDate = "05.06-05.08",
                    imgUrl = "https://picsum.photos/36",
                ),
                Festival(
                    schoolName = "중앙대학교",
                    festivalName = "녹색지대",
                    festivalDate = "05.06-05.08",
                    imgUrl = "https://picsum.photos/36",
                ),
                Festival(
                    schoolName = "서울과기대학교",
                    festivalName = "녹색지대",
                    festivalDate = "05.06-05.08",
                    imgUrl = "https://picsum.photos/36",
                ),
                Festival(
                    schoolName = "서울시립대학교",
                    festivalName = "녹색지대",
                    festivalDate = "05.06-05.08",
                    imgUrl = "https://picsum.photos/36",
                ),
            ),
            onFestivalSelected = {},
            optionTextButton = {
                Text(
                    text = "편집",
                    style = Content3,
                )
            },
            isEditMode = true,
        )
    }
}
