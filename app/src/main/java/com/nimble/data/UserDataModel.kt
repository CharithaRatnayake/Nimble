package com.nimble.data

import com.google.gson.annotations.SerializedName

/**
 * @file UserDataModel
 * @date 11/25/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/25/2023.
 */
data class UserDataModel(
    @SerializedName("email") var email: String = "",
    @SerializedName("name") var name: String = "",
    @SerializedName("avatar_url") var avatarUrl: String = "",
    @SerializedName("password") var password: String = "",
    @SerializedName("password_confirmation") var passwordConfirmation: String = ""
)
