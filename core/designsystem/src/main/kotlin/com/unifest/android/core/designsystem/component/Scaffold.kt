package com.unifest.android.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import tech.thdev.compose.extensions.keyboard.state.MutableExKeyboardStateSource
import tech.thdev.compose.extensions.keyboard.state.foundation.removeFocusWhenKeyboardIsHidden
import tech.thdev.compose.extensions.keyboard.state.localowners.LocalMutableExKeyboardStateSourceOwner

@Composable
fun UnifestScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalMutableExKeyboardStateSourceOwner provides MutableExKeyboardStateSource()
    ) {
        Scaffold(
            topBar = topBar,
            bottomBar = bottomBar,
            snackbarHost = snackbarHost,
            floatingActionButton = floatingActionButton,
            containerColor = containerColor,
            contentColor = contentColor,
            contentWindowInsets = contentWindowInsets,
            modifier = modifier.removeFocusWhenKeyboardIsHidden()
        ) { innerPadding ->
            Box(
                modifier = Modifier.padding(innerPadding)
            ) {
                content()
            }
        }
    }
}
