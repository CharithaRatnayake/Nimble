package com.nimble.di

import android.util.Log
import com.nimble.BuildConfig
import com.nimble.base.AppConstants
import com.nimble.data.AuthTokenDataModel
import com.nimble.data.GrantType
import com.nimble.data.remote.NimbleAuthApi
import com.nimble.di.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @file AuthInterceptor.kt
 * @date 11/27/2023
 * @brief AuthInterceptor for refresh the token
 * Created by Charitha Ratnayake(jachratnayake@gmail.com) on 11/27/2023.
 */
class AuthInterceptor(
    private val userPreferencesRepository: UserPreferencesRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val isLogged = runBlocking { userPreferencesRepository.isLogged.first() }

        if (isLogged) {
            val authToken = runBlocking { userPreferencesRepository.authToken.firstOrNull() }

            authToken?.let {
                val newRequest = request.newBuilder()
                    .header(
                        "Authorization",
                        "Bearer ${it[AppConstants.DATASTORE_KEY_ACCESS_TOKEN]}"
                    )
                    .build()

                val response = chain.proceed(newRequest)

                if (response.code == 401) {
                    response.close()
                    Log.e(javaClass.simpleName, "Auth token expired.")

                    // Handle token refresh here asynchronously
                    runBlocking {
                        refreshTokenAsync(userPreferencesRepository)
                    }


                    val newAuthToken =
                        runBlocking { userPreferencesRepository.authToken.firstOrNull() }
                    // Retry the request with the new token
                    return chain.proceed(
                        newRequest.newBuilder()
                            .header(
                                "Authorization",
                                "Bearer ${newAuthToken?.get(AppConstants.DATASTORE_KEY_ACCESS_TOKEN)}"
                            )
                            .build()
                    )
                }

                return response
            }
        }

        // Handle the case where the user is not logged in
        return chain.proceed(request)
    }

    private suspend fun refreshTokenAsync(userPreferencesRepository: UserPreferencesRepository) {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val refreshToken = userPreferencesRepository.authToken.firstOrNull()
            ?.get(AppConstants.DATASTORE_KEY_REFRESH_TOKEN)
        refreshToken?.let {
            val authData = AuthTokenDataModel(
                grantType = GrantType.REFRESH_TOKEN.value,
                refresh_token = it
            )

            try {
                val authResponse = retrofit.create(NimbleAuthApi::class.java).token(authData)

                if (authResponse.isSuccessful) {
                    authResponse.body()?.let {
                        val expireIn = it.data.attributes.expiresIn + it.data.attributes.createdAt
                        val newAccessToken = it.data.attributes.accessToken

                        userPreferencesRepository.saveUserData(
                            true,
                            expireIn,
                            newAccessToken,
                            it.data.attributes.refreshToken
                        )
                    }
                } else {
                    Log.e(javaClass.simpleName, "Error while refresh the token.")
                    userPreferencesRepository.clearDataStore()
                }
            } catch (e: Exception) {
                Log.e(javaClass.simpleName, "Error refreshing token: ${e.message}")
                userPreferencesRepository.clearDataStore()
            }
        }
    }
}