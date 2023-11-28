package com.nimble.data

import com.google.gson.annotations.SerializedName
import com.nimble.data.http.ErrorBody

/**
 * @file LoginResponseDataModel
 * @date 11/26/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/26/2023.
 */
data class LoginResponseDataModel(
    @SerializedName("data") var data: TokenResponse = TokenResponse(),
    @SerializedName("errors") var errors: ArrayList<ErrorBody>? = arrayListOf()
)
