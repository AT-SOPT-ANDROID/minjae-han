package org.sopt.at.data.repositoryImpl

import javax.inject.Inject
import org.sopt.at.data.datasource.UserRemoteDataSource
import org.sopt.at.data.mapper.toDomain
import org.sopt.at.domain.entity.UserEntity
import org.sopt.at.domain.repository.UserRepository

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserRemoteDataSource
) : UserRepository {
    override suspend fun getUserNickName(): Result<UserEntity> =
        runCatching {
            userDataSource.getUserNickName().data!!.toDomain()
        }
}
