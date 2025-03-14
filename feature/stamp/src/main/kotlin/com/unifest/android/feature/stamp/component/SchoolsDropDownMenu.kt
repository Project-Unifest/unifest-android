package com.unifest.android.feature.stamp.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.theme.BoothTitle2
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.feature.stamp.viewmodel.School
import com.unifest.android.feature.stamp.viewmodel.StampUiAction
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun SchoolsDropDownMenu(
    isDropDownMenuOpened: Boolean,
    schools: ImmutableList<School>,
    onAction: (StampUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    DropdownMenu(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        expanded = isDropDownMenuOpened,
        onDismissRequest = {
            onAction(StampUiAction.OnDropDownMenuDismiss)
        },
        shape = RoundedCornerShape(8.dp),
    ) {
        LazyColumn {
            items(
                items = schools,
                key = { it.id },
            ) { item ->
                DropdownMenuItem(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(horizontal = 7.dp),
                    text = {
                        Row {
                            Text(
                                text = item.name,
                                style = BoothTitle2,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(start = 13.dp)
                                    .align(CenterVertically),
                            )
                        }
                    },
                    onClick = {},
                )
            }
        }
    }
}

@ComponentPreview
@Composable
private fun SchoolsDropDownMenuPreview() {
    UnifestTheme {
        SchoolsDropDownMenu(
            isDropDownMenuOpened = true,
            schools = persistentListOf(
                School(1, "서울시립대학교"),
                School(2, "한국교통대학교"),
                School(3, "한양대학교"),
                School(4, "고려대학교"),
                School(5, "홍익대학교"),
            ),
            onAction = {},
        )
    }
}
