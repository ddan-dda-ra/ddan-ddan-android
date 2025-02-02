package com.ddanddan.ddanddan.util

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val PASSIVE_DATA_ENABLED = booleanPreferencesKey("passive_data_enabled")
    val LATEST_CALORIES = doublePreferencesKey("latest_calories")
    val TOTAL_CALORIES = doublePreferencesKey("total_calories")
    val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
    val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
}
