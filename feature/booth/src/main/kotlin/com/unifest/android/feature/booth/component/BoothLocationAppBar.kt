package com.unifest.android.feature.booth.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.R as designR
import com.unifest.android.core.designsystem.theme.BoothLocation
import com.unifest.android.core.designsystem.theme.Title1
import com.unifest.android.core.designsystem.theme.UnifestTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoothLocationAppBar(
    onBackClick: () -> Unit,
    boothName: String,
    boothLocation: String,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
            )
            .padding(vertical = 8.dp, horizontal = 12.dp),
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = ImageVector.vectorResource(designR.drawable.ic_arrow_back_gray),
                    contentDescription = "뒤로 가기",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        },
        title = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = boothName,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    style = Title1,
                )
                Text(
                    text = boothLocation,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    style = BoothLocation,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.surface),
        actions = {
            Spacer(modifier = Modifier.width(48.dp))
        },
    )
}

@ComponentPreview
@Composable
fun BoothLocationAppBarPreview() {
    UnifestTheme {
        BoothLocationAppBar(
            onBackClick = {},
            boothName = "컴공 주점",
            boothLocation = "청심대 앞",
        )
    }
}
