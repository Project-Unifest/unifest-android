package com.unifest.android.feature.menu

import android.content.pm.PackageManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.domain.entity.MenuEntity
import com.unifest.android.core.ui.DevicePreview
import kotlinx.collections.immutable.persistentListOf
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.component.NetworkImage
import com.unifest.android.core.designsystem.component.TopAppBarNavigationType
import com.unifest.android.core.designsystem.component.UnifestTopAppBar
import com.unifest.android.core.designsystem.theme.Content6
import com.unifest.android.core.designsystem.theme.Content7
import com.unifest.android.core.designsystem.theme.Content8
import com.unifest.android.core.designsystem.theme.MenuTitle
import com.unifest.android.core.designsystem.theme.Title2
import com.unifest.android.core.designsystem.theme.Title3
import com.unifest.android.core.designsystem.theme.Title5
import com.unifest.android.core.domain.entity.BoothDetailEntity
import com.unifest.android.core.domain.entity.Festival
import com.unifest.android.feature.menu.viewmodel.MenuUiState
import com.unifest.android.feature.menu.viewmodel.MenuViewModel
import timber.log.Timber

@Composable
internal fun MenuRoute(
    padding: PaddingValues,
    onNavigateToIntro: () -> Unit,
    onNavigateToInterestedBooths: () -> Unit,
    viewModel: MenuViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    MenuScreen(
        padding = padding,
        uiState = uiState,
        onNavigateToIntro = onNavigateToIntro,
        onNavigateToInterestedBooths = onNavigateToInterestedBooths,
    )
}

@Composable
fun MenuScreen(
    padding: PaddingValues,
    uiState: MenuUiState,
    onNavigateToIntro: () -> Unit,
    onNavigateToInterestedBooths: () -> Unit,
) {
    val context = LocalContext.current
    val appVersion = remember {
        try {
            context.packageManager.getPackageInfo(context.packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            Timber.tag("AppVersion").e(e, "Failed to get package info")
            "Unknown"
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
    ) {
        Column {
            UnifestTopAppBar(
                navigationType = TopAppBarNavigationType.None,
                title = stringResource(id = R.string.menu_title),
                elevation = 8.dp,
                modifier = Modifier
                    .background(
                        Color.White,
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
                            .padding(top = 10.dp, start = 20.dp),
                    ) {
                        Text(
                            text = stringResource(id = R.string.menu_my_interested_festival),
                            style = Title3,
                        )
                        TextButton(
                            onClick = { onNavigateToIntro() },
                            modifier = Modifier.padding(end = 8.dp),
                        ) {
                            Text(
                                text = stringResource(id = R.string.menu_add),
                                style = Content7,
                                color = Color(0xFF545454),
                            )
                        }
                    }
                }
                item {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .height(
                                when {
                                    uiState.festivals.isEmpty() -> 0.dp
                                    else -> {
                                        val rows = ((uiState.festivals.size - 1) / 4 + 1) * 140
                                        rows.dp
                                    }
                                },
                            ),
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(
                            uiState.festivals.size,
                            key = { index -> uiState.festivals[index].schoolName },
                        ) { index ->
                            FestivalItem(
                                festival = uiState.festivals[index],
                            )
                        }
                    }
                }
                item {
                    VerticalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .background(Color(0xFFF1F3F7)),
                    )
                }
                item {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, top = 10.dp),
                    ) {
                        Text(text = stringResource(id = R.string.menu_interested_booths))
                        TextButton(
                            onClick = { onNavigateToInterestedBooths() },
                            modifier = Modifier.padding(end = 8.dp),
                        ) {
                            Text(
                                text = stringResource(id = R.string.menu_watch_more),
                                style = Content7,
                                color = Color(0xFF545454),
                            )
                        }
                    }
                }
                itemsIndexed(uiState.interestedBooths.take(3), key = { _, booth -> booth.id }) { index, booth ->
                    InterestedBoothsItems(booth, index, uiState.interestedBooths.size)
                }
                item {
                    VerticalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .background(Color(0xFFF1F3F7)),
                    )
                }
                item {
                    MenuItem(
                        ImageVector.vectorResource(R.drawable.ic_inquiry),
                        title = stringResource(id = R.string.menu_questions),
                        onClick = { /* 구현 */ },
                    )
                }
                item {
                    VerticalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color(0xFFE0E0E0)),
                    )
                }
                item {
                    MenuItem(
                        ImageVector.vectorResource(R.drawable.ic_admin_mode),
                        title = stringResource(id = R.string.menu_admin_mode),
                        onClick = { /* 구현 */ },
                    )
                }
                item {
                    VerticalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color(0xFFE0E0E0)),
                    )
                }
                item {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 13.dp),
                    ) {
                        Text("UniFest v$appVersion", textAlign = TextAlign.Center, color = Color(0xFFC5C5C5))
                    }
                }
            }
        }
    }
}

