package com.nimble.data

import com.google.gson.annotations.SerializedName

/**
 * @file AppResponses
 * @date 11/25/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/25/2023.
 */
data class AppResponses<T>(
    val data: T? = null,
    @SerializedName("errors") var errors: ArrayList<AppErrorDataModel>? = arrayListOf()
)
