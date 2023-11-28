package com.nimble.data.remote

import com.nimble.data.AppResponses
import com.nimble.data.AuthTokenDataModel
import com.nimble.data.UserDataModel
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST

/**
 * @file NimbleAuthApi
 * @date 11/25/2023
 * @brief Nimble app authenticate api class
 * Created by Charitha Ratnayake(jachratnayake@gmail.com) on 11/25/2023.
 */
interface NimbleAuthApi {

    @POST("/registrations")
    suspend fun registrations(
        @Field("user") user: UserDataModel,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String
    ): AppResponses

    @POST("/oauth/token")
    suspend fun token(@Body authTokenDataModel: AuthTokenDataModel): AppResponses

    @POST("/oauth/revoke")
    suspend fun logout(
        @Field("token") token: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String
    ): AppResponses

    @POST("/password")
    suspend fun forgotPassword(
        @Field("user") user: UserDataModel,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String
    ): AppResponses
}