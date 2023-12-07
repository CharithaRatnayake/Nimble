package com.nimble.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @file ForgotPasswordResponseDataModel
 * @date 11/26/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/26/2023.
 */
@Keep
data class ForgotPasswordResponseDataModel(
    @SerializedName("meta") var meta: MetaDataModel = MetaDataModel()
)
