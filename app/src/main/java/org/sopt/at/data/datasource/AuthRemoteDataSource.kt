package org.sopt.at.data.datasource

import org.sopt.at.core.network.BaseResponse
import org.sopt.at.data.dto.response.SignInResponseDto
import org.sopt.at.data.dto.response.SignUpResponseDto

interface AuthRemoteDataSource {
    suspend fun postSignUp(
        loginId: String,
        password: String,
        nickname: String
    ): BaseResponse<SignUpResponseDto>

    suspend fun postSignIn(
        loginId: String,
        password: String
    ): BaseResponse<SignInResponseDto>
}
