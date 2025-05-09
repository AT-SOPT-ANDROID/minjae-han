package org.sopt.at.data.mapper

import org.sopt.at.data.dto.response.SignInResponseDto
import org.sopt.at.data.dto.response.SignUpResponseDto
import org.sopt.at.domain.entity.SignInEntity
import org.sopt.at.domain.entity.SignUpEntity

fun SignUpResponseDto.toDomain(): SignUpEntity =
    SignUpEntity(
        userId = userId,
        nickname = nickname
    )

fun SignInResponseDto.toDomain(): SignInEntity =
    SignInEntity(
        userId = userId
    )
