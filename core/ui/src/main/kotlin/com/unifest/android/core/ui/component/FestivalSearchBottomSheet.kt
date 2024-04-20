package com.unifest.android.core.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.skydoves.flexible.bottomsheet.material3.FlexibleBottomSheet
import com.skydoves.flexible.core.FlexibleSheetSize
import com.skydoves.flexible.core.rememberFlexibleBottomSheetState
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.component.FestivalSearchTextField
import com.unifest.android.core.designsystem.component.LikedFestivalDeleteDialog
import com.unifest.android.core.designsystem.theme.Content3
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.model.FestivalModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun FestivalSearchBottomSheet(
    @StringRes searchTextHintRes: Int,
    searchText: TextFieldValue,
    likedFestivals: MutableList<FestivalModel>,
    festivalSearchResults: ImmutableList<FestivalModel>,
    updateSearchText: (TextFieldValue) -> Unit,
    setFestivalSearchBottomSheetVisible: (Boolean) -> Unit,
    initSearchText: () -> Unit,
    setEnableSearchMode: (Boolean) -> Unit,
    isSearchMode: Boolean,
    setEnableEditMode: () -> Unit,
    isLikedFestivalDeleteDialogVisible: Boolean,
    setLikedFestivalDeleteDialogVisible: (Boolean) -> Unit,
    isEditMode: Boolean = false,
    addLikeFestivalAtBottomSheetSearch: (FestivalModel) -> Unit,
) {
    val selectedFestivals = remember { mutableStateListOf<FestivalModel>() }
    val bottomSheetState = rememberFlexibleBottomSheetState(
        containSystemBars = true,
        flexibleSheetSize = FlexibleSheetSize(
            intermediatelyExpanded = 1.0f,
        ),
        isModal = true,
        skipSlightlyExpanded = true,
    )

    FlexibleBottomSheet(
        onDismissRequest = {
            setFestivalSearchBottomSheetVisible(false)
        },
        sheetState = bottomSheetState,
        containerColor = Color.White,
        dragHandle = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                VerticalDivider(
                    modifier = Modifier
                        .width(80.dp)
                        .height(5.dp)
                        .clip(RoundedCornerShape(43.dp))
                        .background(Color(0xFFA0A0A0)),
                )
            }
        },
        windowInsets = WindowInsets(0, 0, 0, 0),
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .navigationBarsPadding(),
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            FestivalSearchTextField(
                searchText = searchText,
                updateSearchText = updateSearchText,
                searchTextHintRes = searchTextHintRes,
                onSearch = {},
                initSearchText = initSearchText,
                setEnableSearchMode = setEnableSearchMode,
                isSearchMode = isSearchMode,
                modifier = Modifier
                    .height(46.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
            )
            if (!isSearchMode) {
                Spacer(modifier = Modifier.height(39.dp))
                VerticalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .background(Color(0xFFF1F3F7)),
                )
                Spacer(modifier = Modifier.height(21.dp))
                LikedFestivalsGrid(
                    selectedFestivals = likedFestivals,
                    onFestivalSelected = { school ->
                        selectedFestivals.remove(school)
                    },
                    isEditMode = isEditMode,
                    setLikedFestivalDeleteDialogVisible = setLikedFestivalDeleteDialogVisible,
                ) {
                    TextButton(
                        onClick = setEnableEditMode,
                    ) {
                        Text(
                            text = stringResource(id = R.string.edit),
                            color = Color.Black,
                            style = Content3,
                        )
                    }
                }
            } else {
                FestivalSearchResults(
                    searchResults = festivalSearchResults,
                    addLikeFestivalAtBottomSheetSearch = addLikeFestivalAtBottomSheetSearch,
                )
            }
        }
        if (isLikedFestivalDeleteDialogVisible) {
            LikedFestivalDeleteDialog(
                onCancelClick = {
                    setLikedFestivalDeleteDialogVisible(false)
                },
                onConfirmClick = {
                    setLikedFestivalDeleteDialogVisible(false)
                },
            )
        }
    }
}

@ComponentPreview
@Composable
fun SchoolSearchBottomSheetPreview() {
    UnifestTheme {
        FestivalSearchBottomSheet(
            searchTextHintRes = R.string.festival_search_text_field_hint,
            setFestivalSearchBottomSheetVisible = {},
            searchText = TextFieldValue(),
            updateSearchText = {},
            likedFestivals = mutableListOf(
                FestivalModel(
                    1,
                    1,
                    "https://picsum.photos/36",
                    "서울대학교",
                    "설대축제",
                    "05.06",
                    "05.08",
                    126.957f,
                    37.460f,
                ),
                FestivalModel(
                    2,
                    2,
                    "https://picsum.photos/36",
                    "연세대학교",
                    "연대축제",
                    "05.06",
                    "05.08",
                    126.957f,
                    37.460f,
                ),
                FestivalModel(
                    3,
                    3,
                    "https://picsum.photos/36",
                    "고려대학교",
                    "고대축제",
                    "05.06",
                    "05.08",
                    126.957f,
                    37.460f,
                ),
                FestivalModel(
                    4,
                    4,
                    "https://picsum.photos/36",
                    "성균관대학교",
                    "성대축제",
                    "05.06",
                    "05.08",
                    126.957f,
                    37.460f,
                ),
                FestivalModel(
                    5,
                    5,
                    "https://picsum.photos/36",
                    "건국대학교",
                    "건대축제",
                    "05.06",
                    "05.08",
                    126.957f,
                    37.460f,
                ),
            ),
            festivalSearchResults = persistentListOf(
                FestivalModel(
                    1,
                    1,
                    "https://picsum.photos/36",
                    "서울대학교",
                    "설대축제",
                    "05.06",
                    "05.08",
                    126.957f,
                    37.460f,
                ),
                FestivalModel(
                    2,
                    2,
                    "https://picsum.photos/36",
                    "연세대학교",
                    "연대축제",
                    "05.06",
                    "05.08",
                    126.957f,
                    37.460f,
                ),
                FestivalModel(
                    3,
                    3,
                    "https://picsum.photos/36",
                    "고려대학교",
                    "고대축제",
                    "05.06",
                    "05.08",
                    126.957f,
                    37.460f,
                ),
                FestivalModel(
                    4,
                    4,
                    "https://picsum.photos/36",
                    "성균관대학교",
                    "성대축제",
                    "05.06",
                    "05.08",
                    126.957f,
                    37.460f,
                ),
                FestivalModel(
                    5,
                    5,
                    "https://picsum.photos/36",
                    "건국대학교",
                    "건대축제",
                    "05.06",
                    "05.08",
                    126.957f,
                    37.460f,
                ),
            ),
            initSearchText = {},
            setEnableSearchMode = {},
            isSearchMode = false,
            setEnableEditMode = {},
            isLikedFestivalDeleteDialogVisible = false,
            isEditMode = false,
            setLikedFestivalDeleteDialogVisible = {},
            addLikeFestivalAtBottomSheetSearch = {},
        )
    }
}
