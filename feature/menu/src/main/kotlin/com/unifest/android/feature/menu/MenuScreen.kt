package com.unifest.android.feature.menu

import android.content.pm.PackageManager
import android.widget.Toast
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unifest.android.core.common.ObserveAsEvents
import com.unifest.android.core.common.UiText
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.component.NetworkErrorDialog
import com.unifest.android.core.designsystem.component.NetworkImage
import com.unifest.android.core.designsystem.component.ServerErrorDialog
import com.unifest.android.core.designsystem.component.TopAppBarNavigationType
import com.unifest.android.core.designsystem.component.UnifestHorizontalDivider
import com.unifest.android.core.designsystem.component.UnifestTopAppBar
import com.unifest.android.core.designsystem.theme.Content6
import com.unifest.android.core.designsystem.theme.Content7
import com.unifest.android.core.designsystem.theme.Content8
import com.unifest.android.core.designsystem.theme.MenuTitle
import com.unifest.android.core.designsystem.theme.Title3
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.core.model.LikedBoothModel
import com.unifest.android.core.ui.DevicePreview
import com.unifest.android.core.ui.component.EmptyLikedBoothItem
import com.unifest.android.core.ui.component.LikedBoothItem
import com.unifest.android.feature.festival.FestivalSearchBottomSheet
import com.unifest.android.feature.festival.viewmodel.FestivalUiAction
import com.unifest.android.feature.festival.viewmodel.FestivalUiEvent
import com.unifest.android.feature.festival.viewmodel.FestivalUiState
import com.unifest.android.feature.festival.viewmodel.FestivalViewModel
import com.unifest.android.feature.menu.viewmodel.ErrorType
import com.unifest.android.feature.menu.viewmodel.MenuUiAction
import com.unifest.android.feature.menu.viewmodel.MenuUiEvent
import com.unifest.android.feature.menu.viewmodel.MenuUiState
import com.unifest.android.feature.menu.viewmodel.MenuViewModel
import kotlinx.collections.immutable.persistentListOf
import timber.log.Timber

