package com.unifest.android.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
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
    onChipClick: (String) -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.padding(4.dp),
        shape = RoundedCornerShape(34.dp),
        colors = CardColors(
            containerColor = if (isSelected) Color(0xFFFFF0F3) else Color.White,
            contentColor = if (isSelected) MaterialTheme.colorScheme.primary else Color(0xFF4B4B4B),
            disabledContainerColor = Color.White,
            disabledContentColor = Color(0xFF585858),
        ),
        border = BorderStroke(1.dp, if (isSelected) MaterialTheme.colorScheme.primary else Color(0xFFD2D2D2)),
    ) {
        Box(
            modifier = Modifier.clickable(onClick = { onChipClick(filterName) }),
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
            isSelected = false,
        )
    }
}
