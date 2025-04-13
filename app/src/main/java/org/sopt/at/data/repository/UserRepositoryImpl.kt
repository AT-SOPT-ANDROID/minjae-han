package org.sopt.at.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.sopt.at.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val context: Context
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
}
