package com.nimble.base

/**
 * @file AppConstants
 * @date 11/26/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/26/2023.
 */
class AppConstants {

    companion object {

        //API paths
        const val API_PATH_REGISTRATION = "registrations"
        const val API_PATH_AUTH_TOKEN = "oauth/token"
        const val API_PATH_LOGOUT = "oauth/revoke"
        const val API_PATH_FORGOT_PASSWORD = "passwords"
        const val API_PATH_GET_USER_PROFILE = "me"
        const val API_PATH_GET_SURVEYS_LIST = "surveys"

        //API query
        const val API_QUERY_PAGE_NUMBER = "page%5Bnumber%5D"
        const val API_QUERY_PAGE_SIZE = "page%5Bsize%5D"

        const val REQUEST_BODY_GRANT_TYPE_PASSWORD = "password"
        const val REQUEST_BODY_GRANT_TYPE_REFRESH_TOKEN = "refresh_token"

        //Data store
        const val APP_DATASTORE_NAME = "com.nimble.user_preferences"
        const val DATASTORE_KEY_NAME = "name"
        const val DATASTORE_KEY_EMAIL = "email"
        const val DATASTORE_KEY_ACCESS_TOKEN = "access_token"
        const val DATASTORE_KEY_REFRESH_TOKEN = "refresh_token"
    }
}