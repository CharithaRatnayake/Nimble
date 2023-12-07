package com.nimble.data

import androidx.annotation.Keep
import com.nimble.base.AppConstants

/**
 * @file GrantType
 * @date 11/26/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/26/2023.
 */
@Keep
enum class GrantType(val value: String) {
    PASSWORD(AppConstants.REQUEST_BODY_GRANT_TYPE_PASSWORD),
    REFRESH_TOKEN(AppConstants.REQUEST_BODY_GRANT_TYPE_REFRESH_TOKEN)
}