package com.unifest.android.feature.booth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.component.UnifestButton
import com.unifest.android.core.designsystem.component.UnifestOutlinedButton
import com.unifest.android.core.designsystem.theme.BoothCaution
import com.unifest.android.core.designsystem.theme.BoothTitle1
import com.unifest.android.core.designsystem.theme.Content2
import com.unifest.android.core.designsystem.theme.MenuPrice
import com.unifest.android.core.designsystem.theme.MenuTitle
import com.unifest.android.core.designsystem.theme.Title2
import com.unifest.android.core.designsystem.theme.Title4
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.domain.entity.BoothDetailEntity
import com.unifest.android.core.domain.entity.MenuEntity
import com.unifest.android.core.ui.DevicePreview
import com.unifest.android.feature.booth.viewmodel.BoothUiState
import com.unifest.android.feature.booth.viewmodel.BoothViewModel
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.unifest.android.core.designsystem.component.TopAppBarNavigationType
import com.unifest.android.core.designsystem.component.UnifestTopAppBar
import kotlinx.coroutines.launch

@Composable
internal fun BoothDetailRoute(
    onBackClick: () -> Unit,
    onNavigateToBoothLocation: () -> Unit,
    viewModel: BoothViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BoothDetailScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onNavigateToBoothLocation = onNavigateToBoothLocation,
        onBookmarkClick = { viewModel.toggleBookmark() },
        isBookmarked = uiState.isBookmarked,
        bookmarkCount = uiState.bookmarkCount,
    )
}


@Composable
fun BoothDetailScreen(
    uiState: BoothUiState,
    onBackClick: () -> Unit,
    onNavigateToBoothLocation: () -> Unit,
    onBookmarkClick: () -> Unit,
    isBookmarked: Boolean,
    bookmarkCount: Int,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
//    var showWaitingDialog by remember { mutableStateOf(false) }
//    var showConfirmationDialog by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            BottomBarWithBookmarkAndWaiting(
                bookmarkCount = bookmarkCount,
                isBookmarked = isBookmarked,
                onBookmarkClick = {
                    scope.launch {
                        snackBarHostState.showSnackbar(
                            message = if (isBookmarked) {
                                "관심 주점에서 삭제되었습니다."
                            } else {
                                "관심 주점으로 저장되었습니다."
                            },
                            duration = SnackbarDuration.Short,
                        )
                    }
                    onBookmarkClick()
                },
                onWaitingClick = { /*showWaitingDialog = true*/ },
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
    ) { innerPadding ->
        BoothContent(uiState, onNavigateToBoothLocation, innerPadding, onBackClick)
//        if (showWaitingDialog) {
//            WaitingDialog(
//                boothName = uiState.boothDetailInfo.name,
//                onDismissRequest = { showWaitingDialog = false },
//                onWaitingConfirm = { waitingCount, phoneNumber ->
//
//                    viewModel.addToWaitingList(waitingCount, phoneNumber)
//                    showWaitingDialog = false
//                    showConfirmationDialog = true
//                },
//            )
//        }
//
//        if (showConfirmationDialog) {
//            WaitingConfirmDialog(
//                boothName = uiState.boothDetailInfo.name,
//                onDismissRequest = { showConfirmationDialog = false },
//                onWaitingConfirm = { waitingCount, phoneNumber ->
//
//                    showConfirmationDialog = false
//
//                }
//            )
//        }
    }
}


@Composable
fun BoothContent(
    uiState: BoothUiState,
    onNavigateToBoothLocation: () -> Unit,
    paddingValues: PaddingValues,
    onBackClick: () -> Unit = {},
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
    ) {

        item {
            Box {
                BoothImage()
                UnifestTopAppBar(
                    navigationType = TopAppBarNavigationType.Back,
                    navigationIcon = R.drawable.ic_arrow_back_gray,
                    onNavigationClick = onBackClick,
                    containerColor = Color.Transparent,
                )
            }
        }
        item { Spacer(modifier = Modifier.height(30.dp)) }
        item {
            BoothDescription(
                name = uiState.boothDetailInfo.name,
                category = uiState.boothDetailInfo.category,
                description = uiState.boothDetailInfo.description,
                location = uiState.boothDetailInfo.location,
                onNavigateToBoothLocation = onNavigateToBoothLocation,
            )
        }
        item { Spacer(modifier = Modifier.height(20.dp)) }
        item { MenuText() }
        items(uiState.boothDetailInfo.menus) { menu ->
            MenuItem(menu)
        }
    }
}


