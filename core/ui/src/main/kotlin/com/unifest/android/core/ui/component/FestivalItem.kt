package com.unifest.android.core.ui.component

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.unifest.android.core.designsystem.component.NetworkImage
import com.unifest.android.core.designsystem.theme.Content2
import com.unifest.android.core.designsystem.theme.Content3
import com.unifest.android.core.designsystem.theme.Content4
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.core.designsystem.R as designR

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
                AutoResizedText(
                    text = "${festival.beginDate.toLocalDate().formatToString()} - ${festival.endDate.toLocalDate().formatToString()}",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
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
