package com.unifest.android.feature.intro

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.unifest.android.core.designsystem.component.SearchTextField
import com.unifest.android.core.designsystem.component.UnifestButton
import com.unifest.android.core.designsystem.theme.BoothLocation
import com.unifest.android.core.designsystem.theme.Content1
import com.unifest.android.core.designsystem.theme.Content6
import com.unifest.android.core.designsystem.theme.Title2
import com.unifest.android.core.designsystem.theme.Title3
import com.unifest.android.core.designsystem.theme.Title4
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.domain.entity.Festival
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
        navigateToMain = navigateToMain,
        updateSearchText = viewModel::updateSearchText,
        initSearchText = viewModel::initSearchText,
    )
}

@Composable
fun IntroScreen(
    uiState: IntroUiState,
    navigateToMain: () -> Unit,
    updateSearchText: (TextFieldValue) -> Unit,
    initSearchText: () -> Unit,
) {
    val selectedFestivals = remember { mutableStateListOf<Festival>() }

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
            InterestedFestivalsRow(
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
            onClick = navigateToMain,
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
fun InterestedFestivalsRow(
    selectedFestivals: MutableList<Festival>,
    onFestivalSelected: (Festival) -> Unit,
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
                text = stringResource(id = R.string.intro_interested_festivals_title),
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
                .height(if (selectedFestivals.isEmpty()) 0.dp else 140.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                count = selectedFestivals.size,
                key = { index -> selectedFestivals[index].schoolName },
            ) { index ->
                FestivalItem(
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
fun AllSchoolsTabView(
    schools: ImmutableList<Festival>,
    onSchoolSelected: (Festival) -> Unit,
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

@OptIn(ExperimentalFoundationApi::class)
@DevicePreview
@Composable
fun PreviewIntroScreen() {
    UnifestTheme {
        IntroScreen(
            uiState = IntroUiState(
                schools = persistentListOf(
                    Festival("https://picsum.photos/36", "서울대학교", "설대축제", "05.06-05.08"),
                    Festival("https://picsum.photos/36", "연세대학교", "연대축제", "05.06-05.08"),
                    Festival("https://picsum.photos/36", "고려대학교", "고대축제", "05.06-05.08"),
                    Festival("https://picsum.photos/36", "건국대학교", "녹색지대", "05.06-05.08"),
                    Festival("https://picsum.photos/36", "성균관대", "성대축제", "05.06-05.08"),
                ),
            ),
            navigateToMain = {},
            updateSearchText = {},
            initSearchText = {},
        )
    }
}
