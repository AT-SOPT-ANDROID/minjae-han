package org.sopt.at.data.remote

import org.sopt.at.core.network.BaseResponse
import org.sopt.at.data.dto.UserInfoResponse
import org.sopt.at.data.service.UserApiService
import javax.inject.Inject

interface UserRemoteDataSource {
    suspend fun getUserInfo(): BaseResponse<UserInfoResponse>
}

class UserRemoteDataSourceImpl @Inject constructor(
    private val userApiService: UserApiService
) : UserRemoteDataSource {
    
    override suspend fun getUserInfo(): BaseResponse<UserInfoResponse> {
        return userApiService.getUserInfo()
    }
}
