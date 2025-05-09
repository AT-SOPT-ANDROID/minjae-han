package org.sopt.at.data.datasource

import kotlinx.coroutines.flow.Flow

interface DataStoreDataSource {
    suspend fun saveUserCredentials(id: String, password: String)

    suspend fun saveUserId(id: Int)

    fun getUserId(): Flow<String?>

    fun getUserPassword(): Flow<String?>

    fun getLoginUserId(): Flow<Int?>

    suspend fun clearUserCredentials()

    suspend fun setAutoLogin(enabled: Boolean)

    fun isAutoLoginEnabled(): Flow<Boolean>
}
