package org.sopt.at.presentation.signup

data class SignUpState(
    val userId: String = "",
    val password: String = "",
    val isSignUpComplete: Boolean = false
)
