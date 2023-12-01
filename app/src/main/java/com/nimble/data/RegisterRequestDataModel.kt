package com.nimble.data

import com.google.gson.annotations.SerializedName

/**
 * @file RegisterRequestDataModel
 * @date 11/25/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/25/2023.
 */
data class RegisterRequestDataModel(
    @SerializedName("user") var user: UserDataModel
): HeaderDataModel()
