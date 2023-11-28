package com.nimble.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
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
        private val DATASTORE_KEY_NAME = stringPreferencesKey(AppConstants.DATASTORE_KEY_NAME)
        private val DATASTORE_KEY_EMAIL = stringPreferencesKey(AppConstants.DATASTORE_KEY_EMAIL)
        private val DATASTORE_KEY_ACCESS_TOKEN = stringPreferencesKey(AppConstants.DATASTORE_KEY_ACCESS_TOKEN)
        private val DATASTORE_KEY_REFRESH_TOKEN = stringPreferencesKey(AppConstants.DATASTORE_KEY_REFRESH_TOKEN)
    }

    suspend fun saveUserData(userName: String, email: String, accessToken: String, refreshToken: String) {
        dataStore.edit { preferences ->
            preferences[DATASTORE_KEY_NAME] = userName
            preferences[DATASTORE_KEY_EMAIL] = email
            preferences[DATASTORE_KEY_ACCESS_TOKEN] = accessToken
            preferences[DATASTORE_KEY_REFRESH_TOKEN] = refreshToken
        }
    }

    val email: Flow<String?> = dataStore.data.map { preferences ->
        preferences[DATASTORE_KEY_EMAIL]
    }
}