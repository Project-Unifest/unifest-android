package com.unifest.android.feature.interested_booth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckBoxOutlineBlank
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.component.NetworkImage
import com.unifest.android.core.designsystem.component.TopAppBarNavigationType
import com.unifest.android.core.designsystem.component.UnifestTopAppBar
import com.unifest.android.core.designsystem.theme.Title2
import com.unifest.android.core.designsystem.theme.Title5
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.domain.entity.BoothDetailEntity
import com.unifest.android.core.domain.entity.MenuEntity
import com.unifest.android.core.ui.DevicePreview
import com.unifest.android.feature.interested_booth.viewmodel.InterestedBoothUiState
import com.unifest.android.feature.interested_booth.viewmodel.InterestedBoothViewModel
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun InterestedBoothRoute(
    viewModel: InterestedBoothViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    InterestedBoothScreen(uiState = uiState)
}

@Composable
internal fun InterestedBoothScreen(
    uiState: InterestedBoothUiState,
) {
    Box {
        Column {
            UnifestTopAppBar(
                navigationType = TopAppBarNavigationType.Back,
                title = "관심 부스",
                elevation = 8.dp,
                modifier = Modifier
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp),
                    )
                    .padding(top = 13.dp, bottom = 5.dp),
            )
            LazyColumn {
                itemsIndexed(uiState.interestedBooths) { index, booth ->
                    InterestedBoothsItems(booth, index, uiState.interestedBooths.size)
                }
            }
        }
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

@DevicePreview
@Composable
fun InterestedBoothScreenPreview() {
    UnifestTheme {
        InterestedBoothScreen(
            uiState = InterestedBoothUiState(
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
                    BoothDetailEntity(
                        id = 3,
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
                        id = 4,
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
                        id = 5,
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
                        id = 6,
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
