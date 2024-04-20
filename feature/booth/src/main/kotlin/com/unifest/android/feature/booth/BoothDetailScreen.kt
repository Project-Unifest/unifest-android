package com.unifest.android.feature.booth

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unifest.android.core.common.ObserveAsEvents
import com.unifest.android.core.common.utils.formatAsCurrency
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.component.NetworkImage
import com.unifest.android.core.designsystem.component.TopAppBarNavigationType
import com.unifest.android.core.designsystem.component.UnifestButton
import com.unifest.android.core.designsystem.component.UnifestOutlinedButton
import com.unifest.android.core.designsystem.component.UnifestTopAppBar
import com.unifest.android.core.designsystem.theme.BoothCaution
import com.unifest.android.core.designsystem.theme.BoothLocation
import com.unifest.android.core.designsystem.theme.BoothTitle1
import com.unifest.android.core.designsystem.theme.Content2
import com.unifest.android.core.designsystem.theme.MenuPrice
import com.unifest.android.core.designsystem.theme.MenuTitle
import com.unifest.android.core.designsystem.theme.Title2
import com.unifest.android.core.designsystem.theme.Title4
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.model.BoothDetailModel
import com.unifest.android.core.model.MenuModel
import com.unifest.android.core.ui.DevicePreview
import com.unifest.android.feature.booth.viewmodel.BoothUiAction
import com.unifest.android.feature.booth.viewmodel.BoothUiEvent
import com.unifest.android.feature.booth.viewmodel.BoothUiState
import com.unifest.android.feature.booth.viewmodel.BoothViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tech.thdev.compose.exteions.system.ui.controller.rememberExSystemUiController

private const val SnackBarDuration = 1000L

@Composable
internal fun BoothDetailRoute(
    padding: PaddingValues,
    onBackClick: () -> Unit,
    onNavigateToBoothLocation: () -> Unit,
    viewModel: BoothViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val systemUiController = rememberExSystemUiController()

    DisposableEffect(systemUiController) {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = false,
            isNavigationBarContrastEnforced = false,
        )
        onDispose {
            systemUiController.setSystemBarsColor(
                color = Color.White,
                darkIcons = true,
                isNavigationBarContrastEnforced = false,
            )
        }
    }

    ObserveAsEvents(flow = viewModel.uiEvent) { event ->
        when (event) {
            is BoothUiEvent.NavigateBack -> onBackClick()
            is BoothUiEvent.NavigateToBoothLocation -> onNavigateToBoothLocation()
        }
    }

    BoothDetailScreen(
        padding = padding,
        uiState = uiState,
        onAction = viewModel::onAction,
    )
}

@Composable
fun BoothDetailScreen(
    padding: PaddingValues,
    uiState: BoothUiState,
    onAction: (BoothUiAction) -> Unit,
) {
    val snackBarState = remember { SnackbarHostState() }

    Box(modifier = Modifier.fillMaxSize()) {
        BoothDetailContent(
            uiState = uiState,
            onAction = onAction,
            modifier = Modifier.padding(bottom = 116.dp),
        )
        UnifestTopAppBar(
            navigationType = TopAppBarNavigationType.Back,
            navigationIconRes = R.drawable.ic_arrow_back_gray,
            containerColor = Color.Transparent,
            onNavigationClick = { onAction(BoothUiAction.OnBackClick) },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(padding),
        )
        BottomBar(
            isBookmarked = uiState.isBookmarked,
            bookmarkCount = uiState.bookmarkCount,
            snackBarState = snackBarState,
            onAction = onAction,
            modifier = Modifier.align(Alignment.BottomCenter),
        )
        SnackbarHost(
            hostState = snackBarState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 112.dp),
        )
    }
}

@Composable
fun BoothDetailContent(
    uiState: BoothUiState,
    onAction: (BoothUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        item {
            BoothImage()
        }
        item { Spacer(modifier = Modifier.height(30.dp)) }
        item {
            BoothDescription(
                name = uiState.boothDetailInfo.name,
                category = uiState.boothDetailInfo.category,
                description = uiState.boothDetailInfo.description,
                location = uiState.boothDetailInfo.location,
                onAction = onAction,
            )
        }
        item { Spacer(modifier = Modifier.height(32.dp)) }
        item {
            VerticalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(Color(0xFFF1F3F7)),
            )
        }
        item { Spacer(modifier = Modifier.height(22.dp)) }
        item { MenuText() }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        items(uiState.boothDetailInfo.menus) { menu -> MenuItem(menu) }
    }
}

