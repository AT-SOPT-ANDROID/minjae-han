package org.sopt.at.data.repositoryImpl

import javax.inject.Inject
import org.sopt.at.data.datasource.AuthRemoteDataSource
import org.sopt.at.data.mapper.toDomain
import org.sopt.at.domain.entity.SignInEntity
import org.sopt.at.domain.entity.SignUpEntity
import org.sopt.at.domain.repository.AuthRepository

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthRemoteDataSource
) : AuthRepository {
    override suspend fun signUp(
        loginId: String,
        password: String,
        nickname: String
    ): Result<SignUpEntity> =
        runCatching {
            authDataSource.postSignUp(loginId, password, nickname).data!!.toDomain()
        }

    override suspend fun signIn(
        loginId: String,
        password: String
    ): Result<SignInEntity> =
        runCatching {
            authDataSource.postSignIn(loginId, password).data!!.toDomain()
        }
}
