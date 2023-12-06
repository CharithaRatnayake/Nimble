package com.nimble.utils.security

import android.util.Base64
import com.nimble.base.AppConstants
import com.nimble.utils.base64ToString
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * @file AesCipherProvider
 * @date 12/6/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 12/6/2023.
 */
object AesCipherProvider {

    private const val AES_ALGORITHM = AppConstants.AES_ALGORITHM
    private const val AES_TRANSFORMATION = AppConstants.AES_TRANSFORMATION

    // Replace this key and IV with your own secure values
    private const val SECRET_KEY = AppConstants.AES_SECRET_KEY // 16 characters for AES-128
    private const val INITIALIZATION_VECTOR =
        AppConstants.AES_INITIALIZATION_VECTOR // 16 characters for AES-128

    // Function to encrypt data using AES
    fun encrypt(data: String): String {

        return try {
            val secretKey =
                SecretKeySpec(String().base64ToString(SECRET_KEY).toByteArray(), AES_ALGORITHM)
            val cipher = Cipher.getInstance(AES_TRANSFORMATION)
            cipher.init(
                Cipher.ENCRYPT_MODE,
                secretKey,
                IvParameterSpec(String().base64ToString(INITIALIZATION_VECTOR).toByteArray())
            )

            val encryptedBytes = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
            Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
        } catch (ex: Exception) {
            ""
        }
    }

    // Function to decrypt data using AES
    fun decrypt(encryptedData: String): String {
        return try {
            val secretKey =
                SecretKeySpec(String().base64ToString(SECRET_KEY).toByteArray(), AES_ALGORITHM)
            val cipher = Cipher.getInstance(AES_TRANSFORMATION)
            cipher.init(
                Cipher.DECRYPT_MODE,
                secretKey,
                IvParameterSpec(String().base64ToString(INITIALIZATION_VECTOR).toByteArray())
            )

            val decryptedBytes = cipher.doFinal(Base64.decode(encryptedData, Base64.DEFAULT))
            String(decryptedBytes, Charsets.UTF_8)
        } catch (ex: Exception) {
            ""
        }
    }
}