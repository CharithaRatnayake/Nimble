package com.nimble.data

/**
 * @file com.nimble.data
 * @date 11/26/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/26/2023.
 */
data class Resource1<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): Resource1<T> {
            return Resource1(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): Resource1<T> {
            return Resource1(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): Resource1<T> {
            return Resource1(Status.LOADING, data, null)
        }
    }
}