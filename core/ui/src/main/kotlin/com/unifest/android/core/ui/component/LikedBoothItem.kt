package com.unifest.android.core.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.component.NetworkImage
import com.unifest.android.core.designsystem.theme.MainColor
import com.unifest.android.core.designsystem.theme.Title2
import com.unifest.android.core.designsystem.theme.Title5
import com.unifest.android.core.model.BoothDetailModel

@Composable
fun LikedBoothItem(
    booth: BoothDetailModel,
    index: Int,
    totalCount: Int,
    deleteLikedBooth: (BoothDetailModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val bookMarkColor = if (booth.isLiked) MainColor else Color(0xFF4B4B4B)
    Column(
        modifier = modifier.padding(horizontal = 20.dp),
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxSize(),
        ) {
            NetworkImage(
                imgUrl = "https://picsum.photos/86",
                modifier = Modifier
                    .size(86.dp)
                    .clip(RoundedCornerShape(16.dp)),
            )
            Spacer(modifier = Modifier.width(14.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            ) {
                Text(
                    text = booth.name,
                    style = Title2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = booth.category,
                    style = Title5,
                    color = Color(0xFF545454),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
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
                        style = Title5,
                        color = Color(0xFF545454),
                        modifier = Modifier.align(Alignment.CenterVertically),
                    )
                }
            }
            Icon(
                imageVector = ImageVector.vectorResource(if (booth.isLiked) R.drawable.ic_bookmarked else R.drawable.ic_bookmark),
                contentDescription = "Bookmark Icon",
                tint = bookMarkColor,
                modifier = Modifier
                    .size(24.dp)
                    .clickable(
                        onClick = {
                            deleteLikedBooth(booth)
                        },
                    ),
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (index < totalCount - 1) {
            HorizontalDivider(
                color = Color(0xFFDFDFDF),
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@ComponentPreview
@Composable
fun LikedBoothItemPreview() {
    LikedBoothItem(
        booth = BoothDetailModel(
            id = 1,
            name = "부스 이름",
            category = "부스 카테고리",
            description = "부스 설명",
            warning = "",
            location = "부스 위치",
            isLiked = true,
        ),
        index = 0,
        totalCount = 1,
        deleteLikedBooth = {},
    )
}
