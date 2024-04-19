package com.unifest.android.feature.intro

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.component.NetworkImage
import com.unifest.android.core.designsystem.component.SearchTextField
import com.unifest.android.core.designsystem.component.UnifestButton
import com.unifest.android.core.designsystem.theme.BoothLocation
import com.unifest.android.core.designsystem.theme.Content1
import com.unifest.android.core.designsystem.theme.Content2
import com.unifest.android.core.designsystem.theme.Content3
import com.unifest.android.core.designsystem.theme.Content4
import com.unifest.android.core.designsystem.theme.Content6
import com.unifest.android.core.designsystem.theme.Title2
import com.unifest.android.core.designsystem.theme.Title3
import com.unifest.android.core.designsystem.theme.Title4
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.core.ui.DevicePreview
import com.unifest.android.core.ui.component.FestivalItem
import com.unifest.android.feature.intro.viewmodel.IntroUiState
import com.unifest.android.feature.intro.viewmodel.IntroViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import timber.log.Timber

@Composable
internal fun IntroRoute(
    navigateToMain: () -> Unit,
    viewModel: IntroViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    IntroScreen(
        uiState = uiState,
        updateSearchText = viewModel::updateSearchText,
        initSearchText = viewModel::initSearchText,
        onAddLikeFestivalClick = { festivals -> viewModel.addLikeFestivals(festivals, navigateToMain) },
    )
}

@Composable
fun IntroScreen(
    uiState: IntroUiState,
    updateSearchText: (TextFieldValue) -> Unit,
    initSearchText: () -> Unit,
    onAddLikeFestivalClick: (List<FestivalModel>) -> Unit,
) {
    val selectedFestivals = remember { mutableStateListOf<FestivalModel>() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 80.dp), // 추가 완료 버튼에게 공간 주기
        ) {
            InformationText()
            SearchTextField(
                searchText = uiState.searchText,
                updateSearchText = updateSearchText,
                searchTextHintRes = R.string.intro_search_text_hint,
                onSearch = { query -> Timber.d("검색: $query") },
                initSearchText = initSearchText,
                modifier = Modifier
                    .height(46.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
            )
            Spacer(modifier = Modifier.height(18.dp))
            LikedFestivalsRow(
                selectedFestivals = selectedFestivals,
                onFestivalSelected = { festival ->
                    selectedFestivals.remove(festival)
                },
            )
            AllSchoolsTabView(
                schools = uiState.schools,
                onSchoolSelected = { school ->
                    if (!selectedFestivals.any { it.schoolName == school.schoolName }) {
                        selectedFestivals.add(school)
                    }
                },
            )
        }
        UnifestButton(
            onClick = {
                onAddLikeFestivalClick(selectedFestivals)
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            contentPadding = PaddingValues(vertical = 17.dp),
        ) {
            Text(
                text = stringResource(id = R.string.intro_add_complete),
                style = Title4,
                fontSize = 14.sp,
            )
        }
    }
}

@Composable
fun InformationText() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 64.dp, bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = stringResource(id = R.string.intro_info_title),
            style = Title2,
            color = Color.Black,
        )
        Text(
            text = stringResource(id = R.string.intro_info_description),
            style = BoothLocation,
            fontSize = 12.sp,
            color = Color(0xFF848484),
        )
    }
}

@Composable
fun LikedFestivalsRow(
    selectedFestivals: MutableList<FestivalModel>,
    onFestivalSelected: (FestivalModel) -> Unit,
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        ) {
            Text(
                text = stringResource(id = R.string.intro_liked_festivals_title),
                style = Title3,
            )
            TextButton(
                onClick = { selectedFestivals.clear() },
            ) {
                Text(
                    text = stringResource(id = R.string.intro_clear_item_button_text),
                    color = Color(0xFF848484),
                    textDecoration = TextDecoration.Underline,
                    style = Content6,
                )
            }
        }
        LazyRow(
            modifier = Modifier
                .padding(8.dp)
                .height(if (selectedFestivals.isEmpty()) 0.dp else 130.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                count = selectedFestivals.size,
                key = { index -> selectedFestivals[index].festivalId },
            ) { index ->
                FestivalRowItem(
                    festival = selectedFestivals[index],
                    onFestivalSelected = {
                        onFestivalSelected(it)
                    },
                )
            }
        }
    }
}

@Composable
fun FestivalRowItem(
    festival: FestivalModel,
    onFestivalSelected: (FestivalModel) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color.Black),
        border = BorderStroke(1.dp, Color(0xFFD9D9D9)),
        modifier = Modifier
            .height(130.dp)
            .width(120.dp),
    ) {
        Box(
            modifier = Modifier.clickable { onFestivalSelected(festival) },
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                NetworkImage(
                    imageUrl = festival.thumbnail,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = festival.schoolName,
                    color = Color.Black,
                    style = Content2,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = festival.festivalName,
                    color = Color.Black,
                    style = Content4,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    festival.beginDate + " - " + festival.endDate,
                    color = Color(0xFF979797),
                    style = Content3,
                )
            }
        }
    }
}

@Composable
fun AllSchoolsTabView(
    schools: ImmutableList<FestivalModel>,
    onSchoolSelected: (FestivalModel) -> Unit,
) {
    val tabTitles = LocalContext.current.resources.getStringArray(R.array.region_tab_titles).toList()
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val selectedColor = Color(0xFFF5687E)
    val unselectedColor = Color.Black

    // TODO tab 간의 간격을 더 좁게 해야 함
    ScrollableTabRow(
        // 지역 탭
        selectedTabIndex = selectedTabIndex,
        containerColor = Color.White,
        edgePadding = 0.dp,
        indicator = {},
    ) {
        tabTitles.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { selectedTabIndex = index },
                text = {
                    Text(
                        text = title,
                        color = if (selectedTabIndex == index) selectedColor else unselectedColor,
                        style = Content1,
                        fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                    )
                },
            )
        }
    }

    Column(modifier = Modifier.padding(top = 8.dp)) {
        Text(
            text = "총 ${schools.size}개",
            modifier = Modifier
                .padding(start = 20.dp, bottom = 16.dp)
                .align(Alignment.Start),
            color = Color(0xFF4C4C4C),
            style = BoothLocation,
            fontSize = 12.sp,
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .height(if (schools.isEmpty()) 0.dp else (((schools.size - 1) / 3 + 1) * 140).dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(schools.size) { index ->
                FestivalItem(
                    festival = schools[index],
                    onFestivalSelected = onSchoolSelected,
                )
            }
        }
    }
}

@DevicePreview
@Composable
fun PreviewIntroScreen() {
    UnifestTheme {
        IntroScreen(
            uiState = IntroUiState(
                schools = persistentListOf(
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
            ),
            updateSearchText = {},
            initSearchText = {},
            onAddLikeFestivalClick = {},
        )
    }
}
