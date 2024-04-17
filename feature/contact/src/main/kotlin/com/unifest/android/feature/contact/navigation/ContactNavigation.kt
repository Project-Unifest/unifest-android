package com.unifest.android.feature.contact.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.unifest.android.feature.ContactRoute

const val CONTACT_ROUTE = "contact_route"

fun NavController.navigateToContact() {
    navigate(CONTACT_ROUTE)
}

fun NavGraphBuilder.contactNavGraph(
    padding: PaddingValues,
    onBackClick: () -> Unit,
) {
    composable(
        route = CONTACT_ROUTE,
    ) {
        ContactRoute(
            padding = padding,
            onCloseClick = onBackClick,
        )
    }
}
