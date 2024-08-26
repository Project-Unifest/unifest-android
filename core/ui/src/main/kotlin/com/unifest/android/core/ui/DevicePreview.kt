package com.unifest.android.core.ui

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Light",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
    device = "spec:width=360dp,height=800dp,dpi=411",
)
@Preview(
    name = "Dark",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
    device = "spec:width=360dp,height=800dp,dpi=411",
)
annotation class DevicePreview
