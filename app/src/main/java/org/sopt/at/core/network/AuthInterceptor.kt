package org.sopt.at.core.network

import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.sopt.at.domain.repository.DataStoreRepository

class AuthInterceptor @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val authRequest = originalRequest.newBuilder().newAuthBuilder().build()

        val response = chain.proceed(authRequest)

        return response
    }

    private fun Request.Builder.newAuthBuilder() =
        this.apply {
            // userId 헤더 추가 (로그인된 경우에만)
            runBlocking {
                val userId = dataStoreRepository.getLoginUserId().first()
                userId?.let {
                    addHeader(USER_ID, it.toString())
                }
            }
        }

    companion object {
        private const val USER_ID = "userId"
    }
}
