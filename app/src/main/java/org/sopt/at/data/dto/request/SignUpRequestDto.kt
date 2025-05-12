package org.sopt.at.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequestDto(
    @SerialName("loginId")
    val loginId: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("password")
    val password: String
)
