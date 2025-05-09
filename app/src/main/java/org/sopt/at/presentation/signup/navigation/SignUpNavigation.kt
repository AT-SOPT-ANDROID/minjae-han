package org.sopt.at.presentation.signup.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.sopt.at.core.navigation.Route
import org.sopt.at.presentation.signup.MainSignUpScreen
import org.sopt.at.presentation.signup.SignUpIdRoute
import org.sopt.at.presentation.signup.SignUpNicknameRoute
import org.sopt.at.presentation.signup.SignUpPasswordRoute
import org.sopt.at.presentation.signup.SignUpViewModel

fun NavController.navigateToSignUp(
    navOptions: NavOptions? = null
) {
    navigate(SignUp, navOptions)
}

fun NavGraphBuilder.signUpNavGraph(
    paddingValues: PaddingValues,
    navigateToSingIn: () -> Unit,
    navigateUp: () -> Unit
) {
    composable<SignUp> {
        MainSignUpScreen(
            paddingValues = paddingValues,
            navigateToSingIn = navigateToSingIn,
            navigateUp = navigateUp
        )
    }
}

fun NavGraphBuilder.signUpGraph(
    paddingValues: PaddingValues,
    navController: NavHostController,
    viewModel: SignUpViewModel,
    navigateUp: () -> Unit,
    navigateToSignIn: () -> Unit
) {
    composable<SignUpId>(
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        }
    ) {
        SignUpIdRoute(
            viewModel = viewModel,
            paddingValues = paddingValues,
            onBackClick = navigateUp,
            onNextClick = { navController.navigate(SignUpPassword) }
        )
    }
    composable<SignUpPassword>(
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        }
    ) {
        SignUpPasswordRoute(
            viewModel = viewModel,
            paddingValues = paddingValues,
            onBackClick = navigateUp,
            onNextClick = { navController.navigate(SignUpNickname) }
        )
    }
    composable<SignUpNickname>(
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        }
    ) {
        SignUpNicknameRoute(
            viewModel = viewModel,
            paddingValues = paddingValues,
            onBackClick = navigateUp,
            onNextClick = navigateToSignIn
        )
    }
}

@Serializable
data object SignUp : Route
