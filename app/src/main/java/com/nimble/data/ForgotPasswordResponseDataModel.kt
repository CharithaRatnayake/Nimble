package com.nimble.data

import com.google.gson.annotations.SerializedName

/**
 * @file ForgotPasswordResponseDataModel
 * @date 11/26/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/26/2023.
 */
data class ForgotPasswordResponseDataModel(
    @SerializedName("meta") var meta: MetaDataModel = MetaDataModel()
)
