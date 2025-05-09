package org.sopt.at.domain.repository

import org.sopt.at.domain.entity.UserEntity

interface UserRepository {
    suspend fun getUserNickName(userId: Int): Result<UserEntity>
}
