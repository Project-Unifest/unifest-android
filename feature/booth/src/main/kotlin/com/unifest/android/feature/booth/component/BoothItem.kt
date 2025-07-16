package com.unifest.android.feature.booth.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.component.NetworkImage
import com.unifest.android.core.designsystem.theme.Content2
import com.unifest.android.core.designsystem.theme.Title2
import com.unifest.android.core.designsystem.theme.Title5
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.model.BoothTabModel
import com.unifest.android.core.designsystem.R as designR

@Composable
internal fun BoothItem(
    booth: BoothTabModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Row {
            NetworkImage(
                imgUrl = booth.thumbnail,
                contentDescription = "Stamp Booth Thumbnail",
                modifier = Modifier
                    .size(86.dp)
                    .clip(RoundedCornerShape(16.dp)),
                placeholder = painterResource(id = designR.drawable.item_placeholder),
            )
            Spacer(modifier = Modifier.width(14.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            ) {
                Text(
                    text = booth.name,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = Title2,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = booth.description,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = Content2,
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = designR.drawable.ic_location_green),
                        contentDescription = "Location Icon",
                        tint = Color.Unspecified,
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = booth.location,
                        modifier = Modifier.align(Alignment.CenterVertically),
                        color = MaterialTheme.colorScheme.onSecondary,
                        style = Title5,
                    )
                }
            }
        }
    }
}

@ComponentPreview
@Composable
private fun StampBoothItemPreview() {
    UnifestTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(20.dp),
        ) {
            BoothItem(
                booth = BoothTabModel(
                    id = 1,
                    name = "컴공 주점",
                    description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다",
                    location = "학생회관 앞",
                    waitingEnabled = true,
                    thumbnail = "https://cdn.pixabay.com/photo/2020/06/07/13/33/fireworks-5270439_1280.jpg",
                ),
            )
        }
    }
}
