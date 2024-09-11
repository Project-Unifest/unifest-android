package com.unifest.android.feature.festival

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.component.FestivalSearchTextField
import com.unifest.android.core.designsystem.component.LikedFestivalDeleteDialog
import com.unifest.android.core.designsystem.component.UnifestHorizontalDivider
import com.unifest.android.core.designsystem.theme.Content3
import com.unifest.android.core.designsystem.theme.Title3
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.core.ui.component.LikedFestivalsGrid
import com.unifest.android.feature.festival.preview.FestivalPreviewParameterProvider
import com.unifest.android.feature.festival.viewmodel.ButtonType
import com.unifest.android.feature.festival.viewmodel.FestivalUiAction
import com.unifest.android.feature.festival.viewmodel.FestivalUiState
import kotlinx.collections.immutable.ImmutableList

// TODO HorizontalDivider -> UnifestHorizontalDivider 로 전부 변경
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
        confirmValueChange = { it != SheetValue.Hidden },
    )

    ModalBottomSheet(
        onDismissRequest = {
            onFestivalUiAction(FestivalUiAction.OnDismiss)
        },
        sheetState = bottomSheetState,
        shape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        dragHandle = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(top = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                HorizontalDivider(
                    thickness = 5.dp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier
                        .width(80.dp)
                        .clip(RoundedCornerShape(43.dp)),
                )
            }
        },
        windowInsets = WindowInsets(top = 0),
        modifier = Modifier
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.surface)
            .padding(top = 18.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
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
                UnifestHorizontalDivider(color = MaterialTheme.colorScheme.scrim)
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
                        color = MaterialTheme.colorScheme.onBackground,
                        style = Title3,
                    )
                    TextButton(
                        onClick = {
                            onFestivalUiAction(FestivalUiAction.OnEnableEditMode)
                        },
                    ) {
                        Text(
                            text = stringResource(id = R.string.edit),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
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

@ComponentPreview
@Composable
fun SchoolSearchBottomSheetPreview(
    @PreviewParameter(FestivalPreviewParameterProvider::class)
    festivalUiState: FestivalUiState,
) {
    UnifestTheme {
        FestivalSearchBottomSheet(
            searchTextHintRes = R.string.festival_search_text_field_hint,
            searchText = TextFieldValue(),
            likedFestivals = festivalUiState.festivals,
            festivalSearchResults = festivalUiState.likedFestivals,
            isSearchMode = false,
            isEditMode = false,
            isLikedFestivalDeleteDialogVisible = false,
            onFestivalUiAction = {},
        )
    }
}
