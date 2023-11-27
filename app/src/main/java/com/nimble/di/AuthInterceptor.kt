package com.nimble.di

import android.content.Context
import com.nimble.BuildConfig
import com.nimble.data.AuthTokenDataModel
import com.nimble.data.GrantType
import com.nimble.data.local.UserPreferencesRepository
import com.nimble.data.remote.NimbleAuthApi
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class AuthInterceptor constructor(private val userPreferencesRepository: UserPreferencesRepository) : Interceptor {
    // Other interceptor configuration and properties


    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
//        userPreferencesRepository.accessToken.collect { accessToken->
//            if (!accessToken.isNullOrEmpty()){
//
//            }
//        }

        runBlocking {

            userPreferencesRepository.refreshToken.collect {
                if (!it.isNullOrEmpty()) {
                    val auth = AuthTokenDataModel(
                        grantType = GrantType.REFRESH_TOKEN.value,
                        refresh_token = it
                    )
                    val response = Retrofit.Builder()
                        .baseUrl(BuildConfig.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build().create(NimbleAuthApi::class.java).token(auth)

                    if (response.isSuccessful){

                    }else{

                    }
                }
            }


        }

//        if (accessToken != null && sessionManager.isAccessTokenExpired()) {
//            val refreshToken = sessionManager.getRefreshToken()
//
//            if (refreshedToken != null) {
//                // Create a new request with the refreshed access token
//                val newRequest = originalRequest.newBuilder()
//                    .header("Authorization", "Bearer refreshedToken")
//                    .build()
//
//                // Retry the request with the new access token
//                return chain.proceed(newRequest)
//            }
//        }

        // Add the access token to the request header
        val authorizedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer accessToken")
            .build()

        return chain.proceed(authorizedRequest)
    }
}