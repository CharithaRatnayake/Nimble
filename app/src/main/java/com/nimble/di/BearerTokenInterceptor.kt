package com.nimble.di

import okhttp3.Interceptor
import okhttp3.Response

/**
 * @file BearerTokenInterceptor
 * @date 11/26/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/26/2023.
 */
class BearerTokenInterceptor(private val authToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // Add the Bearer token to the Authorization header
        val requestWithToken = originalRequest.newBuilder()
            .header("Authorization", "Bearer $authToken")
            .build()

        return chain.proceed(requestWithToken)
    }
}