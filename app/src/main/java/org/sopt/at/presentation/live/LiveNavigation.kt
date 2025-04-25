package org.sopt.at.presentation.live

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.sopt.at.core.navigation.MainTabRoute

fun NavController.navigateToLive(
    navOptions: NavOptions? = null
) {
    navigate(Live, navOptions)
}

fun NavGraphBuilder.liveNavGraph() {
    composable<Live> {
        LiveRoute()
    }
}

@Serializable
data object Live : MainTabRoute
