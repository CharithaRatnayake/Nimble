package com.nimble.data

import com.google.gson.annotations.SerializedName

/**
 * @file MetaDataModel
 * @date 11/26/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/26/2023.
 */
data class MetaDataModel(
    @SerializedName("message" ) var message : String = ""
)