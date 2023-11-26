package com.nimble.data

import com.google.gson.annotations.SerializedName
import com.nimble.BuildConfig

/**
 * @file HeaderDataModel
 * @date 11/26/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/26/2023.
 */
open class HeaderDataModel(
    @SerializedName("client_id") var clientId: String? = BuildConfig.CLIENT_ID,
    @SerializedName("client_secret") var clientSecret: String? = BuildConfig.CLIENT_SECRET
)

