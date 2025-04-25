package com.unifest.android.feature.stamp.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.component.NetworkImage
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.model.StampFestivalModel
import com.unifest.android.feature.stamp.R

@Composable
internal fun StampItem(
    collectedStampCount: Int,
    selectedFestival: StampFestivalModel,
    index: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
    ) {
        val isCollected = index < collectedStampCount
        val imgUrl = if (isCollected) selectedFestival.usedImgUrl else selectedFestival.defaultImgUrl
        val fallbackResourceId = if (isCollected) R.drawable.ic_checked_stamp
        else R.drawable.ic_unchecked_stamp

        val contentDescription = if (isCollected) stringResource(R.string.stamp_used_image)
        else stringResource(R.string.stamp_default_image)

        if (imgUrl.isNotEmpty()) {
            NetworkImage(
                imgUrl = imgUrl,
                contentDescription = contentDescription,
                modifier = Modifier
                    .size(62.dp)
                    .clip(CircleShape),
                placeholder = painterResource(id = fallbackResourceId),
            )
        } else {
            Image(
                painter = painterResource(id = fallbackResourceId),
                contentDescription = contentDescription,
                modifier = Modifier.size(62.dp),
            )
        }
    }
}

@ComponentPreview
@Composable
private fun StampItemPreview() {
    UnifestTheme {
        StampItem(
            collectedStampCount = 10,
            selectedFestival = StampFestivalModel(
                festivalId = 1,
                schoolName = "축제",
                defaultImgUrl = "",
                usedImgUrl = "",
            ),
            index = 1,
        )
    }
}
