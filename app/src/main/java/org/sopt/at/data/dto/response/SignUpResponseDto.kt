package org.sopt.at.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpResponseDto(
    @SerialName("userId")
    val userId: Int,
    @SerialName("nickname")
    val nickname: String,
)
