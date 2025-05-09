package org.sopt.at.data.mapper

import org.sopt.at.data.dto.response.MyNickNameResponseDto
import org.sopt.at.domain.entity.UserEntity

fun MyNickNameResponseDto.toDomain() = UserEntity(
    nickname = this.nickname
)
