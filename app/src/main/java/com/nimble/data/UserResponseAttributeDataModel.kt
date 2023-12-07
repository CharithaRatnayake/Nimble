package com.nimble.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @file UserResponseObjDataModel
 * @date 11/25/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/25/2023.
 */
@Keep
data class UserResponseAttributeDataModel(
    @SerializedName("attributes") var attributes: UserDataModel = UserDataModel()
)
