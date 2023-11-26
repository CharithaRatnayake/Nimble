package com.nimble.data

/**
 * @file TokenResponse
 * @date 11/26/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/26/2023.
 */
data class TokenResponse(
    val id: String = "",
    val type: String = "",
    val attributes: TokenAttributes = TokenAttributes()
)
