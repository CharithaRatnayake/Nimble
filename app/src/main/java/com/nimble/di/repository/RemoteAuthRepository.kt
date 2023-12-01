package com.nimble.di.repository

import com.nimble.data.AuthTokenDataModel
import com.nimble.data.RegisterRequestDataModel
import com.nimble.data.http.SafeApiCall
import com.nimble.data.remote.NimbleAuthApi
import javax.inject.Inject

/**
 * @file RemoteAuthRepository
 * @date 11/25/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/25/2023.
 */
class RemoteAuthRepository @Inject constructor(private val api: NimbleAuthApi) : SafeApiCall {

    suspend fun login(
        authTokenDataModel: AuthTokenDataModel
    ) = safeApiCall { api.token(authTokenDataModel) }

    suspend fun register(
        registerRequestDataModel: RegisterRequestDataModel
    ) = safeApiCall { api.registrations(registerRequestDataModel) }

    suspend fun logout(
        authTokenDataModel: AuthTokenDataModel
    ) = safeApiCall { api.logout(authTokenDataModel) }

    suspend fun forgotPassword(
        registerRequestDataModel: RegisterRequestDataModel
    ) = safeApiCall { api.forgotPassword(registerRequestDataModel) }

}