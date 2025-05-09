package org.sopt.at.data.service

import org.sopt.at.core.network.BaseResponse
import org.sopt.at.data.dto.UserInfoResponse
import retrofit2.http.GET

interface UserApiService {
    @GET("/api/v1/users/me")
    suspend fun getUserInfo(): BaseResponse<UserInfoResponse>
}
