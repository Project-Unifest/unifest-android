package com.unifest.android.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.theme.DarkPrimary50
import com.unifest.android.core.designsystem.theme.LightGrey100
import com.unifest.android.core.designsystem.theme.LightGrey800
import com.unifest.android.core.designsystem.theme.LightPrimary500
import com.unifest.android.core.designsystem.theme.Content10
import com.unifest.android.core.designsystem.theme.Title3
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.feature.home.R

@Composable
fun TipComponent(
    modifier: Modifier = Modifier,
    darkTheme: Boolean = isSystemInDarkTheme(),
    tipMessage: String,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
            .background(
                color = if (darkTheme) DarkPrimary50 else LightGrey100,
                shape = RoundedCornerShape(7.dp),
            )
            .then(
                if (darkTheme) Modifier.border(
                    width = 1.dp,
                    color = LightPrimary500,
                    shape = RoundedCornerShape(7.dp),
                ) else Modifier,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.width(15.dp))
        Text(
            text = stringResource(R.string.home_tip_text),
            style = Title3,
            color = MaterialTheme.colorScheme.primary,
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = tipMessage,
            style = Content10,
            color = if (darkTheme) LightPrimary500 else LightGrey800,
        )
    }
}

@ComponentPreview
@Composable
private fun TipComponentPreview() {
    UnifestTheme {
        TipComponent(
            tipMessage = "웨이팅 기능으로 부스 원격 줄서기를 할 수 있어요.",
        )
    }
}
