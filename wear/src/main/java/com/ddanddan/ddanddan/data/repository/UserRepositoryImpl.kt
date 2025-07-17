package com.ddanddan.ddanddan.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import com.ddanddan.ddanddan.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Calendar
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserRepository {
    override suspend fun saveCalories(calories: Double, timestamp: Long) {
        dataStore.edit { preferences ->
            preferences[CALORIES_KEY] = calories
            preferences[LAST_UPDATED_KEY] = timestamp
        }
    }

    override fun getTodayCalories(): Flow<Double> {
        return dataStore.data.map { preferences ->
            val lastUpdated = preferences[LAST_UPDATED_KEY] ?: 0L
            val todayStart = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis

            if (lastUpdated >= todayStart) {
                preferences[CALORIES_KEY] ?: 0.0
            } else {
                0.0
            }
        }
    }

    companion object {
        private val CALORIES_KEY = doublePreferencesKey("daily_calories")
        private val LAST_UPDATED_KEY = longPreferencesKey("last_updated")
    }
}


