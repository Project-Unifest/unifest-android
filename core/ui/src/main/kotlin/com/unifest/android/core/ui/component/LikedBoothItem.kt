package com.unifest.android.core.ui.component

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.unifest.android.core.common.extension.clickableSingle
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.component.NetworkImage
import com.unifest.android.core.designsystem.theme.Title2
import com.unifest.android.core.designsystem.theme.Title5
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.model.LikedBoothModel

@Composable
fun LikedBoothItem(
    booth: LikedBoothModel,
    index: Int,
    totalCount: Int,
    deleteLikedBooth: (LikedBoothModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val bookMarkColor = if (booth.isLiked) MaterialTheme.colorScheme.primary else Color(0xFF4B4B4B)

    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp),
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            NetworkImage(
                imgUrl = booth.thumbnail,
                contentDescription = "Booth Thumbnail",
                modifier = Modifier
                    .size(86.dp)
                    .clip(RoundedCornerShape(16.dp)),
                placeholder = painterResource(id = R.drawable.item_placeholder),
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
                    text = booth.warning,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = Title5,
                )
                Spacer(modifier = Modifier.height(13.dp))
                Row {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_location_green),
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
            Icon(
                imageVector = ImageVector.vectorResource(if (booth.isLiked) R.drawable.ic_bookmarked else R.drawable.ic_bookmark),
                contentDescription = "Bookmark Icon",
                tint = bookMarkColor,
                modifier = Modifier
                    .size(24.dp)
                    .clickableSingle(
                        onClick = {
                            deleteLikedBooth(booth)
                        },
                    ),
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (index < totalCount - 1) {
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outline,
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@ComponentPreview
@Composable
private fun LikedBoothItemPreview() {
    UnifestTheme {
        LikedBoothItem(
            booth = LikedBoothModel(
                id = 1,
                name = "부스 이름",
                category = "부스 카테고리",
                description = "부스 설명",
                location = "부스 위치",
                warning = "학과 전용 부스",
            ),
            index = 0,
            totalCount = 1,
            deleteLikedBooth = {},
        )
    }
}
