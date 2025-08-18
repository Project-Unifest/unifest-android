package com.unifest.android.feature.map.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.unifest.android.core.common.extension.noRippleClickable
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.theme.Title5
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.feature.map.R

@Composable
internal fun BoothLayoutButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Surface(
        modifier = modifier
            .size(56.dp)
            .noRippleClickable { onClick() },
        shape = CircleShape,
        color = MaterialTheme.colorScheme.primary,
        shadowElevation = 19.dp,
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(R.string.map_booth_layout),
                textAlign = TextAlign.Center,
                style = Title5.copy(color = Color.White),
            )
        }
    }
}

@ComponentPreview
@Composable
private fun BoothLayoutButtonPreview() {
    UnifestTheme {
        Box(
            modifier = Modifier.size(100.dp),
        ) {
            BoothLayoutButton(
                onClick = {},
            )
        }
    }
}
