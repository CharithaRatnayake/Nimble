package com.nimble.utils

/**
 * @file ValidatorUtil
 * @date 11/26/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/26/2023.
 */
class ValidatorUtil {

    fun isNameValid(name: String): Boolean {
        val nameRegex = Regex("^([^\\p{N}\\p{S}\\p{C}\\\\/]{2,20})\$")
        return nameRegex.matches(name)
    }

    fun isEmailValid(email: String): Boolean {
        // Simple email validation using a regular expression
        val emailRegex = Regex("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
        return emailRegex.matches(email)
    }

    fun isPasswordValid(password: String): Boolean {
        // Password should have at least 6 characters
        return password.length >= 6
    }

    fun doPasswordsMatch(password: String, retypePassword: String): Boolean {
        // Check if the password and retype password match
        return password == retypePassword
    }

}