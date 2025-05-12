package org.sopt.at.data.repositoryImpl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import org.sopt.at.data.datasource.DataStoreDataSource
import org.sopt.at.domain.repository.DataStoreRepository

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

@Singleton
class DataStoreRepositoryImpl @Inject constructor(
    private val dataSource: DataStoreDataSource
) : DataStoreRepository {
    override suspend fun saveUserCredentials(id: String, password: String) {
        dataSource.saveUserCredentials(id, password)
    }

    override fun getUserId(): Flow<String?> {
        return dataSource.getUserId()
    }

    override fun getUserPassword(): Flow<String?> {
        return dataSource.getUserPassword()
    }

    override suspend fun clearUserCredentials() {
        dataSource.clearUserCredentials()
    }

    override suspend fun setAutoLogin(enabled: Boolean) {
        dataSource.setAutoLogin(enabled)
    }

    override fun isAutoLoginEnabled(): Flow<Boolean> {
        return dataSource.isAutoLoginEnabled()
    }

    override suspend fun saveUserId(id: Int) {
        dataSource.saveUserId(id)
    }

    override fun getLoginUserId(): Flow<Int?> {
        return dataSource.getLoginUserId()
    }
}