@Composable
internal fun MenuRoute(
    padding: PaddingValues,
    popBackStack: () -> Unit,
    navigateToLikedBooth: () -> Unit,
    navigateToBoothDetail: (Long) -> Unit,
    onShowSnackBar: (UiText) -> Unit,
    menuViewModel: MenuViewModel = hiltViewModel(),
    festivalViewModel: FestivalViewModel = hiltViewModel(),
) {
    val menuUiState by menuViewModel.uiState.collectAsStateWithLifecycle()
    val festivalUiState by festivalViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    val appVersion = remember {
        try {
            context.packageManager.getPackageInfo(context.packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            Timber.tag("AppVersion").e(e, "Failed to get package info")
            "Unknown"
        }
    }

    LaunchedEffect(key1 = null) {
        menuViewModel.getLikedBooths()
    }

    ObserveAsEvents(flow = menuViewModel.uiEvent) { event ->
        when (event) {
            is MenuUiEvent.NavigateBack -> popBackStack()
            is MenuUiEvent.NavigateToLikedBooth -> navigateToLikedBooth()
            is MenuUiEvent.NavigateToBoothDetail -> navigateToBoothDetail(event.boothId)
            is MenuUiEvent.NavigateToContact -> uriHandler.openUri(BuildConfig.UNIFEST_CONTACT_URL)
            is MenuUiEvent.NavigateToAdministratorMode -> uriHandler.openUri(BuildConfig.UNIFEST_WEB_URL)
            is MenuUiEvent.ShowSnackBar -> onShowSnackBar(event.message)
            is MenuUiEvent.ShowToast -> Toast.makeText(context, event.message.asString(context), Toast.LENGTH_SHORT).show()
        }
    }

    ObserveAsEvents(flow = festivalViewModel.uiEvent) { event ->
        when (event) {
            is FestivalUiEvent.NavigateBack -> popBackStack()
            is FestivalUiEvent.ShowSnackBar -> onShowSnackBar(event.message)
            is FestivalUiEvent.ShowToast -> Toast.makeText(context, event.message.asString(context), Toast.LENGTH_SHORT).show()
        }
    }

    MenuScreen(
        padding = padding,
        menuUiState = menuUiState,
        festivalUiState = festivalUiState,
        appVersion = appVersion,
        onMenuUiAction = menuViewModel::onMenuUiAction,
        onFestivalUiAction = festivalViewModel::onFestivalUiAction,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MenuScreen(
    padding: PaddingValues,
    menuUiState: MenuUiState,
    festivalUiState: FestivalUiState,
    appVersion: String,
    onMenuUiAction: (MenuUiAction) -> Unit,
    onFestivalUiAction: (FestivalUiAction) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(padding),
    ) {
        Column {
            UnifestTopAppBar(
                navigationType = TopAppBarNavigationType.None,
                title = stringResource(id = R.string.menu_title),
                elevation = 8.dp,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp),
                    )
                    .padding(top = 13.dp, bottom = 5.dp),
            )
            LazyColumn {
                item { Spacer(modifier = Modifier.height(5.dp)) }
                item {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(top = 10.dp, start = 20.dp),
                    ) {
                        Text(
                            text = stringResource(id = R.string.menu_my_liked_festival),
                            color = MaterialTheme.colorScheme.onBackground,
                            style = Title3,
                        )
                        TextButton(
                            onClick = { onFestivalUiAction(FestivalUiAction.OnAddLikedFestivalClick) },
                            modifier = Modifier.padding(end = 8.dp),
                        ) {
                            Text(
                                text = stringResource(id = R.string.menu_add),
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                style = Content7,
                                textDecoration = TextDecoration.Underline,
                            )
                        }
                    }
                }
                item {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .height(if (menuUiState.likedFestivals.isEmpty()) 0.dp else ((menuUiState.likedFestivals.size / 5 + 1) * 140).dp),
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(
                            menuUiState.likedFestivals.size,
                            key = { index -> menuUiState.likedFestivals[index].festivalId },
                        ) { index ->
                            FestivalItem(
                                festival = menuUiState.likedFestivals[index],
                                onMenuUiAction = onMenuUiAction,
                            )
                        }
                    }
                }
                item { UnifestHorizontalDivider() }
                item {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(start = 20.dp, top = 10.dp),
                    ) {
                        Text(
                            text = stringResource(id = R.string.menu_liked_booth),
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold,
                            style = Title3,
                        )
                        TextButton(
                            onClick = { onMenuUiAction(MenuUiAction.OnShowMoreClick) },
                            modifier = Modifier.padding(end = 8.dp),
                        ) {
                            Text(
                                text = stringResource(id = R.string.menu_watch_more),
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                style = Content7,
                                textDecoration = TextDecoration.Underline,
                            )
                        }
                    }
                }
                if (menuUiState.likedBooths.isEmpty()) {
                    item {
                        EmptyLikedBoothItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(248.dp),
                        )
                    }
                } else {
                    itemsIndexed(
                        items = menuUiState.likedBooths.take(3),
                        key = { _, booth -> booth.id },
                    ) { index, booth ->
                        LikedBoothItem(
                            booth = booth,
                            index = index,
                            totalCount = menuUiState.likedBooths.size,
                            deleteLikedBooth = { onMenuUiAction(MenuUiAction.OnToggleBookmark(booth)) },
                            modifier = Modifier
                                .clickable {
                                    onMenuUiAction(MenuUiAction.OnLikedBoothItemClick(booth.id))
                                }
                                .animateItemPlacement(
                                    animationSpec = tween(
                                        durationMillis = 500,
                                        easing = LinearOutSlowInEasing,
                                    ),
                                ),
                        )
                    }
                }
                item { UnifestHorizontalDivider() }
                item {
                    MenuItem(
                        icon = ImageVector.vectorResource(R.drawable.ic_inquiry),
                        title = stringResource(id = R.string.menu_questions),
                        onClick = { onMenuUiAction(MenuUiAction.OnContactClick) },
                    )
                }
                item {
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                    )
                }
                item {
                    MenuItem(
                        icon = ImageVector.vectorResource(R.drawable.ic_admin_mode),
                        title = stringResource(id = R.string.menu_admin_mode),
                        onClick = {
                            onMenuUiAction(MenuUiAction.OnAdministratorModeClick)
                        },
                    )
                }
                item {
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                    )
                }
                item {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 13.dp),
                    ) {
                        Text(
                            text = "UniFest v$appVersion",
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                        )
                    }
                }
            }
        }
        if (menuUiState.isServerErrorDialogVisible) {
            ServerErrorDialog(
                onRetryClick = { onMenuUiAction(MenuUiAction.OnRetryClick(ErrorType.SERVER)) },
            )
        }

        if (menuUiState.isNetworkErrorDialogVisible) {
            NetworkErrorDialog(
                onRetryClick = { onMenuUiAction(MenuUiAction.OnRetryClick(ErrorType.NETWORK)) },
            )
        }

        if (festivalUiState.isFestivalSearchBottomSheetVisible) {
            FestivalSearchBottomSheet(
                searchText = festivalUiState.festivalSearchText,
                searchTextHintRes = R.string.festival_search_text_field_hint,
                likedFestivals = festivalUiState.likedFestivals,
                festivalSearchResults = festivalUiState.festivalSearchResults,
                isLikedFestivalDeleteDialogVisible = festivalUiState.isLikedFestivalDeleteDialogVisible,
                isSearchMode = festivalUiState.isSearchMode,
                isEditMode = festivalUiState.isEditMode,
                onFestivalUiAction = onFestivalUiAction,
            )
        }
    }
}

