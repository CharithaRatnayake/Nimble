package com.nimble.data

import com.google.gson.annotations.SerializedName
import com.nimble.BuildConfig
import com.nimble.base.AppConstants
import com.nimble.utils.Base64Util

/**
 * @file HeaderDataModel
 * @date 11/26/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/26/2023.
 */
open class HeaderDataModel(
    @SerializedName("client_id") var clientId: String = Base64Util.base64ToString(AppConstants.CLIENT_ID),
    @SerializedName("client_secret") var clientSecret: String = Base64Util.base64ToString(AppConstants.CLIENT_SECRET)
)