@Composable
fun FestivalItem(
    festival: Festival,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(vertical = 10.dp),
    ) {
        Box(
            modifier = Modifier
                .size(65.dp)
                .shadow(
                    elevation = 6.dp,
                    shape = CircleShape,
                )
                .background(Color.White, CircleShape)
                .padding(5.dp),
            contentAlignment = Alignment.Center,
        ) {
            NetworkImage(
                imageUrl = "https://picsum.photos/86",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = festival.schoolName,
            color = Color(0xFF545454),
            style = Content6,
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = festival.festivalName,
            color = Color.Black,
            style = MenuTitle,
        )
    }
}

@Composable
fun InterestedBoothsItems(booth: BoothDetailEntity, index: Int, total: Int) {
    var isBookmarked by remember { mutableStateOf(true) }
    val bookMarkColor = if (isBookmarked) Color(0xFFF5687E) else Color(0xFF4B4B4B)
    Column(
        modifier = Modifier
            .clickable { /* 클릭 이벤트 처리 */ }
            .padding(horizontal = 20.dp),
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxSize(),
        ) {
            NetworkImage(
                imageUrl = "https://picsum.photos/86",
                modifier = Modifier
                    .size(86.dp)
                    .clip(RoundedCornerShape(16.dp)),
            )
            Spacer(modifier = Modifier.width(14.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            ) {
                Text(
                    text = booth.name,
                    style = Title2,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = booth.description,
                    style = Title5,
                    color = Color(0xFF545454),
                )
                Spacer(modifier = Modifier.height(13.dp))
                Row {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_location_green),
                        contentDescription = "Location Icon",
                        tint = Color.Unspecified,
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = booth.location,
                        style = Title5,
                        color = Color(0xFF545454),
                        modifier = Modifier.align(Alignment.CenterVertically),
                    )
                }
            }
            Icon(
                imageVector = ImageVector.vectorResource(if (isBookmarked) R.drawable.ic_bookmarked else R.drawable.ic_bookmark),
                contentDescription = if (isBookmarked) "북마크됨" else "북마크하기",
                tint = bookMarkColor,
                modifier = Modifier
                    .size(24.dp)
                    .clickable(onClick = { isBookmarked = !isBookmarked }),
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (index < total - 1) {
            HorizontalDivider(
                color = Color(0xFFDFDFDF),
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
fun MenuItem(icon: ImageVector, title: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(25.dp),
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = Color(0xFF7A7A7C),
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, style = Content8)
    }
}

@DevicePreview
@Composable
fun MenuScreenPreview() {
    UnifestTheme {
        MenuScreen(
            padding = PaddingValues(0.dp),
            onNavigateToIntro = { },
            onNavigateToInterestedBooths = { },
            uiState = MenuUiState(
                festivals = persistentListOf(
                    Festival(
                        schoolName = "건국대",
                        festivalName = "녹색지대",
                        festivalDate = "2021.11.11",
                        imgUrl = "",
                    ),
                    Festival(
                        schoolName = "건국대",
                        festivalName = "녹색지대",
                        festivalDate = "2021.11.11",
                        imgUrl = "",
                    ),
                ),
                interestedBooths = persistentListOf(
                    BoothDetailEntity(
                        id = 1,
                        name = "부스 이름",
                        category = "음식",
                        description = "부스 설명",
                        warning = "주의사항",
                        location = "부스 위치",
                        latitude = 0.0f,
                        longitude = 0.0f,
                        menus = listOf(
                            MenuEntity(
                                id = 1,
                                name = "메뉴 이름",
                                price = 1000,
                                imgUrl = "",
                            ),
                        ),
                    ),
                    BoothDetailEntity(
                        id = 2,
                        name = "부스 이름",
                        category = "음식",
                        description = "부스 설명",
                        warning = "주의사항",
                        location = "부스 위치",
                        latitude = 0.0f,
                        longitude = 0.0f,
                        menus = listOf(
                            MenuEntity(
                                id = 1,
                                name = "메뉴 이름",
                                price = 1000,
                                imgUrl = "",
                            ),
                        ),
                    ),
                ),
            ),
        )
    }
}
