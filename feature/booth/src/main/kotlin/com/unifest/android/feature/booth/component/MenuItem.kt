package com.unifest.android.feature.booth.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.unifest.android.core.common.utils.formatAsCurrency
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.component.NetworkImage
import com.unifest.android.core.designsystem.theme.Content9
import com.unifest.android.core.designsystem.theme.MenuPrice
import com.unifest.android.core.designsystem.theme.MenuTitle
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.model.MenuModel
import com.unifest.android.feature.booth.viewmodel.BoothUiAction

@Composable
fun MenuItem(
    menu: MenuModel,
    onAction: (BoothUiAction) -> Unit,
) {
    Row(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
    ) {
        Box(
            modifier = Modifier
                .size(86.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable(
                    onClick = {
                        if (menu.imgUrl.isNotEmpty()) {
                            onAction(BoothUiAction.OnMenuImageClick(menu))
                        }
                    },
                ),
            contentAlignment = Alignment.Center
        ) {
            NetworkImage(
                imgUrl = menu.imgUrl,
                contentDescription = menu.name,
                placeholder = painterResource(id = R.drawable.item_placeholder),
                modifier = Modifier.matchParentSize(),
            )

            // 품절일 경우 반투명 검은색 배경과 "품절" 텍스트 추가
            if (menu.status == "품절") {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.Black.copy(alpha = 0.6f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "품절",
                        color = Color.White,
                        style = Content9
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(13.dp))
        Column(
            modifier = Modifier.align(Alignment.CenterVertically),
        ) {
            Text(
                text = menu.name,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MenuTitle,
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = menu.price.formatAsCurrency(),
                color = MaterialTheme.colorScheme.onBackground,
                style = MenuPrice,
            )
            Spacer(modifier = Modifier.height(14.dp))
            Tag(menuStatus = menu.status)
        }
    }
}

@ComponentPreview
@Composable
fun MenuItemPreview() {
    UnifestTheme {
        MenuItem(
            menu = MenuModel(
                id = 1L,
                name = "닭강정",
                price = 6000,
                imgUrl = "",
                status = "10개 미만 남음",
            ),
            onAction = {},
        )
    }
}
