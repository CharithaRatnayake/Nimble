package com.nimble.di.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.nimble.base.AppConstants
import com.nimble.utils.security.AesCipherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @file UserPreferencesRepository
 * @date 11/26/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/26/2023.
 */
class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        private val DATASTORE_KEY_AUTHENTICATED =
            booleanPreferencesKey(AppConstants.DATASTORE_KEY_AUTHENTICATED)
        private val DATASTORE_KEY_EXPIRE_IN =
            longPreferencesKey(AppConstants.DATASTORE_KEY_EXPIRE_IN)
        private val DATASTORE_KEY_ACCESS_TOKEN =
            stringPreferencesKey(AppConstants.DATASTORE_KEY_ACCESS_TOKEN)
        private val DATASTORE_KEY_REFRESH_TOKEN =
            stringPreferencesKey(AppConstants.DATASTORE_KEY_REFRESH_TOKEN)
    }

    suspend fun saveUserData(
        authenticated: Boolean, expireIn: Long, accessToken: String, refreshToken: String
    ) {
        Log.d(
            javaClass.simpleName,
            "saveUserData: isLogged:$authenticated | expireIn:$expireIn | accessToken:$accessToken | refreshToken:$refreshToken"
        )

        val encryptedAccessToken = encryptData(accessToken)
        val encryptedRefreshToken = encryptData(refreshToken)

        Log.d(
            javaClass.simpleName,
            "saveUserData encryptData: accessToken:$encryptedAccessToken | refreshToken:$encryptedRefreshToken"
        )

        dataStore.edit { preferences ->
            preferences[DATASTORE_KEY_AUTHENTICATED] = authenticated
            preferences[DATASTORE_KEY_EXPIRE_IN] = expireIn
            preferences[DATASTORE_KEY_ACCESS_TOKEN] = encryptedAccessToken
            preferences[DATASTORE_KEY_REFRESH_TOKEN] = encryptedRefreshToken
        }
    }

    suspend fun clearDataStore() {
        Log.d(javaClass.simpleName, "clear Data Store: ")
        dataStore.edit { it.clear() }
    }

    val isLogged: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[DATASTORE_KEY_AUTHENTICATED] == true
    }

    val accessToken: Flow<String?> = dataStore.data.map { preferences ->
        decryptData(preferences[DATASTORE_KEY_ACCESS_TOKEN])
    }

    val authToken: Flow<HashMap<String, String>> = dataStore.data.map { preferences ->
        val expireIn = preferences[DATASTORE_KEY_EXPIRE_IN].toString()
        val decryptAccessToken = decryptData(preferences[DATASTORE_KEY_ACCESS_TOKEN])
        val decryptRefreshToken = decryptData(preferences[DATASTORE_KEY_REFRESH_TOKEN])

        Log.d(
            javaClass.simpleName,
            "saveUserData decryptData: isLogged:$expireIn | expireIn:$decryptAccessToken | accessToken:$decryptRefreshToken"
        )

        hashMapOf(
            AppConstants.DATASTORE_KEY_EXPIRE_IN to expireIn,
            AppConstants.DATASTORE_KEY_ACCESS_TOKEN to decryptAccessToken,
            AppConstants.DATASTORE_KEY_REFRESH_TOKEN to decryptRefreshToken
        )
    }

    private fun encryptData(data: String): String {
        return AesCipherProvider.encrypt(data)
    }

    private fun decryptData(encryptedData: String?): String {
        return encryptedData?.let { AesCipherProvider.decrypt(encryptedData) } ?: ""
    }
}