@Composable
fun BottomBarWithBookmarkAndWaiting(
    bookmarkCount: Int,
    isBookmarked: Boolean,
    onBookmarkClick: () -> Unit,
    onWaitingClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val bookMarkColor = if (isBookmarked) Color(0xFFF5687E) else Color(0xFF4B4B4B)
    Box(
        modifier = modifier
            .background(Color.White)
            .height(116.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                IconButton(onClick = onBookmarkClick) {
                    Icon(
                        painter = painterResource(id = if (isBookmarked) R.drawable.ic_bookmarked else R.drawable.ic_bookmark),
                        contentDescription = if (isBookmarked) "북마크됨" else "북마크하기",
                        tint = bookMarkColor,
                    )
                }
                Text(
                    text = "$bookmarkCount",
                    color = bookMarkColor,
                )
            }

            UnifestButton(
                onClick = onWaitingClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp),
                contentPadding = PaddingValues(vertical = 15.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.booth_waiting_button),
                    style = Title4,
                    fontSize = 14.sp,
                )
            }
        }
    }
}



@Composable
fun BoothImage() {
    Image(
        painter = painterResource(id = R.drawable.booth_image_example),
        modifier = Modifier
            .height(237.dp)
            .fillMaxWidth()
            .width(394.dp)
            .fillMaxWidth(),
        contentDescription = "Booth",
    )
}

@Composable
fun BoothDescription(
    name: String,
    category: String,
    description: String,
    location: String,
    onNavigateToBoothLocation: () -> Unit,
) {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = name,
                style = BoothTitle1,
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = category,
                style = BoothCaution,
                color = Color(0xFFF5687E),
                modifier = Modifier.align(Alignment.Bottom),
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = description,
            modifier = Modifier.padding(top = 8.dp),
            style = Content2.copy(lineHeight = 18.sp),
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 8.dp),
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_location_green),
                contentDescription = "location icon",
                tint = Color.Unspecified,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = location,
                style = Content2,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        UnifestOutlinedButton(
            onClick = onNavigateToBoothLocation,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = stringResource(id = R.string.booth_check_locaiton))
        }
    }
}

@Composable
fun MenuText() {
    Text(
        text = stringResource(id = R.string.booth_menu),
        modifier = Modifier.padding(start = 20.dp),
        style = Title2,
    )
}

@Composable
fun MenuItem(menu: MenuEntity) {
    Row(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.booth_menu_image_example),
            contentDescription = menu.name,
            modifier = Modifier.size(88.dp),
        )
        Spacer(modifier = Modifier.width(13.dp))
        Column(
            modifier = Modifier.align(Alignment.CenterVertically),
        ) {
            Text(
                text = menu.name,
                style = MenuTitle,
                color = Color(0xFF545454),
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = "${menu.price}원",
                style = MenuPrice,
            )
        }
    }
}


@DevicePreview
@Composable
fun BoothScreenPreview() {
    UnifestTheme {
        BoothDetailScreen(
            uiState = BoothUiState(
                boothDetailInfo = BoothDetailEntity(
                    id = 0L,
                    name = "컴공 주점",
                    category = "컴퓨터공학부 전용 부스",
                    description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                    location = "청심대 앞",
                    latitude = 37.54224856023523f,
                    longitude = 127.07605430700158f,
                    menus = listOf(
                        MenuEntity(1L, "모둠 사시미", 45000, ""),
                        MenuEntity(2L, "모둠 사시미", 45000, ""),
                        MenuEntity(3L, "모둠 사시미", 45000, ""),
                        MenuEntity(4L, "모둠 사시미", 45000, ""),
                    ),
                ),
            ),
            onBackClick = {},
            onNavigateToBoothLocation = {},
            onBookmarkClick = {},
            isBookmarked = false,
            bookmarkCount = 0,
        )
    }
}

