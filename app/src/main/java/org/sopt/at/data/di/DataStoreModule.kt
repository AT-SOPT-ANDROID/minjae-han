package org.sopt.at.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.sopt.at.data.datasource.DataStoreDataSource
import org.sopt.at.data.datasourceimpl.DataStoreDataSourceImpl
import org.sopt.at.data.repositoryImpl.DataStoreRepositoryImpl
import org.sopt.at.domain.repository.DataStoreRepository

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStoreDataSource(@ApplicationContext context: Context): DataStoreDataSource {
        return DataStoreDataSourceImpl(context)
    }

    @Provides
    @Singleton
    fun provideDataStoreRepository(dataSource: DataStoreDataSource): DataStoreRepository {
        return DataStoreRepositoryImpl(dataSource)
    }
}
