package com.nimble.data

import com.google.gson.annotations.SerializedName

/**
 * @file AuthTokenDataModel
 * @date 11/25/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/25/2023.
 */
data class AuthTokenDataModel(
    @SerializedName("grant_type") var grantType: String,
    @SerializedName("email") var email: String,
    @SerializedName("password") var password: String,
    @SerializedName("client_id") var clientId: String,
    @SerializedName("client_secret") var clientSecret: String
)