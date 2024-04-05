package com.unifest.android.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.theme.BoothLocation
import com.unifest.android.core.designsystem.theme.UnifestTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchTextField(
    searchText: TextFieldState,
    @StringRes searchTextHintRes: Int,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    cornerShape: RoundedCornerShape = RoundedCornerShape(67.dp),
    borderStroke: BorderStroke = BorderStroke(width = 1.dp, color = Color(0xFFD9D9D9)),
) {
    BasicTextField2(
        modifier = Modifier.fillMaxWidth(),
        state = searchText,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        textStyle = TextStyle(color = Color(0xFF848484)),
        decorator = { innerTextField ->
            Row(
                modifier = modifier
                    .background(color = backgroundColor, shape = cornerShape)
                    .border(
                        border = borderStroke,
                        shape = cornerShape,
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(modifier = Modifier.width(17.dp))
                Box {
                    if (searchText.text.isEmpty()) {
                        Text(
                            text = stringResource(id = searchTextHintRes),
                            color = Color(0xFF848484),
                            style = BoothLocation,
                        )
                    }
                    innerTextField()
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_search),
                    contentDescription = "Search Icon",
                    modifier = Modifier.clickable {
                        onSearch(searchText.text.toString())
                    },
                )
                Spacer(modifier = Modifier.width(width = 15.dp))
            }
        },
    )
}

@OptIn(ExperimentalFoundationApi::class)
@ComponentPreview
@Composable
fun SearchTextFieldPreview() {
    UnifestTheme {
        SearchTextField(
            searchText = TextFieldState(""),
            searchTextHintRes = R.string.intro_search_text_hint,
            onSearch = {},
            modifier = Modifier
                .height(46.dp)
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        )
    }
}