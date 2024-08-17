package com.unifest.android.feature.booth.component

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.unifest.android.core.common.utils.formatAsCurrency
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.component.NetworkImage
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
        NetworkImage(
            imgUrl = menu.imgUrl,
            contentDescription = menu.name,
            placeholder = painterResource(id = R.drawable.item_placeholder),
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
        )
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
            ),
            onAction = {},
        )
    }
}
