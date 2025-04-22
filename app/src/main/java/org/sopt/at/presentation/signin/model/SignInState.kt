package org.sopt.at.presentation.signin.model

data class SignInState(
    val userId: String = "",
    val password: String = "",
    val isInputValid: Boolean = false,
    val isSignInSuccessful: Boolean = false,
    val isLoading: Boolean = false
)
