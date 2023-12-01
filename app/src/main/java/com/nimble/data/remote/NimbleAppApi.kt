package com.nimble.data.remote

import com.nimble.base.AppConstants
import com.nimble.data.AppResponses
import com.nimble.data.SurveyListResponseDataModel
import com.nimble.data.TokenResponse
import com.nimble.data.UserResponseDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @file NimbleAppApi
 * @date 11/25/2023
 * @brief Nimble app api class
 * Created by Charitha Ratnayake(jachratnayake@gmail.com) on 11/25/2023.
 */
interface NimbleAppApi {

    @GET(AppConstants.API_PATH_GET_USER_PROFILE)
    suspend fun getUserProfile(): Response<UserResponseDataModel>

    @GET(AppConstants.API_PATH_GET_SURVEYS_LIST)
    suspend fun getSurveys(
        @Query(AppConstants.API_QUERY_PAGE_NUMBER, encoded = true) pageNumber: Int,
        @Query(AppConstants.API_QUERY_PAGE_SIZE, encoded = true) pageSize: Int
    ): Response<SurveyListResponseDataModel>

    @POST("/surveys/{id}")
    suspend fun getSurveysDetails(@Path("id") mApplianceId: String): AppResponses<TokenResponse>

    @POST("/responses")
    suspend fun submitSurveys(): AppResponses<TokenResponse>
}