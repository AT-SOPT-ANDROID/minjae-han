package org.sopt.at.data.remote

import org.sopt.at.core.network.BaseResponse
import org.sopt.at.data.dto.SignInRequest
import org.sopt.at.data.dto.SignInResponse
import org.sopt.at.data.dto.SignUpRequest
import org.sopt.at.data.dto.SignUpResponse
import org.sopt.at.data.service.AuthApiService
import javax.inject.Inject

interface AuthRemoteDataSource {
    suspend fun signUp(loginId: String, password: String, nickname: String): BaseResponse<SignUpResponse>
    suspend fun signIn(loginId: String, password: String): BaseResponse<SignInResponse>
}

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authApiService: AuthApiService
) : AuthRemoteDataSource {
    
    override suspend fun signUp(
        loginId: String,
        password: String,
        nickname: String
    ): BaseResponse<SignUpResponse> {
        return authApiService.signUp(
            SignUpRequest(
                loginId = loginId,
                password = password,
                nickname = nickname
            )
        )
    }

    override suspend fun signIn(loginId: String, password: String): BaseResponse<SignInResponse> {
        return authApiService.signIn(
            SignInRequest(
                loginId = loginId,
                password = password
            )
        )
    }
}
