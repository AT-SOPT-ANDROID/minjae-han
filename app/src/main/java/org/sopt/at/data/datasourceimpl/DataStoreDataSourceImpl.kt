package org.sopt.at.data.datasourceimpl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.sopt.at.data.datasource.DataStoreDataSource

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

@Singleton
class DataStoreDataSourceImpl @Inject constructor(
    private val context: Context
) : DataStoreDataSource {
    private val userId = stringPreferencesKey("user_id")
    private val userPassword = stringPreferencesKey("user_password")
    private val loginUserId = intPreferencesKey("login_user_id")
    private val autoLogin = booleanPreferencesKey("auto_login_enabled")

    override suspend fun saveUserCredentials(id: String, password: String) {
        context.dataStore.edit { preferences ->
            preferences[userId] = id
            preferences[userPassword] = password
        }
    }

    override suspend fun saveUserId(id: Int) {
        context.dataStore.edit { preferences ->
            preferences[loginUserId] = id
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

    override fun getLoginUserId(): Flow<Int?> {
        return context.dataStore.data.map { preferences ->
            preferences[loginUserId]
        }
    }

    override suspend fun clearUserCredentials() {
        context.dataStore.edit { preferences ->
            preferences.remove(userId)
            preferences.remove(userPassword)
            preferences.remove(loginUserId)
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
            preferences[autoLogin] == true
        }
    }
}
