package com.unifest.android.feature.intro

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun IntroScreen() {
    val selectedSchools = remember { mutableStateListOf<School>() }
    //todo: 유저가 관심 축제 저장하고 가져오는 로직 추가
    Column {
        InformationText()
        SearchBar()
        SelectedSchoolsGrid(selectedSchools = selectedSchools)
        AllSchoolsTabView(onSchoolSelected = { school ->
            if (!selectedSchools.any { it.schoolName == school.schoolName }) {
                selectedSchools.add(school)
            }
        })
    }
}

data class School(
    val image: String,
    val schoolName: String,
    val festivalName: String,
    val festivalDate: String
)

@Composable
fun InformationText() {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 64.dp, bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        Text(
            text = "관심있는 학교 축제를 추가해보세요",
            fontWeight = FontWeight.Bold
        )
        Text(text = "관심 학교는 언제든지 수정 가능합니다")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    TextField(
        value = "",
        onValueChange = {},
        placeholder = { Text("학교를 검색해보세요") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 16.dp, start = 20.dp, end = 20.dp),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            unfocusedIndicatorColor = Color.LightGray,
            focusedIndicatorColor = Color.LightGray
        ),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Color.Gray
            )
        }
    )
}

@Composable
fun SelectedSchoolsGrid(selectedSchools: List<School>) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("나의 관심 축제")
            Button(onClick = { selectedSchools.clear() }, colors = ButtonDefaults.buttonColors(Color.Transparent)) {
                Text("모두 선택 해제", color = Color.Black)
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(selectedSchools) { school ->
                SchoolItem(school = school, onSchoolSelected = { selectedSchools.remove(school) })
            }
        }
    }
}

private fun <E> List<E>.clear() {
    this.clear()
}

private fun <E> List<E>.remove(school: E) {
    this.remove(school)
}

@Composable
fun SchoolItem(
    school: School,
    onSchoolSelected: (School) -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color.Black),
        border = BorderStroke(1.dp, Color.LightGray),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSchoolSelected(school) }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(20.dp)
        ) {
            Image(
                painter = painterResource(id = com.nexters.ilab.android.core.designsystem.R.drawable.ic_waiting),
                //todo: coil로 학교 마크추가
                contentDescription = "School Mark",
                modifier = Modifier.size(80.dp)
            )
            Text(school.schoolName)
            Text(school.festivalName, fontWeight = FontWeight.Bold)
            Text(school.festivalDate, color = Color.Gray)
        }
    }
}


@Composable
fun AllSchoolsTabView(onSchoolSelected: (School) -> Unit) {
    val tabTitles = listOf("전체", "서울", "경기/인천", "강원", "대전/충청","광주/전라","부산/대구" ,"경상도")
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val selectedColor = Color(0xFFF5687E) // 선택된 탭의 글자 색
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
                        color = if (selectedTabIndex == index) selectedColor else unselectedColor
                    )
                }
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

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(schools) { school ->
            SchoolItem(school = school, onSchoolSelected = onSchoolSelected)
        }

    }
}



