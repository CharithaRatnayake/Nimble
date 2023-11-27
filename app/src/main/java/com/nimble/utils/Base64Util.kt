package com.nimble.utils

import android.util.Base64

/**
 * @file AESEncryptUtil
 * @date 11/27/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/27/2023.
 */
class Base64Util {

    companion object {

        fun stringToBase64(input: String): String {
            val bytes = input.toByteArray(Charsets.UTF_8)
            return Base64.encodeToString(bytes, Base64.DEFAULT)
        }

        fun base64ToString(base64String: String): String {
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            return String(decodedBytes, Charsets.UTF_8)
        }

    }
}