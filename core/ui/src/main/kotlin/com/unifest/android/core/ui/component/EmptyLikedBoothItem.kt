package com.unifest.android.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.DarkComponentPreview
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.theme.Content6
import com.unifest.android.core.designsystem.theme.Title2
import com.unifest.android.core.designsystem.theme.UnifestTheme

@Composable
fun EmptyLikedBoothItem(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.menu_liked_booth_empty),
                style = Title2,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Spacer(modifier = Modifier.height(9.dp))
            Text(
                text = stringResource(id = R.string.menu_insert_liked_booth),
                style = Content6,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@ComponentPreview
@Composable
fun EmptyLikedBoothItemPreview() {
    UnifestTheme {
        EmptyLikedBoothItem(
            modifier = Modifier
                .fillMaxWidth()
                .height(248.dp),
        )
    }
}

@DarkComponentPreview
@Composable
fun EmptyLikedBoothItemDarkPreview() {
    UnifestTheme {
        EmptyLikedBoothItem(
            modifier = Modifier
                .fillMaxWidth()
                .height(248.dp),
        )
    }
}
