package com.nimble.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @file UserDataModel
 * @date 11/25/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/25/2023.
 */
@Keep
data class UserResponseDataModel(
    @SerializedName("data") var userDataModel: UserResponseAttributeDataModel = UserResponseAttributeDataModel()
)
