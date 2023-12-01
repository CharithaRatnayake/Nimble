package com.nimble.data

import com.google.gson.annotations.SerializedName
import com.nimble.data.http.ErrorBody

/**
 * @file AppResponses
 * @date 11/25/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/25/2023.
 */
data class AppResponses<T>(
    val data: T? = null,
    @SerializedName("errors") var errors: ArrayList<ErrorBody>? = arrayListOf()
)
