package org.sopt.at.domain.repository

import kotlinx.coroutines.flow.Flow
import org.sopt.at.domain.entity.UserEntity

interface UserRepository {
    // Local storage methods
    suspend fun saveUserCredentials(id: String, password: String)
    fun getUserId(): Flow<String?>
    fun getUserPassword(): Flow<String?>
    suspend fun clearUserCredentials()
    suspend fun setAutoLogin(enabled: Boolean)
    fun isAutoLoginEnabled(): Flow<Boolean>
    
    // API methods
    suspend fun signUp(loginId: String, password: String, nickname: String): Result<UserEntity>
    suspend fun signIn(loginId: String, password: String): Result<UserEntity>
    suspend fun getUserInfo(): Result<UserEntity>
    
    // userId storage for API
    suspend fun saveUserId(userId: Long)
    suspend fun getSavedUserId(): Long?
}
