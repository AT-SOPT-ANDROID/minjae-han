package org.sopt.at.data.datasourceimpl

import javax.inject.Inject
import org.sopt.at.core.network.BaseResponse
import org.sopt.at.data.datasource.UserRemoteDataSource
import org.sopt.at.data.dto.response.MyNickNameResponseDto
import org.sopt.at.data.service.UserService

class UserRemoteDataSourceImpl @Inject constructor(
    private val userService: UserService
) : UserRemoteDataSource {
    override suspend fun getUserNickName(): BaseResponse<MyNickNameResponseDto> =
        userService.getUserNickName()
}
