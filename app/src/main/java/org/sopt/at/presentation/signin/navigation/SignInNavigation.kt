package org.sopt.at.presentation.signin.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.sopt.at.core.navigation.Route
import org.sopt.at.presentation.signin.SignInRoute

fun NavController.navigateToSignIn(
    navOptions: NavOptions? = null
) {
    navigate(SignIn, navOptions)
}

fun NavGraphBuilder.signInNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToSignUp: () -> Unit
) {
    composable<SignIn> {
        SignInRoute(
            paddingValues = paddingValues,
            navigateUp = navigateUp,
            navigateToSignUp = navigateToSignUp,
            navigateToHome = navigateToHome
        )
    }
}

@Serializable
data object SignIn : Route
