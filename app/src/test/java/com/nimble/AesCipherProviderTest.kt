package com.nimble

import com.nimble.utils.security.AesCipherProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

/**
 * @file com.nimble
 * @date 12/6/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 12/6/2023.
 */
class AesCipherProviderTest {

    @Test
    fun testEncryptDecrypt() {
        val data = "Hello, World!"

        val result = AesCipherProvider.encrypt(data)
        val decryptResult = AesCipherProvider.decrypt(result)

        assertEquals(result, decryptResult)
    }

    @Test
    fun testEncryptDecryptEmpty() {
        val data = ""

        val result = AesCipherProvider.encrypt(data)
        val decryptResult = AesCipherProvider.decrypt(result)

        assertEquals(result, decryptResult)
    }

    @Test
    fun testEncryptSuccessFalse() {
        val data = "Hello, World!"

        val result = AesCipherProvider.encrypt(data)

        assertFalse(data == result)
    }
}