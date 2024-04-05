package com.unifest.android.feature.booth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.component.UnifestOutlinedButton
import com.unifest.android.core.designsystem.theme.BoothCaution
import com.unifest.android.core.designsystem.theme.BoothTitle1
import com.unifest.android.core.designsystem.theme.Content2
import com.unifest.android.core.designsystem.theme.MenuPrice
import com.unifest.android.core.designsystem.theme.MenuTitle
import com.unifest.android.core.designsystem.theme.Title2
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.ui.DevicePreview

@Composable
internal fun BoothRoute(
    padding: PaddingValues,
) {
    BoothScreen(padding = padding)
}

@Composable
@Suppress("unused")
fun BoothScreen(
    padding: PaddingValues,
) {
    LazyColumn {
        item { BoothImage() }
        item { Spacer(modifier = Modifier.height(30.dp)) }
        item { BoothDescription() }
        item { Spacer(modifier = Modifier.height(20.dp)) }
        item { MenuText() }
        item { Spacer(modifier = Modifier.height(8.dp)) }
        items(boothMenus) { menu ->
            MenuItem(menu = menu)
        }
    }
}

@Composable
fun BoothImage() {
    Image(
        painter = painterResource(id = com.unifest.android.core.designsystem.R.drawable.booth_image_example),
        modifier = Modifier
            .height(237.dp)
            .fillMaxWidth()
            .width(394.dp)
            .fillMaxWidth(),
        contentDescription = "Booth",
    )
}

@Composable
fun BoothDescription() {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "컴공 주점",
                style = BoothTitle1,
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "컴퓨터공학부 전용 부스",
                style = BoothCaution,
                color = Color(0xFFF5687E),
                modifier = Modifier.align(Alignment.Bottom),
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "저희 주점은 일본 이자카야를 모티브로 만든 컴공인을 위한 주점입니다. 100번째 방문자에게 깜짝 선물 증정 이벤트를 하고 있으니 많은 관심 부탁드려요~!",
            modifier = Modifier.padding(top = 8.dp),
            style = Content2.copy(lineHeight = 18.sp),
            // todo: 줄간격 논의
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 8.dp),
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_location),
                contentDescription = "location icon",
                tint = Color(0xFF1FC0BA),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "청심대 앞",
                style = Content2,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        UnifestOutlinedButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "위치 확인하기")
        }
    }
}

@Composable
fun MenuText() {
    Text(
        text = "메뉴",
        modifier = Modifier.padding(start = 20.dp),
        style = Title2,
    )
}

@Composable
fun MenuItem(menu: BoothMenu) {
    Row(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
    ) {
        Image(
            painter = painterResource(id = menu.imageResource),
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

data class BoothMenu(val name: String, val price: Int, val imageResource: Int)

val boothMenus = listOf(
    BoothMenu("모둠 사시미", 45000, com.unifest.android.core.designsystem.R.drawable.booth_menu_image_example),
    BoothMenu("모둠 사시미", 45000, com.unifest.android.core.designsystem.R.drawable.booth_menu_image_example),
    BoothMenu("모둠 사시미", 45000, com.unifest.android.core.designsystem.R.drawable.booth_menu_image_example),
    BoothMenu("모둠 사시미", 45000, com.unifest.android.core.designsystem.R.drawable.booth_menu_image_example),
)

@DevicePreview
@Composable
fun BoothScreenPreview() {
    UnifestTheme {
        BoothScreen(padding = PaddingValues(0.dp))
    }
}
