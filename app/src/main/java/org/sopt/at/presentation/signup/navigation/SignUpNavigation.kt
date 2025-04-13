package org.sopt.at.presentation.signup.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.sopt.at.core.navigation.Route
import org.sopt.at.presentation.signup.SignUpIdRoute
import org.sopt.at.presentation.signup.SignUpPasswordRoute

fun NavController.navigateToSignUpId(
    navOptions: NavOptions? = null
) {
    navigate(SignUpId, navOptions)
}

fun NavGraphBuilder.signUpIdNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToSignUpPassword: () -> Unit
) {
    composable<SignUpId> {
        SignUpIdRoute(
            paddingValues = paddingValues,
            onBackClick = navigateUp,
            onNextClick = navigateToSignUpPassword
        )
    }
}

fun NavController.navigateToSignUpPassword(
    navOptions: NavOptions? = null
) {
    navigate(SignUpPassword, navOptions)
}

fun NavGraphBuilder.signUpPasswordNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToSignIn: () -> Unit
) {
    composable<SignUpPassword> {
        SignUpPasswordRoute(
            paddingValues = paddingValues,
            onBackClick = navigateUp,
            onNextClick = navigateToSignIn
        )
    }
}

@Serializable
data object SignUpId : Route

@Serializable
data object SignUpPassword : Route
