package com.unifest.android.core.common.utils

import com.unifest.android.core.model.FestivalModel

fun matchesSearchText(festival: FestivalModel, searchText: String): Boolean {
    return festival.schoolName.replace(" ", "").contains(searchText.replace(" ", ""), ignoreCase = true) ||
        festival.festivalName.replace(" ", "").contains(searchText.replace(" ", ""), ignoreCase = true)
}
