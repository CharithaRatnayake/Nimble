package com.nimble.di.repository

import com.nimble.data.http.SafeApiCall
import com.nimble.data.remote.NimbleAppApi
import javax.inject.Inject

/**
 * @file RemoteAuthRepository
 * @date 11/25/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/25/2023.
 */
class RemoteAppRepository @Inject constructor(private val api: NimbleAppApi) : SafeApiCall {

    suspend fun getUserProfile() = safeApiCall { api.getUserProfile() }

    suspend fun getSurveys(page: Int, pageSize: Int) =
        safeApiCall { api.getSurveys(page, pageSize) }
}