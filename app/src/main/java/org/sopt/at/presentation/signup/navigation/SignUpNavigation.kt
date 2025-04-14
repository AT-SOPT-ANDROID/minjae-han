package org.sopt.at.presentation.signup.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.sopt.at.core.navigation.Route
import org.sopt.at.presentation.signup.SignUpIdRoute
import org.sopt.at.presentation.signup.SignUpPasswordRoute
import org.sopt.at.presentation.signup.SignUpScreen
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
        SignUpScreen(
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
    composable<SignUpId> {
        SignUpIdRoute(
            viewModel = viewModel,
            paddingValues = paddingValues,
            onBackClick = navigateUp,
            onNextClick = { navController.navigate(SignUpPassword) }
        )
    }
    composable<SignUpPassword> {
        SignUpPasswordRoute(
            viewModel = viewModel,
            paddingValues = paddingValues,
            onBackClick = navigateUp,
            onNextClick = navigateToSignIn
        )
    }
}

@Serializable
data object SignUp : Route
