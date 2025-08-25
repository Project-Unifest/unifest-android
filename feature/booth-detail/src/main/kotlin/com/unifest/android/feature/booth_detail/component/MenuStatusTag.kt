package com.unifest.android.feature.booth_detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.theme.BottomMenuBar
import com.unifest.android.core.designsystem.theme.Content7
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.model.MenuStatus
import com.unifest.android.feature.booth_detail.R

@Composable
internal fun Tag(
    modifier: Modifier = Modifier,
    menuStatus: MenuStatus,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(7.dp))
            .background(MaterialTheme.colorScheme.outline)
            .padding(horizontal = 12.dp, vertical = 4.dp),
    ) {
        when (menuStatus) {
            MenuStatus.SOLD_OUT -> {
                Text(
                    text = stringResource(R.string.sold_out),
                    style = Content7,
                    color = MaterialTheme.colorScheme.error,
                )
            }

            MenuStatus.UNDER_10 -> {
                val parts = "10개 미만 남음".split("남음")
                Text(
                    buildAnnotatedString {
                        append(parts[0]) // "10개 미만"
                        withStyle(style = SpanStyle(fontSize = BottomMenuBar.fontSize, fontWeight = BottomMenuBar.fontWeight)) {
                            append("남음") // "남음" 부분 스타일 적용
                        }
                    },
                    style = Content7,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                )
            }

            MenuStatus.UNDER_50 -> {
                val parts = "50개 미만 남음".split("남음")
                Text(
                    buildAnnotatedString {
                        append(parts[0]) // "50개 미만"
                        withStyle(style = SpanStyle(fontSize = BottomMenuBar.fontSize, fontWeight = BottomMenuBar.fontWeight)) {
                            append("남음") // "남음" 부분 스타일 적용
                        }
                    },
                    style = Content7,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                )
            }

            MenuStatus.ENOUGH -> {
                Text(
                    text = stringResource(R.string.enough_status),
                    style = Content7,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                )
            }

            MenuStatus.NO_DATA -> {
                Text(
                    text = stringResource(id = R.string.no_menu_status),
                    style = Content7,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
        }
    }
}

@ComponentPreview
@Composable
private fun TagPreview() {
    UnifestTheme {
        Tag(menuStatus = MenuStatus.UNDER_10)
    }
}
