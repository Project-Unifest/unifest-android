package com.unifest.android.core.common.utils

import androidx.compose.ui.text.input.TextFieldValue
import com.unifest.android.core.model.FestivalModel

fun matchesSearchText(festival: FestivalModel, searchText: TextFieldValue): Boolean {
    return festival.schoolName.replace(" ", "").contains(searchText.text.replace(" ", ""), ignoreCase = true) ||
        festival.festivalName.replace(" ", "").contains(searchText.text.replace(" ", ""), ignoreCase = true)
}
