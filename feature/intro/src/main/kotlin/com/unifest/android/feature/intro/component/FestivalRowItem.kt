package com.unifest.android.feature.intro.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.unifest.android.feature.intro.viewmodel.IntroUiAction

@Composable
internal fun FestivalRowItem(
    festival: FestivalModel,
    onAction: (IntroUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        modifier = modifier.width(120.dp),
    ) {
        Box(
            modifier = Modifier.clickable {
                onAction(IntroUiAction.OnFestivalDeselected(festival))
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
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = festival.schoolName,
                    color = MaterialTheme.colorScheme.onBackground,
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
                    "${festival.beginDate.toLocalDate().formatToString()} - ${festival.endDate.toLocalDate().formatToString()}",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = Content3,
                )
            }
        }
    }
}

@ComponentPreview
@Composable
private fun FestivalRowItemPreview() {
    UnifestTheme {
        FestivalRowItem(
            festival = FestivalModel(
                1,
                1,
                "https://picsum.photos/36",
                "서울대학교",
                "서울",
                "설대축제설대축제설대축제설대축제",
                "2024-04-21",
                "2024-04-23",
                126.957f,
                37.460f,
            ),
            onAction = {},
        )
    }
}
