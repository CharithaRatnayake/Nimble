package com.nimble.data.remote

import com.nimble.data.AppResponses
import com.nimble.data.AuthTokenDataModel
import com.nimble.data.SurveysDataModel
import com.nimble.data.TokenResponse
import com.nimble.data.UserDataModel
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * @file NimbleAppApi
 * @date 11/25/2023
 * @brief Nimble app api class
 * Created by Charitha Ratnayake(jachratnayake@gmail.com) on 11/25/2023.
 */
interface NimbleAppApi {

    @GET("/surveys")
    suspend fun getSurveys(): AppResponses<TokenResponse>

    @POST("/surveys/{id}")
    suspend fun getSurveysDetails(@Path("id") mApplianceId: String): AppResponses<TokenResponse>

    @POST("/responses")
    suspend fun submitSurveys(
        @Body surveysDataModel: SurveysDataModel
    ): AppResponses<TokenResponse>
}