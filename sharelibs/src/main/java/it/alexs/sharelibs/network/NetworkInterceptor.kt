package it.alexs.sharelibs.network

import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        if (originalRequest.header("X-Api-Key") != null) {
            chain.proceed(originalRequest)
        }

        val authenticateRequest = originalRequest.newBuilder()
            .header("X-Api-Key", "5892bc04750c416e8d675db688b2885d")
            .build()

        return chain.proceed(authenticateRequest)
    }
}