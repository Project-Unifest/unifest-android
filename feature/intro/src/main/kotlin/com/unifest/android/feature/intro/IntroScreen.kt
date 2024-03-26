package com.unifest.android.feature.intro

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.unifest.android.feature.intro.viewmodel.IntroViewModel
import com.unifest.android.core.designsystem.R

@Composable
internal fun IntroRoute(
    navigateToMain: () -> Unit,
    viewModel: IntroViewModel = hiltViewModel(),
) {
    IntroScreen()
}

@Preview(showBackground = true)
@Composable
fun IntroScreen() {
    val selectedSchools = remember { mutableStateListOf<School>() }
    var searchText by remember { mutableStateOf("") }
    //todo: 유저가 관심 축제 저장하고 가져오는 로직 추가
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            InformationText()
        }
        item {
            SearchBar(searchText = searchText, onValueChange = { searchText = it }) { query ->
                println("검색: $query")
            }
        }
        item {
            SelectedSchoolsGrid(selectedSchools)
        }
        item {
            AllSchoolsTabView(
                onSchoolSelected = { school ->
                    if (!selectedSchools.any { it.schoolName == school.schoolName }) {
                        selectedSchools.add(school)
                    }
                },
            )
        }
        //todo:버튼 추가
    }
}

data class School(
    val image: String,
    val schoolName: String,
    val festivalName: String,
    val festivalDate: String,
)

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
            text = "관심있는 학교 축제를 추가해보세요",
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
        )
        Text(
            text = "관심 학교는 언제든지 수정 가능합니다",
            fontSize = 12.sp,
            color = Color.Gray,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchText: String,
    onValueChange: (String) -> Unit,
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
                "학교를 검색해보세요",
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
        shape = RoundedCornerShape(16.dp),
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
fun SelectedSchoolsGrid(selectedSchools: MutableList<School>) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
        ) {
            Text(
                text = "나의 관심 축제",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterVertically),
            )
            TextButton(
                onClick = { selectedSchools.clear() },
                modifier = Modifier.align(Alignment.CenterVertically),
            ) {
                Text(
                    text="모두 선택 해제",
                    color = Color.Gray,
                    textDecoration = TextDecoration.Underline,
                )
            }
        }

        selectedSchools.chunked(3).forEach { rowSchools ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                rowSchools.forEach { school ->
                    SchoolItem(school = school, onSchoolSelected = { selectedSchools.remove(it) })
                }

                repeat(3 - rowSchools.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}


@Composable
fun SchoolItem(school: School, onSchoolSelected: (School) -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color.Black),
        border = BorderStroke(1.dp, Color.LightGray),
        modifier = Modifier
            .clickable { onSchoolSelected(school) }
            .padding(4.dp),
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
            //todo:coil로 이미지 넣기
            Text(school.schoolName, fontWeight = FontWeight.Bold)
            Text(school.festivalName)
            Text(school.festivalDate)
        }
    }
}

@Composable
fun AllSchoolsTabView(onSchoolSelected: (School) -> Unit) {
    val tabTitles = listOf("전체", "서울", "경기/인천", "강원", "대전/충청", "광주/전라", "부산/대구", "경상도")
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val selectedColor = Color(0xFFF5687E)
    val unselectedColor = Color.Black

    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        indicator = {},
        edgePadding = 0.dp,
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

    // 임시 데이터
    val schools = listOf(
        School("school_image_url_1", "서울대학교", "설대축제", "05.06-05.08"),
        School("school_image_url_2", "연세대학교", "연대축제", "05.06-05.08"),
        School("school_image_url_3", "고려대학교", "고대축제", "05.06-05.08"),
        School("school_image_url_4", "건국대학교", "녹색지대", "05.06-05.08"),
        School("school_image_url_5", "성균관대", "성대축제", "05.06-05.08"),

        )

    // todo:선택된 지역탭을 기반으로 필터링된 대학교 목록을 가져오게 구현
    // val filteredSchools = schools.filter { }
    // 이후 items(schools) { school -> } 에 filteredSchools를 넣어줄예정.

    Column(modifier = Modifier.padding(top = 8.dp)) {
        Text(
            text = "총 ${schools.size}개",
            modifier = Modifier
                .padding(start = 20.dp)
                .align(Alignment.Start),
        )

        val rows = (schools.size + 2) / 3
        Column {
            for (row in 0 until rows) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    for (col in 0 until 3) {
                        val index = row * 3 + col
                        if (index < schools.size) {
                            val school = schools[index]
                            SchoolItem(school = school, onSchoolSelected = onSchoolSelected)
                        } else {
                            Spacer(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}



