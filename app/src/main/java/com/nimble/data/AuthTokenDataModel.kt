package com.nimble.data

import com.google.gson.annotations.SerializedName
import com.nimble.BuildConfig
import com.nimble.base.AppConstants
import com.nimble.utils.Base64Util

/**
 * @file AuthTokenDataModel
 * @date 11/25/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/25/2023.
 */
data class AuthTokenDataModel(
    @SerializedName("grant_type") var grantType: String = GrantType.PASSWORD.value,
    @SerializedName("refresh_token") var refresh_token: String = "",
    @SerializedName("email") var email: String = "",
    @SerializedName("password") var password: String = "",
    @SerializedName("token") var token: String = "",
    @SerializedName("client_id") var clientId: String = Base64Util.base64ToString(AppConstants.CLIENT_ID),
    @SerializedName("client_secret") var clientSecret: String = Base64Util.base64ToString(AppConstants.CLIENT_SECRET)
)