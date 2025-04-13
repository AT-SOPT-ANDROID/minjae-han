package org.sopt.at.presentation.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.sopt.at.core.designsystem.event.LocalSnackBarTrigger
import org.sopt.at.core.util.extension.imePadding
import org.sopt.at.presentation.signup.navigation.SignUpId
import org.sopt.at.presentation.signup.navigation.signUpGraph

@Composable
fun SignUpScreen(
    paddingValues: PaddingValues,
    navigateToSingIn: () -> Unit,
    navigateUp: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val lifecycleOwner = LocalLifecycleOwner.current
    val snackbarTrigger = LocalSnackBarTrigger.current

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle).collect { effect ->
            when (effect) {
                is SignUpSideEffect.ShowSnackbar -> {
                    snackbarTrigger(effect.message)
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(paddingValues)
    ) {
        NavHost(
            navController = navController,
            startDestination = SignUpId,
            modifier = Modifier.weight(1f)
        ) {
            signUpGraph(
                navController = navController,
                viewModel = viewModel,
                navigateUp = navigateUp,
                paddingValues = paddingValues,
                navigateToSignIn = navigateToSingIn
            )
        }
    }
}
