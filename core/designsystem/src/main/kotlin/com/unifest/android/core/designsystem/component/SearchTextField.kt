package com.unifest.android.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.theme.BoothLocation
import com.unifest.android.core.designsystem.theme.MainColor
import com.unifest.android.core.designsystem.theme.UnifestTheme

val unifestTextSelectionColors = TextSelectionColors(
    handleColor = MainColor,
    backgroundColor = Color(0xFFFAB3BE),
)

@Composable
fun SearchTextField(
    searchText: TextFieldValue,
    updateSearchText: (TextFieldValue) -> Unit,
    @StringRes searchTextHintRes: Int,
    onSearch: (TextFieldValue) -> Unit,
    clearSearchText: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    cornerShape: RoundedCornerShape = RoundedCornerShape(67.dp),
    borderStroke: BorderStroke = BorderStroke(width = 1.dp, color = Color(0xFFBABABA)),
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    CompositionLocalProvider(LocalTextSelectionColors provides unifestTextSelectionColors) {
        BasicTextField(
            value = searchText,
            onValueChange = updateSearchText,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(searchText)
                    keyboardController?.hide()
                },
            ),
            textStyle = TextStyle(color = Color.Black),
            decorationBox = { innerTextField ->
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
                    if (searchText.text.isEmpty()) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_search),
                            contentDescription = "Search Icon",
                            tint = Color.Unspecified,
//                            modifier = Modifier.clickable {
//                                onSearch(searchText)
//                            },
                        )
                    } else {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_delete_gray),
                            contentDescription = "Delete Icon",
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .clickable {
                                    clearSearchText()
                                },
                        )
                    }
                    Spacer(modifier = Modifier.width(width = 15.dp))
                }
            },
        )
    }
}

@Composable
fun FestivalSearchTextField(
    searchText: TextFieldValue,
    updateSearchText: (TextFieldValue) -> Unit,
    @StringRes searchTextHintRes: Int,
    onSearch: (String) -> Unit,
    clearSearchText: () -> Unit,
    setEnableSearchMode: (Boolean) -> Unit,
    isSearchMode: Boolean,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    cornerShape: RoundedCornerShape = RoundedCornerShape(67.dp),
    borderStroke: BorderStroke = BorderStroke(width = 1.dp, color = Color(0xFFBABABA)),
) {
    LaunchedEffect(key1 = searchText.text) {
        setEnableSearchMode(searchText.text.isNotEmpty())
    }

    CompositionLocalProvider(LocalTextSelectionColors provides unifestTextSelectionColors) {
        BasicTextField(
            value = searchText,
            onValueChange = updateSearchText,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            textStyle = TextStyle(color = Color.Black),
            decorationBox = { innerTextField ->
                Row(
                    modifier = modifier
                        .background(color = backgroundColor, shape = cornerShape)
                        .border(
                            border = borderStroke,
                            shape = cornerShape,
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(modifier = Modifier.width(14.dp))
                    if (isSearchMode) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back_dark_gray),
                            contentDescription = "Search Icon",
                            tint = Color(0xFF767676),
                            modifier = Modifier.clickable {
                                clearSearchText()
                            },
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
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
                    if (searchText.text.isEmpty()) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_search),
                            contentDescription = "Search Icon",
                            modifier = Modifier.clickable {
                                onSearch(searchText.text)
                            },
                        )
                    } else {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_delete_gray),
                            contentDescription = "Delete Icon",
                            tint = Color.Unspecified,
                            modifier = Modifier.clickable {
                                clearSearchText()
                            },
                        )
                    }
                    Spacer(modifier = Modifier.width(width = 15.dp))
                }
            },
        )
    }
}

@ComponentPreview
@Composable
fun SearchTextFieldPreview() {
    UnifestTheme {
        SearchTextField(
            searchText = TextFieldValue(),
            updateSearchText = {},
            searchTextHintRes = R.string.intro_search_text_hint,
            onSearch = {},
            clearSearchText = {},
            modifier = Modifier
                .height(46.dp)
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        )
    }
}

@ComponentPreview
@Composable
fun FestivalSearchTextFieldPreview() {
    UnifestTheme {
        FestivalSearchTextField(
            searchText = TextFieldValue("건국대학교"),
            updateSearchText = {},
            searchTextHintRes = R.string.intro_search_text_hint,
            onSearch = {},
            clearSearchText = {},
            setEnableSearchMode = {},
            isSearchMode = true,
            modifier = Modifier
                .height(46.dp)
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        )
    }
}
