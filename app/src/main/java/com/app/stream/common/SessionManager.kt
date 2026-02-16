package com.app.stream.common

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "session")

class SessionManager(private val context: Context) {

    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }

    // Save tokens
    suspend fun saveToken(
        accessToken: String,
        refreshToken: String
    ) {
        context.dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN] = accessToken
            prefs[REFRESH_TOKEN] = refreshToken
        }
    }

    // Get access token
    val accessToken: Flow<String?> =
        context.dataStore.data.map { prefs ->
            prefs[ACCESS_TOKEN]
        }

    // Get refresh token
    val refreshToken: Flow<String?> =
        context.dataStore.data.map { prefs ->
            prefs[REFRESH_TOKEN]
        }

    // Clear session (logout)
    suspend fun clearSession() {
        context.dataStore.edit { it.clear() }
    }
}

