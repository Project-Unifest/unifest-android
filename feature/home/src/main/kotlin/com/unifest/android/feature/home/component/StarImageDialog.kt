package com.unifest.android.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.window.DialogProperties
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.component.NetworkImage
import com.unifest.android.core.designsystem.theme.Title2
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.model.StarInfoModel
import com.unifest.android.core.designsystem.R as designR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun StarImageDialog(
    onDismissRequest: () -> Unit,
    starInfo: StarInfoModel,
    modifier: Modifier = Modifier,
) {
    val configuration = LocalConfiguration.current
    val dialogSize = remember(configuration) {
        val screenWidth = configuration.screenWidthDp.dp
        val screenHeight = configuration.screenHeightDp.dp
        min(screenWidth, screenHeight) - 128.dp
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
                imgUrl = starInfo.imgUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(dialogSize)
                    .clip(CircleShape),
                placeholder = painterResource(designR.drawable.ic_star_placeholder),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = starInfo.name,
                color = Color.White,
                textAlign = TextAlign.Center,
                style = Title2,
            )
        }
    }
}

@ComponentPreview
@Composable
private fun StarImageDialogPreview() {
    UnifestTheme {
        StarImageDialog(
            onDismissRequest = {},
            starInfo = StarInfoModel(
                starId = 0L,
                name = "창모",
                imgUrl = "",
            ),
        )
    }
}
