package org.sopt.at.data.service

import org.sopt.at.core.network.BaseResponse
import org.sopt.at.data.dto.response.MyNickNameResponseDto
import retrofit2.http.GET
import retrofit2.http.Header

interface UserService {
    @GET("/api/v1/users/me")
    suspend fun getUserNickName(
        @Header("userId")
        userId: Int
    ): BaseResponse<MyNickNameResponseDto>
}
