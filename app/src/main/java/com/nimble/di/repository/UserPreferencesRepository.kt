package com.nimble.di.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.nimble.base.AppConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @file UserPreferences
 * @date 11/26/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/26/2023.
 */
class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        private val DATASTORE_KEY_IS_LOGGED =
            booleanPreferencesKey(AppConstants.DATASTORE_KEY_IS_LOGGED)
        private val DATASTORE_KEY_EXPIRE_IN =
            longPreferencesKey(AppConstants.DATASTORE_KEY_EXPIRE_IN)
        private val DATASTORE_KEY_ACCESS_TOKEN =
            stringPreferencesKey(AppConstants.DATASTORE_KEY_ACCESS_TOKEN)
        private val DATASTORE_KEY_REFRESH_TOKEN =
            stringPreferencesKey(AppConstants.DATASTORE_KEY_REFRESH_TOKEN)
    }

    suspend fun saveUserData(
        isLogged: Boolean, expireIn: Long, accessToken: String, refreshToken: String
    ) {
        Log.d(
            javaClass.simpleName,
            "saveUserData: isLogged:$isLogged | expireIn:$expireIn | accessToken:$accessToken | refreshToken:$refreshToken | "
        )
        dataStore.edit { preferences ->
            preferences[DATASTORE_KEY_IS_LOGGED] = isLogged
            preferences[DATASTORE_KEY_EXPIRE_IN] = expireIn
            preferences[DATASTORE_KEY_ACCESS_TOKEN] = accessToken
            preferences[DATASTORE_KEY_REFRESH_TOKEN] = refreshToken
        }
    }

    suspend fun clearDataStore() {
        Log.d(javaClass.simpleName, "clear Data Store: ")
        dataStore.edit { it.clear() }
    }

    val isLogged: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[DATASTORE_KEY_IS_LOGGED] == true
    }

    val expireIn: Flow<Long?> = dataStore.data.map { preferences ->
        preferences[DATASTORE_KEY_EXPIRE_IN]
    }

    val refreshToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[DATASTORE_KEY_REFRESH_TOKEN]
    }

    val accessToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[DATASTORE_KEY_ACCESS_TOKEN]
    }

    val authToken: Flow<HashMap<String, String>> = dataStore.data.map { preferences ->
        hashMapOf(
            AppConstants.DATASTORE_KEY_EXPIRE_IN to preferences[DATASTORE_KEY_EXPIRE_IN].toString(),
            AppConstants.DATASTORE_KEY_ACCESS_TOKEN to preferences[DATASTORE_KEY_ACCESS_TOKEN].toString(),
            AppConstants.DATASTORE_KEY_REFRESH_TOKEN to preferences[DATASTORE_KEY_REFRESH_TOKEN].toString()
        )
    }
}