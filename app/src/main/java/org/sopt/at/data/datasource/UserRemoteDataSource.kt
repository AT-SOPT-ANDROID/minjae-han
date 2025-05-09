package org.sopt.at.data.datasource

import org.sopt.at.core.network.BaseResponse
import org.sopt.at.data.dto.response.MyNickNameResponseDto

interface UserRemoteDataSource {
    suspend fun getUserNickName(): BaseResponse<MyNickNameResponseDto>
}
