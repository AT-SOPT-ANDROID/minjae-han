package org.sopt.at.presentation.history

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.sopt.at.core.navigation.MainTabRoute

fun NavController.navigateToHistory(
    navOptions: NavOptions? = null
) {
    navigate(History, navOptions)
}

fun NavGraphBuilder.HistoryNavGraph() {
    composable<History> {
        HistoryRoute()
    }
}

@Serializable
data object History : MainTabRoute
