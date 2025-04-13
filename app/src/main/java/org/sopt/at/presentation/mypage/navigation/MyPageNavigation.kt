package org.sopt.at.presentation.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.sopt.at.core.navigation.MainTabRoute
import org.sopt.at.core.navigation.Route
import org.sopt.at.presentation.mypage.MyPageRoute

fun NavController.navigateToMyPage(
    navOptions: NavOptions? = null
) {
    navigate(MyPage, navOptions)
}

fun NavGraphBuilder.myPageNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToNotification: () -> Unit,
    navigateToSignIn: () -> Unit,
    navigateToSetting: () -> Unit
) {
    composable<MyPage> {
        MyPageRoute(
            paddingValues = paddingValues,
            onBackClick = navigateUp,
            onNotificationClick = navigateToNotification,
            onSettingClick = navigateToSetting,
            onSignOutClick = navigateToSignIn
        )
    }
}

@Serializable
data object MyPage : MainTabRoute
