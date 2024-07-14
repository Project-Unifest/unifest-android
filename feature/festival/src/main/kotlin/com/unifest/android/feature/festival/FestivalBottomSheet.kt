package com.unifest.android.feature.festival

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.component.FestivalSearchTextField
import com.unifest.android.core.designsystem.component.LikedFestivalDeleteDialog
import com.unifest.android.core.designsystem.component.UnifestHorizontalDivider
import com.unifest.android.core.designsystem.theme.Content3
import com.unifest.android.core.designsystem.theme.Title3
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.core.ui.component.LikedFestivalsGrid
import com.unifest.android.feature.festival.viewmodel.ButtonType
import com.unifest.android.feature.festival.viewmodel.FestivalUiAction
import com.unifest.android.core.designsystem.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FestivalSearchBottomSheet(
    @StringRes searchTextHintRes: Int,
    searchText: TextFieldValue,
    likedFestivals: ImmutableList<FestivalModel>,
    festivalSearchResults: ImmutableList<FestivalModel>,
    isSearchMode: Boolean,
    isLikedFestivalDeleteDialogVisible: Boolean,
    onFestivalUiAction: (FestivalUiAction) -> Unit,
    isEditMode: Boolean = false,
) {
//    val bottomSheetState = rememberFlexibleBottomSheetState(
//        containSystemBars = true,
//        flexibleSheetSize = FlexibleSheetSize(
//            intermediatelyExpanded = 1.0f,
//        ),
//        isModal = true,
//        skipSlightlyExpanded = true,
//    )
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )

    ModalBottomSheet(
        onDismissRequest = {
            onFestivalUiAction(FestivalUiAction.OnDismiss)
        },
        sheetState = bottomSheetState,
        shape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp),
        containerColor = MaterialTheme.colorScheme.background,
        dragHandle = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                HorizontalDivider(
                    thickness = 5.dp,
                    color = Color(0xFFA0A0A0),
                    modifier = Modifier
                        .width(80.dp)
                        .clip(RoundedCornerShape(43.dp)),
                )
            }
        },
        windowInsets = WindowInsets(top = 0),
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 18.dp),
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .navigationBarsPadding(),
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            FestivalSearchTextField(
                searchText = searchText,
                updateSearchText = { text -> onFestivalUiAction(FestivalUiAction.OnSearchTextUpdated(text)) },
                searchTextHintRes = searchTextHintRes,
                onSearch = {},
                clearSearchText = { onFestivalUiAction(FestivalUiAction.OnSearchTextCleared) },
                setEnableSearchMode = { flag -> onFestivalUiAction(FestivalUiAction.OnEnableSearchMode(flag)) },
                isSearchMode = isSearchMode,
                modifier = Modifier
                    .height(46.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
            )
            if (!isSearchMode) {
                Spacer(modifier = Modifier.height(39.dp))
                UnifestHorizontalDivider()
                Spacer(modifier = Modifier.height(21.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.intro_liked_festivals_title),
                        style = Title3,
                    )
                    TextButton(
                        onClick = {
                            onFestivalUiAction(FestivalUiAction.OnEnableEditMode)
                        },
                    ) {
                        Text(
                            text = stringResource(id = R.string.edit),
                            color = Color.Black,
                            style = Content3,
                        )
                    }
                }
                LikedFestivalsGrid(
                    selectedFestivals = likedFestivals,
                    onFestivalSelected = { festival ->
                        onFestivalUiAction(FestivalUiAction.OnLikedFestivalSelected(festival))
                    },
                    isEditMode = isEditMode,
                    onDeleteLikedFestivalClick = { festival ->
                        onFestivalUiAction(FestivalUiAction.OnDeleteIconClick(festival))
                    },
                )
            } else {
                FestivalSearchResults(
                    searchResults = festivalSearchResults,
                    onFestivalUiAction = onFestivalUiAction,
                    likedFestivals = likedFestivals,
                )
            }
        }
        if (isLikedFestivalDeleteDialogVisible) {
            LikedFestivalDeleteDialog(
                onCancelClick = {
                    onFestivalUiAction(
                        FestivalUiAction.OnDeleteDialogButtonClick(ButtonType.CANCEL),
                    )
                },
                onConfirmClick = {
                    onFestivalUiAction(
                        FestivalUiAction.OnDeleteDialogButtonClick(ButtonType.CONFIRM),
                    )
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
            searchText = TextFieldValue(),
            likedFestivals = persistentListOf(
                FestivalModel(
                    1,
                    1,
                    "https://picsum.photos/36",
                    "서울대학교",
                    "서울",
                    "설대축제",
                    "2024-04-21",
                    "2024-04-23",
                    126.957f,
                    37.460f,
                ),
                FestivalModel(
                    2,
                    2,
                    "https://picsum.photos/36",
                    "연세대학교",
                    "서울",
                    "연대축제",
                    "2024-04-21",
                    "2024-04-23",
                    126.957f,
                    37.460f,
                ),
                FestivalModel(
                    3,
                    3,
                    "https://picsum.photos/36",
                    "고려대학교",
                    "서울",
                    "고대축제",
                    "2024-04-21",
                    "2024-04-23",
                    126.957f,
                    37.460f,
                ),
                FestivalModel(
                    4,
                    4,
                    "https://picsum.photos/36",
                    "성균관대학교",
                    "서울",
                    "성대축제",
                    "2024-04-21",
                    "2024-04-23",
                    126.957f,
                    37.460f,
                ),
                FestivalModel(
                    5,
                    5,
                    "https://picsum.photos/36",
                    "건국대학교",
                    "서울",
                    "건대축제",
                    "2024-04-21",
                    "2024-04-23",
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
                    "서울",
                    "설대축제",
                    "2024-04-21",
                    "2024-04-23",
                    126.957f,
                    37.460f,
                ),
                FestivalModel(
                    2,
                    2,
                    "https://picsum.photos/36",
                    "연세대학교",
                    "서울",
                    "연대축제",
                    "2024-04-21",
                    "2024-04-23",
                    126.957f,
                    37.460f,
                ),
                FestivalModel(
                    3,
                    3,
                    "https://picsum.photos/36",
                    "고려대학교",
                    "서울",
                    "고대축제",
                    "2024-04-21",
                    "2024-04-23",
                    126.957f,
                    37.460f,
                ),
                FestivalModel(
                    4,
                    4,
                    "https://picsum.photos/36",
                    "성균관대학교",
                    "성대축제",
                    "서울",
                    "2024-04-21",
                    "2024-04-23",
                    126.957f,
                    37.460f,
                ),
                FestivalModel(
                    5,
                    5,
                    "https://picsum.photos/36",
                    "건국대학교",
                    "서울",
                    "건대축제",
                    "2024-04-21",
                    "2024-04-23",
                    126.957f,
                    37.460f,
                ),
            ),
            isSearchMode = false,
            isEditMode = false,
            isLikedFestivalDeleteDialogVisible = false,
            onFestivalUiAction = {},
        )
    }
}
