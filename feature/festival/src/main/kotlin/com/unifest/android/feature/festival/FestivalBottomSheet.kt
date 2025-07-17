package com.unifest.android.feature.festival

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
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.component.FestivalSearchTextField
import com.unifest.android.core.designsystem.component.LikedFestivalDeleteDialog
import com.unifest.android.core.designsystem.theme.Content3
import com.unifest.android.core.designsystem.theme.Title3
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.ui.component.LikedFestivalsGrid
import com.unifest.android.feature.festival.preview.FestivalPreviewParameterProvider
import com.unifest.android.feature.festival.viewmodel.ButtonType
import com.unifest.android.feature.festival.viewmodel.FestivalUiAction
import com.unifest.android.feature.festival.viewmodel.FestivalUiState
import com.unifest.android.core.designsystem.R as designR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FestivalSearchBottomSheet(
    uiState: FestivalUiState,
    onFestivalUiAction: (FestivalUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = {
            onFestivalUiAction(FestivalUiAction.OnDismiss)
        },
        sheetState = bottomSheetState,
        shape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        dragHandle = {
            Column(
                modifier = modifier
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
        contentWindowInsets = { WindowInsets(top = 0) },
        modifier = Modifier
            .fillMaxHeight()
            .statusBarsPadding()
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
                searchTextState = uiState.festivalSearchText,
                // updateSearchText = { text -> onFestivalUiAction(FestivalUiAction.OnSearchTextUpdated(text)) },
                searchTextHintRes = designR.string.festival_search_text_field_hint,
                onSearch = {},
                clearSearchText = { onFestivalUiAction(FestivalUiAction.OnSearchTextCleared) },
                setEnableSearchMode = { flag -> onFestivalUiAction(FestivalUiAction.OnEnableSearchMode(flag)) },
                isSearchMode = uiState.isSearchMode,
                modifier = Modifier
                    .height(46.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
            )
            if (!uiState.isSearchMode) {
                Spacer(modifier = Modifier.height(39.dp))
                HorizontalDivider(
                    thickness = 8.dp,
                    color = MaterialTheme.colorScheme.scrim,
                )
                Spacer(modifier = Modifier.height(21.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                ) {
                    Text(
                        text = stringResource(id = designR.string.liked_festivals_title),
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
                    selectedFestivals = uiState.likedFestivals,
                    onFestivalSelected = { festival ->
                        onFestivalUiAction(FestivalUiAction.OnLikedFestivalSelected(festival))
                    },
                    isEditMode = uiState.isEditMode,
                    onDeleteLikedFestivalClick = { festival ->
                        onFestivalUiAction(FestivalUiAction.OnDeleteIconClick(festival))
                    },
                )
            } else {
                FestivalSearchResults(
                    searchResults = uiState.festivalSearchResults,
                    onFestivalUiAction = onFestivalUiAction,
                    likedFestivals = uiState.likedFestivals,
                )
            }
        }
    }

    if (uiState.isLikedFestivalDeleteDialogVisible) {
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
private fun SchoolSearchBottomSheetPreview(
    @PreviewParameter(FestivalPreviewParameterProvider::class)
    festivalUiState: FestivalUiState,
) {
    UnifestTheme {
        FestivalSearchBottomSheet(
            uiState = festivalUiState,
            onFestivalUiAction = {},
        )
    }
}
