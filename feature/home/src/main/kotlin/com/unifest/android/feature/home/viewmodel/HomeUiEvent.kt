package com.unifest.android.feature.home.viewmodel

import com.unifest.android.core.common.UiText

sealed interface HomeUiEvent {
    data class ShowSnackBar(val message: UiText) : HomeUiEvent
    data class NavigateToCardNews(val imgUrl: String) : HomeUiEvent
}
