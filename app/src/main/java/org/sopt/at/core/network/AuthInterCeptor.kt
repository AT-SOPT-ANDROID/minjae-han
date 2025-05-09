package org.sopt.at.core.network

import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.sopt.at.data.provider.UserIdProvider

class AuthInterceptor @Inject constructor(
    private val userIdProvider: UserIdProvider
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        
        val authRequest = originalRequest.newBuilder().newAuthBuilder().build()
        
        val response = chain.proceed(authRequest)
        
        return response
    }
    
    private fun Request.Builder.newAuthBuilder(): Request.Builder {
        val userId = userIdProvider.getUserId()
        
        return if (userId != null) {
            this.addHeader(USER_ID, userId.toString())
        } else {
            this
        }
    }
    
    companion object {
        private const val USER_ID = "userId"
    }
}
