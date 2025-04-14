package org.sopt.at.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import org.sopt.at.presentation.history.navigateToHistory
import org.sopt.at.presentation.home.navigateToHome
import org.sopt.at.presentation.live.navigateToLive
import org.sopt.at.presentation.search.navigateToSearch
import org.sopt.at.presentation.shorts.navigateToShorts
import org.sopt.at.presentation.signin.navigation.SignIn
import org.sopt.at.presentation.signin.navigation.navigateToSignIn
import org.sopt.at.presentation.signup.navigation.navigateToSignUp

const val NAVIGATION_ROOT = 0

class MainNavigator(
    val navController: NavHostController
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val startDestination = SignIn

    val currentTab: MainTab?
        @Composable get() = MainTab.find { tab ->
            currentDestination?.hasRoute(tab::class) == true
        }

    val clearStackOptions = navOptions {
        popUpTo(0) {
            inclusive = true
        }
        launchSingleTop = true
    }

    fun navigate(tab: MainTab) {
        val navOptions = navOptions {
            navController.currentDestination?.route?.let {
                popUpTo(it) {
                    inclusive = true
                    saveState = true
                }
            }
            launchSingleTop = true
            restoreState = true
        }

        when (tab) {
            MainTab.HOME -> navController.navigateToHome(navOptions)
            MainTab.LIVE -> navController.navigateToLive(navOptions)
            MainTab.SEARCH -> navController.navigateToSearch(navOptions)
            MainTab.SHORTS -> navController.navigateToShorts(navOptions)
            MainTab.HISTORY -> navController.navigateToHistory(navOptions)
        }
    }

    @Composable
    fun shouldShowBottomBar() = MainTab.contains {
        currentDestination?.hasRoute(it::class) == true
    }

    fun navigateUp() {
        navController.navigateUp()
    }

    fun navigateToHome() {
        navController.navigateToHome(clearStackOptions)
    }

    fun navigateToSignIn() {
        navController.navigateToSignIn(clearStackOptions)
    }

    fun navigateToSignUpId() {
        navController.navigateToSignUp()
    }

    inline fun isCurrentDestination(destination: NavDestination): Boolean {
        return navController.currentDestination == destination
    }
}

@Composable
fun rememberMainNavigator(
    navController: NavHostController = rememberNavController()
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}
