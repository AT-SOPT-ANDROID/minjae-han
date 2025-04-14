package org.sopt.at.presentation.search

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.sopt.at.core.navigation.MainTabRoute

fun NavController.navigateToSearch(
    navOptions: NavOptions? = null
) {
    navigate(Search, navOptions)
}

fun NavGraphBuilder.SearchNavGraph() {
    composable<Search> {
        SearchRoute()
    }
}

@Serializable
data object Search : MainTabRoute
