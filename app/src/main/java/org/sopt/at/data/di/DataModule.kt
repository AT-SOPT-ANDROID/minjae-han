package org.sopt.at.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.sopt.at.data.provider.UserIdProvider
import org.sopt.at.data.provider.UserIdProviderImpl
import org.sopt.at.data.remote.AuthRemoteDataSource
import org.sopt.at.data.remote.AuthRemoteDataSourceImpl
import org.sopt.at.data.remote.UserRemoteDataSource
import org.sopt.at.data.remote.UserRemoteDataSourceImpl
import org.sopt.at.data.repository.UserRepositoryImpl
import org.sopt.at.data.service.AuthApiService
import org.sopt.at.data.service.UserApiService
import org.sopt.at.domain.repository.UserRepository
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideUserIdProvider(
        @ApplicationContext context: Context
    ): UserIdProvider {
        return UserIdProviderImpl(context)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        @ApplicationContext context: Context,
        authRemoteDataSource: AuthRemoteDataSource,
        userRemoteDataSource: UserRemoteDataSource,
        userIdProvider: UserIdProvider
    ): UserRepository {
        return UserRepositoryImpl(context, authRemoteDataSource, userRemoteDataSource, userIdProvider)
    }

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApiService(retrofit: Retrofit): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRemoteDataSource(authApiService: AuthApiService): AuthRemoteDataSource {
        return AuthRemoteDataSourceImpl(authApiService)
    }

    @Provides
    @Singleton
    fun provideUserRemoteDataSource(userApiService: UserApiService): UserRemoteDataSource {
        return UserRemoteDataSourceImpl(userApiService)
    }
}
