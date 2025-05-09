package org.sopt.at.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sopt.at.domain.entity.UserEntity

@Serializable
data class SignUpRequest(
    @SerialName("loginId")
    val loginId: String,
    @SerialName("password")
    val password: String,
    @SerialName("nickname")
    val nickname: String
)

@Serializable
data class SignUpResponse(
    @SerialName("userId")
    val userId: Long,
    @SerialName("nickname")
    val nickname: String
) {
    fun toDomain(): UserEntity = UserEntity(
        userId = userId,
        nickname = nickname
    )
}

@Serializable
data class SignInRequest(
    @SerialName("loginId")
    val loginId: String,
    @SerialName("password")
    val password: String
)

@Serializable
data class SignInResponse(
    @SerialName("userId")
    val userId: Long
) {
    fun toDomain(): UserEntity = UserEntity(
        userId = userId,
        nickname = ""
    )
}

@Serializable
data class UserInfoResponse(
    @SerialName("nickname")
    val nickname: String
) {
    fun toDomain(): UserEntity = UserEntity(
        userId = -1, // Not available in this response
        nickname = nickname
    )
}
