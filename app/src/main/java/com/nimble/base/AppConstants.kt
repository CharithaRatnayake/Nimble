package com.nimble.base

/**
 * @file AppConstants
 * @date 11/26/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/26/2023.
 */
class AppConstants {

    companion object {

        //Http
        const val HTTP_CONNECT_TIMEOUT = 30L
        const val HTTP_READ_TIMEOUT = 30L
        const val HTTP_WRITE_TIMEOUT = 30L

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
        const val APP_DATABASE_NAME = "NimbleDatabase"
        const val DATASTORE_KEY_IS_LOGGED = "isLogged"
        const val DATASTORE_KEY_EXPIRE_IN = "expire_in"
        const val DATASTORE_KEY_ACCESS_TOKEN = "access_token"
        const val DATASTORE_KEY_REFRESH_TOKEN = "refresh_token"

        //app
        const val CLIENT_ID = "NkdiRThkaG96NTE5bDJOX0Y5OVN0cW9PczZUY21tMXJYZ2RhNHFfX3JJdw=="
        const val CLIENT_SECRET = "X2F5ZkltN0JlVUFoeDJXMU9VcWkyMGZ3TzN1TnhmbzFRc3R5S2xGQ2dIdw=="
    }
}