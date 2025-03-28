package com.unifest.android.feature.menu.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.component.AutoResizedText
import com.unifest.android.core.designsystem.component.NetworkImage
import com.unifest.android.core.designsystem.theme.Content6
import com.unifest.android.core.designsystem.theme.MenuTitle
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.feature.menu.viewmodel.MenuUiAction

@Composable
fun FestivalItem(
    festival: FestivalModel,
    onMenuUiAction: (MenuUiAction) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(vertical = 10.dp)
            .clickable {
                onMenuUiAction(MenuUiAction.OnLikedFestivalItemClick(festival))
            },
    ) {
        Box(
            modifier = Modifier
                .size(65.dp)
                .shadow(
                    elevation = 6.dp,
                    shape = CircleShape,
                )
                .background(MaterialTheme.colorScheme.tertiaryContainer, CircleShape)
                .padding(5.dp),
            contentAlignment = Alignment.Center,
        ) {
            NetworkImage(
                imgUrl = festival.thumbnail,
                contentDescription = "Festival Thumbnail",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                placeholder = painterResource(id = R.drawable.item_placeholder),
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        AutoResizedText(
            text = festival.schoolName,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = Content6,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(2.dp))
        AutoResizedText(
            text = festival.festivalName,
            color = MaterialTheme.colorScheme.onBackground,
            style = MenuTitle,
            textAlign = TextAlign.Center,
        )
    }
}

@ComponentPreview
@Composable
fun FestivalItemPreview() {
    FestivalItem(
        festival = FestivalModel(
            schoolName = "건국대학교",
            festivalName = "건국대학교 축제 건국대학교 축제",
            thumbnail = "https://picsum.photos/200/300",
        ),
        onMenuUiAction = {},
    )
}
