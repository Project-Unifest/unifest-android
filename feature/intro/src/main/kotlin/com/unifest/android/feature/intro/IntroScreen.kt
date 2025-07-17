package com.unifest.android.feature.intro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unifest.android.core.common.ObserveAsEvents
import com.unifest.android.core.designsystem.component.LoadingWheel
import com.unifest.android.core.designsystem.component.NetworkErrorDialog
import com.unifest.android.core.designsystem.component.SearchTextField
import com.unifest.android.core.designsystem.component.ServerErrorDialog
import com.unifest.android.core.designsystem.component.UnifestButton
import com.unifest.android.core.designsystem.component.UnifestScaffold
import com.unifest.android.core.designsystem.theme.BoothLocation
import com.unifest.android.core.designsystem.theme.Title2
import com.unifest.android.core.designsystem.theme.Title4
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.ui.DevicePreview
import com.unifest.android.feature.intro.component.AllFestivalsTabRow
import com.unifest.android.feature.intro.component.LikedFestivalsRow
import com.unifest.android.feature.intro.preview.IntroPreviewParameterProvider
import com.unifest.android.feature.intro.viewmodel.ErrorType
import com.unifest.android.feature.intro.viewmodel.IntroUiAction
import com.unifest.android.feature.intro.viewmodel.IntroUiEvent
import com.unifest.android.feature.intro.viewmodel.IntroUiState
import com.unifest.android.feature.intro.viewmodel.IntroViewModel
import kotlinx.collections.immutable.persistentListOf
import com.unifest.android.core.designsystem.R as designR

@Composable
internal fun IntroRoute(
    navigateToMain: () -> Unit,
    viewModel: IntroViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ObserveAsEvents(flow = viewModel.uiEvent) { event ->
        when (event) {
            is IntroUiEvent.NavigateToMain -> {
                navigateToMain()
            }
        }
    }

    IntroScreen(
        uiState = uiState,
        onAction = viewModel::onAction,
    )
}

@Composable
internal fun IntroScreen(
    uiState: IntroUiState,
    onAction: (IntroUiAction) -> Unit,
) {
    UnifestScaffold(
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->
        IntroContent(
            uiState = uiState,
            onAction = onAction,
            innerPadding = innerPadding,
        )

        if (uiState.isLoading) {
            LoadingWheel(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
            )
        }

        if (uiState.isServerErrorDialogVisible) {
            ServerErrorDialog(
                onRetryClick = { onAction(IntroUiAction.OnRetryClick(ErrorType.SERVER)) },
            )
        }

        if (uiState.isNetworkErrorDialogVisible) {
            NetworkErrorDialog(
                onRetryClick = { onAction(IntroUiAction.OnRetryClick(ErrorType.NETWORK)) },
            )
        }
    }
}

@Composable
internal fun IntroContent(
    uiState: IntroUiState,
    onAction: (IntroUiAction) -> Unit,
    innerPadding: PaddingValues,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 60.dp),
        ) {
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
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = stringResource(id = R.string.intro_info_description),
                    style = BoothLocation,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            SearchTextField(
                searchTextState = uiState.searchTextState,
                // updateSearchText = { text -> onAction(IntroUiAction.OnSearchTextUpdated(text)) },
                searchTextHintRes = designR.string.search_text_hint,
                onSearch = {
                    onAction(IntroUiAction.OnSearch(it))
                },
                clearSearchText = { onAction(IntroUiAction.OnSearchTextCleared) },
                modifier = Modifier
                    .height(46.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
            )
            Spacer(modifier = Modifier.height(18.dp))
            LikedFestivalsRow(
                selectedFestivals = uiState.selectedFestivals,
                onAction = onAction,
            )
            if (uiState.selectedFestivals.isNotEmpty()) {
                Spacer(modifier = Modifier.height(21.dp))
                HorizontalDivider(
                    thickness = 8.dp,
                    color = MaterialTheme.colorScheme.outline,
                )
            }
            AllFestivalsTabRow(
                festivals = uiState.festivals,
                isSearchLoading = uiState.isSearchLoading,
                onAction = onAction,
                selectedFestivals = uiState.selectedFestivals,
                modifier = Modifier.weight(1f),
            )
        }
        UnifestButton(
            onClick = { onAction(IntroUiAction.OnAddCompleteClick) },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            contentPadding = PaddingValues(vertical = 17.dp),
            enabled = uiState.selectedFestivals.isNotEmpty(),
        ) {
            Text(
                text = stringResource(id = R.string.intro_add_complete),
                style = Title4,
                fontSize = 14.sp,
            )
        }
    }
}

@DevicePreview
@Composable
private fun IntroScreenPreview(
    @PreviewParameter(IntroPreviewParameterProvider::class)
    introUiState: IntroUiState,
) {
    UnifestTheme {
        IntroScreen(
            uiState = introUiState,
            onAction = {},
        )
    }
}

@DevicePreview
@Composable
private fun IntroScreenEmptyPreview() {
    UnifestTheme {
        IntroScreen(
            uiState = IntroUiState(festivals = persistentListOf()),
            onAction = {},
        )
    }
}
