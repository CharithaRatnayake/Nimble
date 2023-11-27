package com.nimble.data.remote

import com.nimble.base.AppConstants
import com.nimble.data.AppResponses
import com.nimble.data.AuthTokenDataModel
import com.nimble.data.ForgotPasswordResponseDataModel
import com.nimble.data.LoginResponseDataModel
import com.nimble.data.RegisterRequestDataModel
import com.nimble.data.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @file NimbleAuthApi
 * @date 11/25/2023
 * @brief Nimble app authenticate api class
 * Created by Charitha Ratnayake(jachratnayake@gmail.com) on 11/25/2023.
 */
interface NimbleAuthApi {

    @POST(AppConstants.API_PATH_REGISTRATION)
    suspend fun registrations(
        @Body registerRequestDataModel: RegisterRequestDataModel
    ): Response<AppResponses<TokenResponse>>

    @POST(AppConstants.API_PATH_AUTH_TOKEN)
    suspend fun token(@Body authTokenDataModel: AuthTokenDataModel): Response<LoginResponseDataModel>

    @POST(AppConstants.API_PATH_LOGOUT)
    suspend fun logout(@Body authTokenDataModel: AuthTokenDataModel): Response<TokenResponse>

    @POST(AppConstants.API_PATH_FORGOT_PASSWORD)
    suspend fun forgotPassword(
        @Body registerRequestDataModel: RegisterRequestDataModel
    ): Response<ForgotPasswordResponseDataModel>
}