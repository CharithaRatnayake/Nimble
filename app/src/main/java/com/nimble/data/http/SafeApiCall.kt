package com.nimble.data.http

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException


/**
 * @file com.nimble.data
 * @date 11/28/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/28/2023.
 */
interface SafeApiCall {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {

                    is HttpException -> {
                        Resource.Failure(false, throwable.code(), throwable.response()?.errorBody())
                    }

                    else -> {
                        Resource.Failure(true, null, null)
                    }
                }
            }
        }
    }


}