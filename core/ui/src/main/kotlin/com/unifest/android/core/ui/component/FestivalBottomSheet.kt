package com.unifest.android.core.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.compose.Balloon
import com.skydoves.balloon.compose.rememberBalloonBuilder
import com.skydoves.balloon.compose.setBackgroundColor
import com.unifest.android.core.common.ButtonType
import com.unifest.android.core.common.FestivalUiAction
import com.unifest.android.core.common.extension.noRippleClickable
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.component.FestivalSearchTextField
import com.unifest.android.core.designsystem.component.LikedFestivalDeleteDialog
import com.unifest.android.core.designsystem.theme.Content3
import com.unifest.android.core.designsystem.theme.Content5
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.model.FestivalModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FestivalSearchBottomSheet(
    @StringRes searchTextHintRes: Int,
    searchText: TextFieldValue,
    likedFestivals: MutableList<FestivalModel>,
    festivalSearchResults: ImmutableList<FestivalModel>,
    isSearchMode: Boolean,
    isLikedFestivalDeleteDialogVisible: Boolean,
    onFestivalUiAction: (FestivalUiAction) -> Unit,
    isOnboardingCompleted: Boolean = true,
    isEditMode: Boolean = false,
) {
    val selectedFestivals = remember { mutableStateListOf<FestivalModel>() }
    var deleteSelectedFestival by remember { mutableStateOf<FestivalModel?>(null) }
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
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 18.dp)
    ) {
        val scope = rememberCoroutineScope()
        val builder = rememberBalloonBuilder {
            setArrowSize(10)
            setArrowPosition(0.1f)
            setArrowOrientation(ArrowOrientation.BOTTOM)
            setWidth(BalloonSizeSpec.WRAP)
            setHeight(BalloonSizeSpec.WRAP)
            setPadding(9)
            setCornerRadius(8f)
            setBackgroundColor(Color(0xFFF5687E))
            setBalloonAnimation(BalloonAnimation.FADE)
            setDismissWhenClicked(true)
            setDismissWhenTouchOutside(false)
            setFocusable(false)
        }
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
                VerticalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .background(Color(0xFFF1F3F7)),
                )
                Spacer(modifier = Modifier.height(21.dp))
                LikedFestivalsGrid(
                    selectedFestivals = likedFestivals,
                    onFestivalSelected = { festival ->
                        selectedFestivals.remove(festival)
                    },
                    isEditMode = isEditMode,
                    onDeleteLikedFestivalClick = { festival ->
                        deleteSelectedFestival = festival
                        onFestivalUiAction(FestivalUiAction.OnDeleteIconClick)
                    },
                    tooltip = {
                        if (!isOnboardingCompleted) {
                            Balloon(
                                builder = builder,
                                balloonContent = {
                                    Text(
                                        modifier = Modifier
                                            .wrapContentWidth()
                                            .noRippleClickable {
                                                onFestivalUiAction(FestivalUiAction.OnTooltipClick)
                                            },
                                        text = stringResource(id = R.string.festival_search_onboarding_title),
                                        textAlign = TextAlign.Center,
                                        color = Color.White,
                                        style = Content5,
                                    )
                                },
                            ) { balloonWindow ->
                                LaunchedEffect(key1 = Unit) {
                                    scope.launch {
                                        delay(500L)
                                        balloonWindow.awaitAlignEnd()
                                    }
                                }
                            }
                        }
                    },
                    optionTextButton = {
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
                    },
                )
            } else {
                FestivalSearchResults(
                    searchResults = festivalSearchResults,
                    onFestivalUiAction = onFestivalUiAction,
                )
            }
        }
        if (isLikedFestivalDeleteDialogVisible) {
            LikedFestivalDeleteDialog(
                onCancelClick = {
                    onFestivalUiAction(FestivalUiAction.OnDialogButtonClick(ButtonType.CANCEL))
                },
                onConfirmClick = {
                    onFestivalUiAction(FestivalUiAction.OnDialogButtonClick(ButtonType.CONFIRM, deleteSelectedFestival))
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
            isSearchMode = false,
            isEditMode = false,
            isLikedFestivalDeleteDialogVisible = false,
            isOnboardingCompleted = true,
            onFestivalUiAction = {},
        )
    }
}
