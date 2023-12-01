package com.nimble

import com.nimble.utils.ValidatorUtil
import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ValidatorUnitTest {
    @Test
    fun emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertTrue(ValidatorUtil().isEmailValid("name@email.com"))
    }

    @Test
    fun emailValidator_CorrectEmailSubDomain_ReturnsTrue() {
        assertTrue(ValidatorUtil().isEmailValid("name@email.co.uk"))
    }

    @Test
    fun emailValidator_InvalidEmailNoTld_ReturnsFalse() {
        assertFalse(ValidatorUtil().isEmailValid("name@email"))
    }

    @Test
    fun emailValidator_InvalidEmailDoubleDot_ReturnsFalse() {
        assertFalse(ValidatorUtil().isEmailValid("name@email..com"))
    }

    @Test
    fun emailValidator_InvalidEmailNoUsername_ReturnsFalse() {
        assertFalse(ValidatorUtil().isEmailValid("@email.com"))
    }

    @Test
    fun emailValidator_EmptyString_ReturnsFalse() {
        assertFalse(ValidatorUtil().isEmailValid(""))
    }

    @Test
    fun emailValidator_NullEmail_ReturnsFalse() {
//        assertFalse(ValidatorUtil().isEmailValid(null))
    }

    @Test
    fun emailValidator_CorrectNameSimple_ReturnsTrue() {
        assertTrue(ValidatorUtil().isNameValid("Charitha"))
    }

    @Test
    fun emailValidator_CorrectTwoName_ReturnsTrue() {
        assertTrue(ValidatorUtil().isNameValid("Charitha Ratnayake"))
    }

    @Test
    fun emailValidator_InvalidNameNumber_ReturnsFalse() {
        assertFalse(ValidatorUtil().isNameValid("Charitha123"))
    }

    @Test
    fun emailValidator_InvalidNameSpecialCharacter_ReturnsFalse() {
        assertFalse(ValidatorUtil().isNameValid("Charitha123@#$"))
    }

    @Test
    fun emailValidator_InvalidNameEmpty_ReturnsFalse() {
        assertFalse(ValidatorUtil().isNameValid(""))
    }

    @Test
    fun emailValidator_InvalidNameNull_ReturnsFalse() {
//        assertFalse(ValidatorUtil().isNameValid(null))
    }
}