package org.sopt.at.data.datasourceimpl

import org.sopt.at.core.network.BaseResponse
import org.sopt.at.data.service.AuthService
import org.sopt.at.data.datasource.AuthRemoteDataSource
import org.sopt.at.data.dto.request.SignInRequestDto
import org.sopt.at.data.dto.request.SignUpRequestDto
import org.sopt.at.data.dto.response.SignInResponseDto
import org.sopt.at.data.dto.response.SignUpResponseDto
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService
) : AuthRemoteDataSource {
    override suspend fun postSignUp(
        loginId: String, password: String, nickname: String
    ): BaseResponse<SignUpResponseDto> =
        authService.postSignUp(
            SignUpRequestDto(
                loginId = loginId,
                password = password,
                nickname = nickname
            )
        )


    override suspend fun postSignIn(
        loginId: String, password: String
    ): BaseResponse<SignInResponseDto> =
        authService.postSignIn(
            SignInRequestDto(
                loginId = loginId,
                password = password
            )
        )
}
