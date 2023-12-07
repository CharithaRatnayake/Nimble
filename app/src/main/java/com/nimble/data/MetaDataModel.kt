package com.nimble.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @file MetaDataModel
 * @date 11/26/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/26/2023.
 */
@Keep
data class MetaDataModel(
    @SerializedName("message") var message: String = "",
    @SerializedName("page") var page: Int = 0,
    @SerializedName("pages") var pages: Int = 0,
    @SerializedName("page_size") var pageSize: Int = 0,
    @SerializedName("records") var records: Int = 0
)