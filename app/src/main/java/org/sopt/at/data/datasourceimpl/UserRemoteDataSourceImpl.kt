package org.sopt.at.data.datasourceimpl

import org.sopt.at.core.network.BaseResponse
import org.sopt.at.data.datasource.UserRemoteDataSource
import org.sopt.at.data.dto.response.MyNickNameResponseDto
import org.sopt.at.data.service.UserService
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val userService: UserService
) : UserRemoteDataSource{
    override suspend fun getUserNickName(): BaseResponse<MyNickNameResponseDto> =
        userService.getUserNickName()
}
