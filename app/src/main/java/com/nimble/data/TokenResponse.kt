package com.nimble.data

import androidx.annotation.Keep

/**
 * @file TokenResponse
 * @date 11/26/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/26/2023.
 */
@Keep
data class TokenResponse(
    val id: String = "",
    val type: String = "",
    val attributes: TokenAttributes = TokenAttributes()
)
