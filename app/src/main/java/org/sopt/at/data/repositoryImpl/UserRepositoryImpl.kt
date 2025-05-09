package org.sopt.at.data.repositoryImpl

import org.sopt.at.data.datasource.UserRemoteDataSource
import org.sopt.at.data.mapper.toDomain
import org.sopt.at.domain.entity.UserEntity
import org.sopt.at.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserRemoteDataSource
) : UserRepository {
    override suspend fun getUserNickName(userId: Int): Result<UserEntity> =
        runCatching {
            userDataSource.getUserNickName(userId).data!!.toDomain()
        }
}
