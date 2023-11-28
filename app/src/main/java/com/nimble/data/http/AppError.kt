package com.nimble.data.http

import com.google.gson.annotations.SerializedName

/**
 * @file Error
 * @date 11/28/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/28/2023.
 */
data class AppError(
    @SerializedName("errors") var errors: ArrayList<ErrorBody>? = arrayListOf()
)