//@Composable
//fun WaitingDialog(
//    boothName: String,
//    onDismissRequest: () -> Unit,
//    onWaitingConfirm: (Int, String) -> Unit,
//) {
//    var waitingCount by remember { mutableIntStateOf(0) }
//    var phoneNumber by remember { mutableStateOf("") }
//    //https://stackoverflow.com/questions/65243956/jetpack-compose-fullscreen-dialog
//    Dialog(
//        properties = DialogProperties(usePlatformDefaultWidth = false),
//        onDismissRequest = onDismissRequest,
//    ) {
//        Box(
//            Modifier
//                .background(Color.Black.copy(alpha = 0.6f))
//                .fillMaxSize(),
//        ) {
//            Card(
//                modifier = Modifier
//                    .align(Alignment.Center)
//                    .padding(horizontal = 32.dp)
//                    .background(Color.White),
//            ) {
//                Column(
//                    modifier = Modifier
//                        .padding(16.dp)
//                        .fillMaxWidth(),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                ) {
//                    Row {
//                        Icon(
//                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_location_green),
//                            contentDescription = "location icon",
//                            tint = Color.Unspecified,
//                        )
//                        Spacer(modifier = Modifier.width(2.dp))
//                        Text(text = boothName, style = Title3)
//
//
//                    }
//
//                    Spacer(modifier = Modifier.height(5.dp))
//                    Text(text = "현재 내 앞 웨이팅", style = BoothLocation)
//                    Spacer(modifier = Modifier.height(4.dp))
//
//                    Text(text = "$waitingCount 팀", style = WaitingTeam)
//                    Spacer(modifier = Modifier.height(9.dp))
//
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                    ) {
//                        Text("인원 수", style = Title5, modifier = Modifier.padding(horizontal = 16.dp))
//                        Row {
//                            IconButton(
//                                onClick = { if (waitingCount > 0) waitingCount-- },
//                            )
//                            { Icon(Icons.Default.Remove, contentDescription = "Decrease",) }
//                            Spacer(modifier = Modifier.width(20.dp))
//                            Text(
//                                "$waitingCount",
//                                Modifier.padding(horizontal = 16.dp),
//                            )
//                            Spacer(modifier = Modifier.width(20.dp))
//                            IconButton(
//                                onClick = { waitingCount++ },
//                            )
//                            { Icon(Icons.Default.Add, contentDescription = "Increase",) }
//                        }
//                    }
//                    OutlinedTextField(
//                        value = phoneNumber,
//                        onValueChange = { phoneNumber = it },
//                        label = { Text("전화번호 입력", style = BoothLocation) },
//                        singleLine = true,
//                    )
//                    Spacer(modifier = Modifier.height(16.dp))
//                    UnifestButton(
//                        onClick = { onWaitingConfirm(waitingCount, phoneNumber) },
//                        modifier = Modifier.fillMaxWidth(),
//                    ) {
//                        Text("웨이팅 신청")
//                    }
//                }
//            }
//        }
//    }
//}
//
//
//@Composable
//fun WaitingConfirmDialog(
//    boothName: String,
//    onDismissRequest: () -> Unit,
//    onWaitingConfirm: (Int, String) -> Unit,
//) {
//    //https://stackoverflow.com/questions/65243956/jetpack-compose-fullscreen-dialog
//    Dialog(
//        properties = DialogProperties(usePlatformDefaultWidth = false),
//        onDismissRequest = onDismissRequest
//    ) {
//        Box(
//            Modifier
//                .background(Color.Black.copy(alpha = 0.6f))
//                .fillMaxSize(),
//        ) {
//            Card(
//                modifier = Modifier
//                    .align(Alignment.Center)
//                    .padding(horizontal = 32.dp)
//                    .background(Color.White),
//            ) {
//                Column(
//                    modifier = Modifier
//                        .padding(16.dp)
//                        .fillMaxWidth(),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                ) {
//                    Row {
//                        Icon(
//                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_location_green),
//                            contentDescription = "location icon",
//                            tint = Color.Unspecified,
//                        )
//                        Spacer(modifier = Modifier.width(2.dp))
//                        Text(text = boothName, style = Title3)
//
//
//                    }
//
//                    Spacer(modifier = Modifier.height(5.dp))
//                    Text(text = "웨이팅 등록 완료!", style = Title1)
//                    Spacer(modifier = Modifier.height(4.dp))
//                    Text(text = "입장 순서가 되면 안내 해드릴게요.", style = Content2)
//                    Spacer(modifier = Modifier.height(16.dp))
//                    Row {
//                        Column {
//                            Text("웨이팅 번호", style = Title5)
//                            Text("112번", style = WaitingTeam)
//                        }
//                        Spacer(modifier = Modifier.width(40.dp))
//                        Column {
//                            Text("인원수", style = Title5)
//                            Text("3명", style = WaitingTeam)
//                        }
//                        Spacer(modifier = Modifier.width(40.dp))
//                        Column {
//                            Text("내 앞 웨이팅", style = Title5)
//                            Text("35팀", style = WaitingTeam)
//                        }
//                    }
//                    Spacer(modifier = Modifier.height(16.dp))
//                    Row {
//                        UnifestOutlinedButton(onClick = { /*TODO*/ }, borderColor = Color(0xFFD2D2D2), contentColor = Color.Black) {
//                            Text(text = "웨이팅 취소")
//                        }
//                        Spacer(modifier = Modifier.width(6.dp))
//                        UnifestButton(onClick = { /*TODO*/ }) {
//                            Text(text = "순서 확인하기")
//                        }
//                    }
//
//                }
//            }
//        }
//    }
//}
