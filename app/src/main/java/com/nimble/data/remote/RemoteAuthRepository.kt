package com.nimble.data.remote

import com.nimble.BuildConfig
import com.nimble.data.AppResponses
import com.nimble.data.AuthTokenDataModel
import com.nimble.data.TokenResponse
import com.nimble.data.UserDataModel
import javax.inject.Inject

/**
 * @file RemoteAuthRepository
 * @date 11/25/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/25/2023.
 */
class RemoteAuthRepository @Inject constructor(private val api: NimbleAuthApi) {

    suspend fun login(
        authTokenDataModel: AuthTokenDataModel
    ): AppResponses<TokenResponse> {
        return api.token(authTokenDataModel)
    }

    suspend fun register(
        userDataModel: UserDataModel
    ): AppResponses<TokenResponse> {
        return api.registrations(userDataModel, BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET)
    }

    suspend fun logout(
        token: String
    ): AppResponses<TokenResponse> {
        return api.logout(token, BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET)
    }

    suspend fun forgotPassword(
        userDataModel: UserDataModel
    ): AppResponses<TokenResponse> {
        return api.forgotPassword(userDataModel, BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET)
    }

}