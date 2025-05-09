package org.sopt.at.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveUserCredentials(id: String, password: String)
    fun getUserId(): Flow<String?>
    fun getUserPassword(): Flow<String?>
    suspend fun clearUserCredentials()
    suspend fun setAutoLogin(enabled: Boolean)
    fun isAutoLoginEnabled(): Flow<Boolean>
}
