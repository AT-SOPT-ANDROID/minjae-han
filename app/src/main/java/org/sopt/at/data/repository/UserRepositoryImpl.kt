package org.sopt.at.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.sopt.at.data.provider.UserIdProvider
import org.sopt.at.data.remote.AuthRemoteDataSource
import org.sopt.at.data.remote.UserRemoteDataSource
import org.sopt.at.domain.entity.UserEntity
import org.sopt.at.domain.repository.UserRepository

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val context: Context,
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userIdProvider: UserIdProvider
) : UserRepository {
    private val userId = stringPreferencesKey("user_id")
    private val userPassword = stringPreferencesKey("user_password")
    private val autoLogin = booleanPreferencesKey("auto_login_enabled")

    override suspend fun saveUserCredentials(id: String, password: String) {
        context.dataStore.edit { preferences ->
            preferences[userId] = id
            preferences[userPassword] = password
        }
    }

    override fun getUserId(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[userId]
        }
    }

    override fun getUserPassword(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[userPassword]
        }
    }

    override suspend fun clearUserCredentials() {
        context.dataStore.edit { preferences ->
            preferences.remove(userId)
            preferences.remove(userPassword)
            preferences[autoLogin] = false
        }
    }

    override suspend fun setAutoLogin(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[autoLogin] = enabled
        }
    }

    override fun isAutoLoginEnabled(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[autoLogin] ?: false
        }
    }
    
    override suspend fun signUp(loginId: String, password: String, nickname: String): Result<UserEntity> {
        return runCatching {
            val response = authRemoteDataSource.signUp(loginId, password, nickname)
            if (response.success) {
                response.data?.toDomain() ?: throw IllegalStateException("SignUp data is null")
            } else {
                throw IllegalStateException(response.message)
            }
        }
    }

    override suspend fun signIn(loginId: String, password: String): Result<UserEntity> {
        return runCatching {
            val response = authRemoteDataSource.signIn(loginId, password)
            if (response.success) {
                val userEntity = response.data?.toDomain() ?: throw IllegalStateException("SignIn data is null")
                saveUserId(userEntity.userId)
                userEntity
            } else {
                throw IllegalStateException(response.message)
            }
        }
    }

    override suspend fun getUserInfo(): Result<UserEntity> {
        return runCatching {
            val response = userRemoteDataSource.getUserInfo()
            if (response.success) {
                response.data?.toDomain() ?: throw IllegalStateException("사용자 정보 데이터가 null입니다")
            } else {
                throw IllegalStateException(response.message)
            }
        }
    }

    override suspend fun saveUserId(userId: Long) {
        userIdProvider.saveUserId(userId)
    }

    override suspend fun getSavedUserId(): Long? {
        return userIdProvider.getUserId()
    }
}
