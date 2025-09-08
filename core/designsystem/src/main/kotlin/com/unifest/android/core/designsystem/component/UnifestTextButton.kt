package com.unifest.android.core.designsystem.component

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import com.unifest.android.core.common.MultipleEventsCutter
import com.unifest.android.core.common.get
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.theme.Content6
import com.unifest.android.core.designsystem.theme.UnifestTheme

@Composable
fun UnifestTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    multipleEventsCutterEnabled: Boolean = true,
    content: @Composable () -> Unit = {},
) {
    val multipleEventsCutter = remember { MultipleEventsCutter.get() }
    TextButton(
        onClick = {
            if (multipleEventsCutterEnabled) {
                multipleEventsCutter.processEvent { onClick() }
            } else {
                onClick()
            }
        },
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
    ) {
        content()
    }
}

@ComponentPreview
@Composable
private fun UnifestTextButtonPreview() {
    UnifestTheme {
        UnifestTextButton(
            onClick = {},
        ) {
            Text(
                text = "Text Button",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textDecoration = TextDecoration.Underline,
                style = Content6,
            )
        }
    }
}
