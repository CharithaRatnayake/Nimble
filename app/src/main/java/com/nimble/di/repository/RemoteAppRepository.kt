package com.nimble.di.repository

import com.nimble.data.SurveyListResponseDataModel
import com.nimble.data.UserResponseDataModel
import com.nimble.data.remote.NimbleAppApi
import retrofit2.Response
import javax.inject.Inject

/**
 * @file RemoteAuthRepository
 * @date 11/25/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/25/2023.
 */
class RemoteAppRepository @Inject constructor(private val api: NimbleAppApi) {

    suspend fun getUserProfile(): Response<UserResponseDataModel> {
        return api.getUserProfile()
    }

    suspend fun getSurveys(page: Int, pageSize: Int): Response<SurveyListResponseDataModel> {
        return api.getSurveys(page, pageSize)
    }

}