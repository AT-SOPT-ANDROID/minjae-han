package org.sopt.at.presentation.signup.model

data class SignUpState(
    val nickname: String = "",
    val userId: String = "",
    val password: String = "",
    val isSignUpComplete: Boolean = false
)
