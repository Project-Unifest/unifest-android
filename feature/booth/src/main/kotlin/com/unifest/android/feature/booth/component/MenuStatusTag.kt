package com.unifest.android.feature.booth.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import com.unifest.android.core.designsystem.ComponentPreview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.unifest.android.core.designsystem.theme.BottomMenuBar
import com.unifest.android.core.designsystem.theme.Content7

@Composable
fun Tag(
    menuStatus: String = "",
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(7.dp))
            .background(MaterialTheme.colorScheme.outline)
            .padding(horizontal = 12.dp, vertical = 4.dp),
    ) {
        when {
            menuStatus == "품절" -> {
                Text(
                    text = menuStatus,
                    style = Content7,
                    color = MaterialTheme.colorScheme.error,
                )
            }

            menuStatus.contains("남음") -> {
                val parts = menuStatus.split("남음")
                Text(
                    buildAnnotatedString {
                        append(parts[0])
                        withStyle(style = SpanStyle(fontSize = BottomMenuBar.fontSize, fontWeight = BottomMenuBar.fontWeight)) {
                            append("남음")
                        }
                    },
                    style = Content7,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                )
            }

            else -> {
                Text(
                    text = menuStatus,
                    style = Content7,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
        }
    }
}

@ComponentPreview
@Composable
fun TagPreview() {
    Tag(menuStatus = "10개 미만 남음")
}
