package org.sopt.at.presentation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import org.sopt.at.core.navigation.MainTabRoute
import org.sopt.at.core.navigation.Route
import org.sopt.at.presentation.history.History
import org.sopt.at.presentation.home.navigation.Home
import org.sopt.at.presentation.live.Live
import org.sopt.at.presentation.main.MainTab.entries
import org.sopt.at.presentation.search.Search
import org.sopt.at.presentation.shorts.Shorts

enum class MainTab(
    val selectedIconResource: ImageVector,
    val unselectedIconResource: ImageVector,
    val label: String,
    val route: MainTabRoute
) {
    HOME(
        selectedIconResource = Icons.Outlined.Home,
        unselectedIconResource = Icons.Filled.Home,
        label = "HOME",
        route = Home
    ),
    SHORTS(
        selectedIconResource = Icons.Outlined.PlayArrow,
        unselectedIconResource = Icons.Filled.PlayArrow,
        label = "Shorts",
        route = Shorts
    ),
    LIVE(
        selectedIconResource = Icons.Outlined.ThumbUp,
        unselectedIconResource = Icons.Filled.ThumbUp,
        label = "LIVE",
        route = Live
    ),
    SEARCH(
        selectedIconResource = Icons.Outlined.Search,
        unselectedIconResource = Icons.Filled.Search,
        label = "SEARCH",
        route = Search
    ),
    HISTORY(
        selectedIconResource = Icons.Outlined.Refresh,
        unselectedIconResource = Icons.Filled.Refresh,
        label = "HISTORY",
        route = History
    )
    ;

    companion object {
        @Composable
        fun find(predicate: @Composable (MainTabRoute) -> Boolean): MainTab? {
            return entries.find { predicate(it.route) }
        }

        @Composable
        fun contains(predicate: @Composable (Route) -> Boolean): Boolean {
            return entries.map { it.route }.any { predicate(it) }
        }
    }
}
