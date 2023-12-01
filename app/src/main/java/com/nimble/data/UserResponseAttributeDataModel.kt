package com.nimble.data

import com.google.gson.annotations.SerializedName

/**
 * @file UserResponseObjDataModel
 * @date 11/25/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/25/2023.
 */
data class UserResponseAttributeDataModel(
    @SerializedName("attributes") var attributes: UserDataModel = UserDataModel()
)
