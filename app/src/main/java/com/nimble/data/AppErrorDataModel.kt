package com.nimble.data

import com.google.gson.annotations.SerializedName

/**
 * @file AppErrorDataModel
 * @date 11/26/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/26/2023.
 */
open class AppErrorDataModel(
    @SerializedName("detail") var detail: String,
    @SerializedName("code") var code: String
)