@Composable
fun FestivalItem(
    festival: FestivalModel,
    onMenuUiAction: (MenuUiAction) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(vertical = 10.dp)
            .clickable {
                onMenuUiAction(MenuUiAction.OnLikedFestivalItemClick(festival.schoolName))
            },
    ) {
        Box(
            modifier = Modifier
                .size(65.dp)
                .shadow(
                    elevation = 6.dp,
                    shape = CircleShape,
                )
                .background(MaterialTheme.colorScheme.tertiaryContainer, CircleShape)
                .padding(5.dp),
            contentAlignment = Alignment.Center,
        ) {
            NetworkImage(
                imgUrl = festival.thumbnail,
                contentDescription = "Festival Thumbnail",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                placeholder = painterResource(id = R.drawable.item_placeholder),
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = festival.schoolName,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = Content6,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = festival.festivalName,
            color = MaterialTheme.colorScheme.onBackground,
            style = MenuTitle,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun MenuItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .clickable(onClick = onClick)
            .padding(25.dp),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Menu Icon",
            tint = Color.Unspecified,
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            style = Content8,
        )
    }
}

@DevicePreview
@Composable
fun MenuScreenPreview() {
    UnifestTheme {
        MenuScreen(
            padding = PaddingValues(),
            menuUiState = MenuUiState(
                festivals = persistentListOf(
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
                ),
                likedBooths = persistentListOf(
                    LikedBoothModel(
                        id = 1,
                        name = "부스 이름",
                        category = "부스 카테고리",
                        description = "부스 설명",
                        location = "부스 위치",
                        warning = "학과 전용 부스",
                    ),
                    LikedBoothModel(
                        id = 2,
                        name = "부스 이름",
                        category = "부스 카테고리",
                        description = "부스 설명",
                        location = "부스 위치",
                        warning = "학과 전용 부스",
                    ),
                ),
            ),
            festivalUiState = FestivalUiState(),
            appVersion = "1.0.0",
            onMenuUiAction = {},
            onFestivalUiAction = {},
        )
    }
}
