package org.sopt.at.data.provider

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

interface UserIdProvider {
    fun getUserId(): Long?
    suspend fun saveUserId(userId: Long)
}

@Singleton
class UserIdProviderImpl @Inject constructor(
    private val context: Context
) : UserIdProvider {
    private val apiUserId = longPreferencesKey("api_user_id")
    
    override fun getUserId(): Long? {
        return runBlocking {
            context.userDataStore.data.map { preferences ->
                preferences[apiUserId]
            }.first()
        }
    }
    
    override suspend fun saveUserId(userId: Long) {
        context.userDataStore.edit { preferences ->
            preferences[apiUserId] = userId
        }
    }
}
