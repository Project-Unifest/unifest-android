package com.unifest.android.feature.main

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import com.unifest.android.core.common.UiText
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.component.UnifestScaffold
import com.unifest.android.core.designsystem.theme.BottomMenuBar
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.feature.booth.navigation.boothNavGraph
import com.unifest.android.feature.contact.navigation.contactNavGraph
import com.unifest.android.feature.home.navigation.homeNavGraph
import com.unifest.android.feature.liked_booth.navigation.likedBoothNavGraph
import com.unifest.android.feature.map.navigation.mapNavGraph
import com.unifest.android.feature.menu.navigation.menuNavGraph
import com.unifest.android.feature.waiting.navigation.waitingNavGraph
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val SnackBarDuration = 1000L

@Composable
internal fun MainScreen(
    navigator: MainNavController = rememberMainNavController(),
) {
    val snackBarstate = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val onShowSnackBar: (message: UiText) -> Unit = { message ->
        scope.launch {
            scope.launch {
                val job = launch {
                    snackBarstate.showSnackbar(
                        message = message.asString(context),
                        duration = SnackbarDuration.Short,
                    )
                }
                delay(SnackBarDuration)
                job.cancel()
            }
        }
    }

    UnifestScaffold(
        bottomBar = {
            MainBottomBar(
                visible = navigator.shouldShowBottomBar(),
                tabs = MainTab.entries.toPersistentList(),
                currentTab = navigator.currentTab,
                onTabSelected = { navigator.navigate(it) },
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarstate)
        },
        containerColor = White,
    ) { innerPadding ->
        NavHost(
            navController = navigator.navController,
            startDestination = navigator.startDestination,
            modifier = Modifier.fillMaxSize(),
        ) {
            homeNavGraph(
                padding = innerPadding,
                onShowSnackBar = onShowSnackBar,
            )
            mapNavGraph(
                padding = innerPadding,
                navigateToBoothDetail = navigator::navigateToBoothDetail,
            )
            boothNavGraph(
                padding = innerPadding,
                navController = navigator.navController,
                popBackStack = navigator::popBackStackIfNotHome,
                navigateToBoothLocation = navigator::navigateToBoothLocation,
            )
            waitingNavGraph(
                padding = innerPadding,
            )
            menuNavGraph(
                padding = innerPadding,
                navigateToMap = navigator::popBackStackIfNotHome,
                navigateToLikedBooth = navigator::navigateToLikedBooth,
                navigateToBoothDetail = navigator::navigateToBoothDetail,
                navigateToContact = navigator::navigateToContact,
                onShowSnackBar = onShowSnackBar,
            )
            likedBoothNavGraph(
                padding = innerPadding,
                popBackStack = navigator::popBackStackIfNotHome,
                navigateToBoothDetail = navigator::navigateToBoothDetail,
                onShowSnackBar = onShowSnackBar,
            )
            contactNavGraph(
                padding = innerPadding,
                popBackStack = navigator::popBackStackIfNotHome,
            )
        }
    }
}

@Composable
private fun MainBottomBar(
    visible: Boolean,
    tabs: PersistentList<MainTab>,
    currentTab: MainTab?,
    onTabSelected: (MainTab) -> Unit,
) {
    if (visible) {
        Box(modifier = Modifier.background(White)) {
            Column {
                HorizontalDivider(color = Color(0xFFEBEBEB))
                Row(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .fillMaxWidth()
                        .height(64.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    tabs.forEach { tab ->
                        MainBottomBarItem(
                            tab = tab,
                            selected = tab == currentTab,
                            onClick = { onTabSelected(tab) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RowScope.MainBottomBarItem(
    tab: MainTab,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .selectable(
                selected = selected,
                indication = null,
                role = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = if (selected) ImageVector.vectorResource(tab.selectedIconResId)
                else ImageVector.vectorResource(tab.iconResId),
                contentDescription = tab.contentDescription,
                tint = Color.Unspecified,
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = tab.label,
                color = if (selected) Color(0xFFFD067D) else Color(0xFF555555),
                fontWeight = if (selected) FontWeight.SemiBold
                else FontWeight.Normal,
                style = BottomMenuBar,
            )
        }
    }
}

@ComponentPreview
@Composable
fun MainBottomBarPreview() {
    UnifestTheme {
        MainBottomBar(
            visible = true,
            tabs = MainTab.entries.toPersistentList(),
            currentTab = MainTab.HOME,
            onTabSelected = {},
        )
    }
}
