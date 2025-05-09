package org.sopt.at.domain.entity

data class SignUpEntity(
    val userId: Int,
    val nickname: String
)

data class SignInEntity(
    val userId: Int
)
