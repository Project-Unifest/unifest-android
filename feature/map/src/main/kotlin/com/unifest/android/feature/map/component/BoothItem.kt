package com.unifest.android.feature.map.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.DarkComponentPreview
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.component.NetworkImage
import com.unifest.android.core.designsystem.theme.Content2
import com.unifest.android.core.designsystem.theme.Title2
import com.unifest.android.core.designsystem.theme.Title5
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.feature.map.model.BoothMapModel
import com.unifest.android.feature.map.viewmodel.MapUiAction

@Composable
fun BoothItem(
    boothInfo: BoothMapModel,
    isPopularMode: Boolean,
    ranking: Int,
    onAction: (MapUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val textStyle = Content2
    val textHeight = remember(textStyle) {
        with(density) { Content2.fontSize.toDp() * 2 }
    }

    Card(
        modifier = modifier.clickable {
            onAction(MapUiAction.OnBoothItemClick(boothInfo.id))
        },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Box {
            Row(
                modifier = Modifier.padding(15.dp),
            ) {
                NetworkImage(
                    imgUrl = boothInfo.thumbnail,
                    contentDescription = "Booth Thumbnail",
                    modifier = Modifier
                        .size(86.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    placeholder = painterResource(id = R.drawable.item_placeholder),
                )
                Column(
                    modifier = Modifier.padding(start = 15.dp),
                ) {
                    Text(
                        text = boothInfo.name,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = Title2,
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = boothInfo.description,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.heightIn(min = textHeight),
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
                            color = MaterialTheme.colorScheme.onSecondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = Title5,
                        )
                    }
                }
            }
            if (isPopularMode) {
                RankingBadge(ranking = ranking)
            }
        }
    }
}

@Composable
fun RankingBadge(ranking: Int) {
    Box(
        modifier = Modifier
            .size(width = 43.dp, height = 45.dp)
            .padding(start = 7.dp, top = 9.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary, CircleShape),
        contentAlignment = Alignment.TopStart,
    ) {
        Text(
            text = stringResource(id = R.string.map_ranking, ranking),
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            style = Title5,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@ComponentPreview
@Composable
fun BoothItemPreview() {
    UnifestTheme {
        BoothItem(
            boothInfo = BoothMapModel(
                id = 1L,
                name = "컴공 주점",
                category = "",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                location = "청심대 앞",
            ),
            isPopularMode = true,
            ranking = 1,
            onAction = {},
        )
    }
}

@DarkComponentPreview
@Composable
fun BoothItemDarkPreview() {
    UnifestTheme {
        BoothItem(
            boothInfo = BoothMapModel(
                id = 1L,
                name = "컴공 주점",
                category = "",
                description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                location = "청심대 앞",
            ),
            isPopularMode = true,
            ranking = 1,
            onAction = {},
        )
    }
}

@ComponentPreview
@Composable
fun RankingBadgePreview() {
    UnifestTheme {
        RankingBadge(ranking = 1)
    }
}

@DarkComponentPreview
@Composable
fun RankingBadgeDarkPreview() {
    UnifestTheme {
        RankingBadge(ranking = 1)
    }
}
