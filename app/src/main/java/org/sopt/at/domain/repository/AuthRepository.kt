package org.sopt.at.domain.repository

import org.sopt.at.domain.entity.SignInEntity
import org.sopt.at.domain.entity.SignUpEntity

interface AuthRepository {
    suspend fun signUp(
        loginId: String,
        password: String,
        nickname: String
    ): Result<SignUpEntity>

    suspend fun signIn(
        loginId: String,
        password: String
    ): Result<SignInEntity>
}
