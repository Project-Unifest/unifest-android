package com.unifest.android.feature.booth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.window.DialogProperties
import com.unifest.android.core.common.utils.formatAsCurrency
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.component.NetworkImage
import com.unifest.android.core.designsystem.theme.Content1
import com.unifest.android.core.designsystem.theme.Title2
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.model.MenuModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MenuImageDialog(
    onDismissRequest: () -> Unit,
    menu: MenuModel,
    modifier: Modifier = Modifier,
) {
    val windowInfo = LocalWindowInfo.current
    val dialogSize = remember(windowInfo) {
        val screenWidth = windowInfo.containerSize.width.dp
        val screenHeight = windowInfo.containerSize.height.dp
        min(screenWidth, screenHeight) - 36.dp
    }

    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier
            .width(dialogSize)
            .height(dialogSize + 96.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Transparent),
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
        ),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            NetworkImage(
                imgUrl = menu.imgUrl,
                contentDescription = "Menu Image",
                modifier = Modifier
                    .size(dialogSize)
                    .clip(RoundedCornerShape(16.dp)),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = menu.name,
                color = Color.White,
                textAlign = TextAlign.Center,
                style = Title2,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = menu.price.formatAsCurrency(),
                color = Color.White,
                textAlign = TextAlign.Center,
                style = Content1,
            )
        }
    }
}

@ComponentPreview
@Composable
private fun MenuImageDialogPreview() {
    UnifestTheme {
        MenuImageDialog(
            onDismissRequest = {},
            menu = MenuModel(
                id = 0,
                name = "모둠 사시미",
                price = 45000,
                imgUrl = "",
                status = "10개 미만 남음",
            ),
        )
    }
}
