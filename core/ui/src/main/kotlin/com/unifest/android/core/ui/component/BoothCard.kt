package com.unifest.android.core.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.component.NetworkImage
import com.unifest.android.core.designsystem.theme.Content2
import com.unifest.android.core.designsystem.theme.Title2
import com.unifest.android.core.designsystem.theme.Title5
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.domain.entity.BoothDetailEntity

@Composable
fun BoothCard(
    boothInfo: BoothDetailEntity,
    onNavigateToBooth: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.clickable {
            onNavigateToBooth(boothInfo.id)
        },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Row(
            modifier = Modifier.padding(15.dp),
        ) {
            NetworkImage(
                imageUrl = "https://picsum.photos/86",
                modifier = Modifier
                    .size(86.dp)
                    .clip(RoundedCornerShape(16.dp)),
            )
            Column(
                modifier = Modifier.padding(start = 15.dp),
            ) {
                Text(
                    text = boothInfo.name,
                    style = Title2,
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = boothInfo.description,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = Content2,
                )
                Spacer(modifier = Modifier.height(3.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_location_green),
                        contentDescription = "Location Icon",
                        tint = Color.Unspecified,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = boothInfo.location,
                        style = Title5,
                    )
                }
            }
        }
    }
}

@ComponentPreview
@Composable
fun BoothCardPreview() {
    UnifestTheme {
        BoothCard(
            boothInfo = BoothDetailEntity(
                id = 1L,
                name = "컴공 주점",
                category = "",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                warning = "",
                location = "청심대 앞",
                latitude = 0f,
                longitude = 0f,
                menus = emptyList(),
            ),
            onNavigateToBooth = {}
        )
    }
}
