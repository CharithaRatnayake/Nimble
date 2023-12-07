package com.nimble.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @file TokenAttributes
 * @date 11/26/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/26/2023.
 */
@Keep
data class TokenAttributes(
    @SerializedName("access_token")
    val accessToken: String = "",
    @SerializedName("token_type")
    val tokenType: String = "",
    @SerializedName("expires_in")
    val expiresIn: Long = 0,
    @SerializedName("refresh_token")
    val refreshToken: String = "",
    @SerializedName("created_at")
    val createdAt: Long = 0
)
