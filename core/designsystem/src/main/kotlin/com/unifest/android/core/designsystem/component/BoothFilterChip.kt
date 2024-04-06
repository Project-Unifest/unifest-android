package com.unifest.android.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.theme.BoothLocation
import com.unifest.android.core.designsystem.theme.UnifestTheme

@Composable
fun BoothFilterChip(
    filterName: String,
    onChipClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.padding(4.dp),
        shape = RoundedCornerShape(34.dp),
        colors = CardColors(
            containerColor = Color(0xFFFFF0F3),
            contentColor = Color(0xFFf5678E),
            disabledContainerColor = Color.White,
            disabledContentColor = Color(0xFF585858),
        ),
        border = BorderStroke(1.dp, Color(0xFFf5678E)),
    ) {
        Box(
            modifier = Modifier.clickable { onChipClick() },
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 13.dp, vertical = 5.dp),
            ) {
                Text(
                    text = filterName,
                    style = BoothLocation,
                )
            }
        }
    }
}

@ComponentPreview
@Composable
fun BoothFilterChipPreview() {
    UnifestTheme {
        BoothFilterChip(
            filterName = "주점",
            onChipClick = {},
        )
    }
}
