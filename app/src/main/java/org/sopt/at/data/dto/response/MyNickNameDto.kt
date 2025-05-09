package org.sopt.at.data.dto.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyNickNameDto(
    @SerialName("nickname")
    val nickname: String
)
