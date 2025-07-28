package com.unifest.android.feature.home.component

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.component.NetworkImage
import com.unifest.android.core.designsystem.theme.Content10
import com.unifest.android.core.designsystem.theme.Title2
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.model.CardNewsModel
import com.unifest.android.feature.home.R
import com.unifest.android.feature.home.clickable
import com.unifest.android.core.designsystem.R as designR

@Composable
fun HomeCardNews(
    modifier: Modifier = Modifier,
    cardNewsList: List<CardNewsModel>,
    onCardNewClick: (CardNewsModel) -> Unit,
) {
    val scrollState = rememberScrollState()
    Column {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = stringResource(id = R.string.home_gacheon_festival_news),
            style = Title2,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.height(7.dp))
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = stringResource(id = R.string.home_gacheon_festival_news_message),
            style = Content10,
            color = MaterialTheme.colorScheme.onSecondary,
        )
        Spacer(modifier = Modifier.height(18.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .scrollable(scrollState, orientation = Orientation.Horizontal),
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            cardNewsList.forEach { cardNews ->
                NetworkImage(
                    modifier = Modifier
                        .size(204.dp)
                        .clip(RoundedCornerShape(7.dp))
                        .clickable { onCardNewClick(cardNews) },
                    imgUrl = cardNews.coverImgUrl,
                    contentDescription = "",
                    placeholder = painterResource(id = designR.drawable.item_placeholder),
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Spacer(modifier = Modifier.width(20.dp))
        }
    }
}

@ComponentPreview
@Composable
private fun HomeCardNewsPreview() {
    UnifestTheme {
        HomeCardNews(
            cardNewsList = listOf(
                CardNewsModel(),
                CardNewsModel(),
            ),
            onCardNewClick = {},
        )
    }
}
