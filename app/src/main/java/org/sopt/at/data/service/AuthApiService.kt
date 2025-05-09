package org.sopt.at.data.service

import org.sopt.at.core.network.BaseResponse
import org.sopt.at.data.dto.SignInRequest
import org.sopt.at.data.dto.SignInResponse
import org.sopt.at.data.dto.SignUpRequest
import org.sopt.at.data.dto.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("/api/v1/auth/signup")
    suspend fun signUp(
        @Body request: SignUpRequest
    ): BaseResponse<SignUpResponse>

    @POST("/api/v1/auth/signin")
    suspend fun signIn(
        @Body request: SignInRequest
    ): BaseResponse<SignInResponse>
}
