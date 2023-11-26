package com.nimble.data.remote

import com.nimble.BuildConfig
import com.nimble.data.AppResponses
import com.nimble.data.AuthTokenDataModel
import com.nimble.data.ForgotPasswordResponseDataModel
import com.nimble.data.LoginResponseDataModel
import com.nimble.data.RegisterRequestDataModel
import com.nimble.data.TokenResponse
import com.nimble.data.UserDataModel
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
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
    ): Response<LoginResponseDataModel> {
        return api.token(authTokenDataModel)
    }

    suspend fun register(
        registerRequestDataModel: RegisterRequestDataModel
    ): Response<AppResponses<TokenResponse>> {
        return api.registrations(registerRequestDataModel)
    }

    suspend fun logout(
        token: String
    ): AppResponses<TokenResponse> {
        return api.logout(token, BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET)
    }

    suspend fun forgotPassword(
        registerRequestDataModel: RegisterRequestDataModel
    ): Response<ForgotPasswordResponseDataModel> {
        return api.forgotPassword(registerRequestDataModel)
    }

}