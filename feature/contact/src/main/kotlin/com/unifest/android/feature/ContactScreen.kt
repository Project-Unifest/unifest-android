package com.unifest.android.feature

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.component.TopAppBarNavigationType
import com.unifest.android.core.designsystem.component.UnifestTopAppBar
import com.unifest.android.feature.contact.BuildConfig

@Composable
internal fun ContactRoute(
    padding: PaddingValues,
    onCloseClick: () -> Unit,
) {
    ContactScreen(
        padding = padding,
        onBackClick = onCloseClick,
        contactWebViewUrl = BuildConfig.CONTACT_WEB_VIEW_URL,
    )
}

@Composable
internal fun ContactScreen(
    padding: PaddingValues,
    onBackClick: () -> Unit,
    contactWebViewUrl: String,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
    ) {
        Column {
            ContactTopAppBar(onBackClick = onBackClick)
            ContactContent(contactWebViewUrl = contactWebViewUrl)
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
internal fun ContactContent(
    contactWebViewUrl: String,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                    )
                    webViewClient = WebViewClient()
                    webChromeClient = WebChromeClient()
                    settings.apply {
                        javaScriptEnabled = true
                        domStorageEnabled = true
                        loadWithOverviewMode = true
                        useWideViewPort = true
                    }
                    loadUrl(contactWebViewUrl)
                }
            },
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
internal fun ContactTopAppBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    UnifestTopAppBar(
        navigationType = TopAppBarNavigationType.Back,
        onNavigationClick = onBackClick,
        title = stringResource(id = R.string.contact_title),
        elevation = 8.dp,
        modifier = modifier
            .background(
                color = Color.White,
                shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp),
            )
            .padding(top = 13.dp, bottom = 5.dp),
    )
}
