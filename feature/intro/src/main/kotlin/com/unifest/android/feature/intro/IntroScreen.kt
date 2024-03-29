package com.unifest.android.feature.intro

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.domain.entity.School
import com.unifest.android.core.ui.DevicePreview
import com.unifest.android.feature.intro.viewmodel.IntroViewModel

@Composable
internal fun IntroRoute(
    navigateToMain: () -> Unit,
    @Suppress("unused")
    viewModel: IntroViewModel = hiltViewModel(),
) {
    IntroScreen(navigateToMain)
}
@Composable
fun IntroScreen(navigateToMain: () -> Unit) {
    val selectedSchools = remember { mutableStateListOf<School>() }
    var searchText by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 80.dp) // 추가 완료 버튼에게 공간 주기
        ) {
            InformationText()
            SearchBar(
                searchText = searchText,
                onValueChange = { searchText = it },
                onSearch = { query -> println("검색: $query") }
            )
            SelectedSchoolsGrid(
                selectedSchools = selectedSchools,
                onSchoolSelected = { school ->
                    selectedSchools.remove(school)
                }
            )
            AllSchoolsTabView(
                onSchoolSelected = { school ->
                    if (!selectedSchools.any { it.schoolName == school.schoolName }) {
                        selectedSchools.add(school)
                    }
                }
            )
        }

        Button(
            onClick = navigateToMain,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            shape = RoundedCornerShape(16),
            colors = ButtonDefaults.buttonColors(Color(0xFFF5687E)),
            contentPadding = PaddingValues(vertical = 12.dp),
        ) {
            Text(
                text = stringResource(id = R.string.intro_add_complete),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.White,
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
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
        )
        Text(
            text = stringResource(id = R.string.intro_info_description),
            fontSize = 12.sp,
            color = Color.Gray,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    // 학교 검색
    searchText: String,
    onValueChange: (String) -> Unit,
    @Suppress("unused")
    onSearch: (String) -> Unit,
) {
    var text by remember { mutableStateOf(searchText) }
    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onValueChange(it)
        },
        placeholder = {
            Text(
                text = stringResource(id = R.string.intro_search_bar_hint),
                color = Color.Gray,
                fontSize = 13.sp,
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 16.dp, start = 20.dp, end = 20.dp),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            unfocusedIndicatorColor = Color.Gray,
            focusedIndicatorColor = Color.Gray,
        ),
        shape = RoundedCornerShape(67.dp),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Color.Gray,
                modifier = Modifier.size(30.dp),
            )
        },
    )
}

@Composable
fun SelectedSchoolsGrid(
    selectedSchools: MutableList<School>,
    onSchoolSelected: (School) -> Unit
) {
    // 나의 관심 축제
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
        ) {
            Text(
                text = stringResource(id = R.string.intro_interested_festivals_title),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterVertically),
            )
            TextButton(
                onClick = { selectedSchools.clear() },
                modifier = Modifier.align(Alignment.CenterVertically),
            ) {
                Text(
                    text = stringResource(id = R.string.intro_clear_item_button_text),
                    color = Color.Gray,
                    textDecoration = TextDecoration.Underline,
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .padding(8.dp)
                .height(
                    when {
                        selectedSchools.isEmpty() -> 0.dp
                        else -> {
                            val rows = ((selectedSchools.size - 1) / 3 + 1) * 180
                            rows.dp
                        }
                    }
                ),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {

            items(selectedSchools.size) { index ->
                val school = selectedSchools[index]
                SchoolItem(school = school, onSchoolSelected = {
                    onSchoolSelected(it)
                })
            }

        }



    }
}

@Composable
fun SchoolItem(
    school: School,
    onSchoolSelected: (School) -> Unit,
) {
    // 그리드에 들어갈 각 학교별 아이템
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color.Black),
        border = BorderStroke(1.dp, Color.LightGray),
        modifier = Modifier
            .clickable { onSchoolSelected(school) },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_map),
                contentDescription = null,
                modifier = Modifier.size(50.dp),
            )
            // todo:coil로 이미지 넣기
            Text(school.schoolName, fontWeight = FontWeight.Bold)
            Text(school.festivalName)
            Text(school.festivalDate)
        }
    }
}

@Composable
fun AllSchoolsTabView(onSchoolSelected: (School) -> Unit) {
    // 전체 학교 그리드뷰
    val tabTitles = LocalContext.current.resources.getStringArray(R.array.region_tab_titles).toList()
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val selectedColor = Color(0xFFF5687E)
    val unselectedColor = Color.Black

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
                        title,
                        color = if (selectedTabIndex == index) selectedColor else unselectedColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
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
        )
        // 총 학교수

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .height(
                    when {
                        schools.isEmpty() -> 0.dp
                        else -> {
                            val rows = ((schools.size - 1) / 3 + 1) * 180
                            rows.dp
                        }
                    }
                ),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(schools.size) { index ->

                val school = schools[index]
                SchoolItem(school = school, onSchoolSelected = onSchoolSelected)
            }
        }
    }
}

// 임시 데이터
val schools = listOf(
    School("school_image_url_1", "서울대학교", "설대축제", "05.06-05.08"),
    School("school_image_url_2", "연세대학교", "연대축제", "05.06-05.08"),
    School("school_image_url_3", "고려대학교", "고대축제", "05.06-05.08"),
    School("school_image_url_4", "건국대학교", "녹색지대", "05.06-05.08"),
    School("school_image_url_5", "성균관대", "성대축제", "05.06-05.08"),
)

@DevicePreview
@Composable
fun PreviewIntroScreen() {
    IntroScreen(navigateToMain = {})
}
