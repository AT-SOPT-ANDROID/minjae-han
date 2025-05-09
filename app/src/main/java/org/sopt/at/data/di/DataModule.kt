package org.sopt.at.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.sopt.at.data.repositoryImpl.UserRepositoryImpl
import org.sopt.at.domain.repository.UserRepository

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideUserRepository(@ApplicationContext context: Context): UserRepository {
        return UserRepositoryImpl(context)
    }
}