@Composable
fun BottomBar(
    bookmarkCount: Int,
    isBookmarked: Boolean,
    snackBarState: SnackbarHostState,
    onAction: (BoothUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val bookMarkColor = if (isBookmarked) Color(0xFFF5687E) else Color(0xFF4B4B4B)

    Surface(
        modifier = modifier.height(116.dp),
        shadowElevation = 32.dp,
        color = Color.White,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 27.dp, top = 15.dp, end = 15.dp, bottom = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier.background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(if (isBookmarked) R.drawable.ic_bookmarked else R.drawable.ic_bookmark),
                        contentDescription = if (isBookmarked) "북마크됨" else "북마크하기",
                        tint = bookMarkColor,
                        modifier = Modifier.clickable {
                            onAction(BoothUiAction.OnToggleBookmark)
                            scope.launch {
                                val job = launch {
                                    snackBarState.showSnackbar(
                                        message = if (isBookmarked) context.getString(R.string.booth_bookmark_removed_message)
                                        else context.getString(R.string.booth_bookmarked_message),
                                        duration = SnackbarDuration.Short,
                                    )
                                }
                                delay(SnackBarDuration)
                                job.cancel()
                            }
                        },
                    )
                    Text(
                        text = "$bookmarkCount",
                        color = bookMarkColor,
                        style = BoothCaution.copy(fontWeight = FontWeight.Bold),
                    )
                }
                Spacer(modifier = Modifier.width(18.dp))
                UnifestButton(
                    onClick = { /* showWaitingDialog = true */ },
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 15.dp),
                    enabled = false,
                    containerColor = Color(0xFF777777),
                ) {
                    Text(
                        text = stringResource(id = R.string.booth_waiting_button_invalid),
                        style = Title4,
                        fontSize = 14.sp,
                    )
                }
            }
        }
    }
}

@Composable
fun BoothImage() {
    NetworkImage(
        imageUrl = "https://picsum.photos/200/300",
        modifier = Modifier
            .height(260.dp)
            .fillMaxWidth(),
        contentDescription = "Booth Image",
    )
}

@Composable
fun BoothDescription(
    name: String,
    category: String,
    description: String,
    location: String,
    onAction: (BoothUiAction) -> Unit,
) {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = name,
                modifier = Modifier.alignByBaseline(),
                style = BoothTitle1,
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = category,
                modifier = Modifier.alignByBaseline(),
                style = BoothCaution,
                color = Color(0xFFF5687E),
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
                color = Color(0xFF393939),
                style = BoothLocation,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        UnifestOutlinedButton(
            onClick = { onAction(BoothUiAction.OnCheckLocationClick) },
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
fun MenuItem(menu: MenuModel) {
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
                text = menu.price.formatAsCurrency(),
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
            padding = PaddingValues(),
            uiState = BoothUiState(
                boothDetailInfo = BoothDetailModel(
                    id = 0L,
                    name = "컴공 주점",
                    category = "컴퓨터공학부 전용 부스",
                    description = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
                    warning = "",
                    location = "청심대 앞",
                    latitude = 37.54224856023523f,
                    longitude = 127.07605430700158f,
                    menus = listOf(
                        MenuModel(1L, "모둠 사시미", 45000, ""),
                        MenuModel(2L, "모둠 사시미", 45000, ""),
                        MenuModel(3L, "모둠 사시미", 45000, ""),
                        MenuModel(4L, "모둠 사시미", 45000, ""),
                    ),
                ),
            ),
            onAction = {},
        )
    }
}

// @Composable
// fun WaitingDialog(
//    boothName: String,
//    onDismissRequest: () -> Unit,
//    onWaitingConfirm: (Int, String) -> Unit,
// ) {
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
// }
//
//
// @Composable
// fun WaitingConfirmDialog(
//    boothName: String,
//    onDismissRequest: () -> Unit,
//    onWaitingConfirm: (Int, String) -> Unit,
// ) {
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
// }
