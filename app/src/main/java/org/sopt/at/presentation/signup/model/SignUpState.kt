package org.sopt.at.presentation.signup.model

data class SignUpState(
    val userId: String = "",
    val password: String = "",
    val nickname: String = "",
    val isSignUpComplete: Boolean = false
)
