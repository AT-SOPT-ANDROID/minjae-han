package org.sopt.at.data.service

import org.sopt.at.core.network.BaseResponse
import org.sopt.at.data.dto.request.SignInRequestDto
import org.sopt.at.data.dto.request.SignUpRequestDto
import org.sopt.at.data.dto.response.SignInResponseDto
import org.sopt.at.data.dto.response.SignUpResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/api/v1/auth/signup")
    suspend fun postSignUp(
        @Body signUpRequestDto: SignUpRequestDto
    ): BaseResponse<SignUpResponseDto>

    @POST("/api/v1/auth/signin")
    suspend fun postSignIn(
        @Body signInRequestDto: SignInRequestDto
    ): BaseResponse<SignInResponseDto>
}
