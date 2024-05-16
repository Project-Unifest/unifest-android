package com.unifest.android.feature.home

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.window.DialogProperties
import com.unifest.android.core.model.StarInfoModel
import com.unifest.android.core.ui.component.LargeStarImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StarImageDialog(
    onDismissRequest: () -> Unit,
    starInfo: StarInfoModel,
    modifier: Modifier = Modifier,
) {
    val configuration = LocalConfiguration.current
    val dialogSize = remember(configuration) {
        val screenWidth = configuration.screenWidthDp.dp
        val screenHeight = configuration.screenHeightDp.dp
        min(screenWidth, screenHeight) - 36.dp
    }

    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier
            .size(dialogSize)
            .clip(CircleShape),
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
        ),
    ) {
        LargeStarImage(
            imgUrl = starInfo.imgUrl,
            onClick = onDismissRequest,
            label = starInfo.name,
        )
    }
}
