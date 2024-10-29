package com.ddanddan.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import com.ddanddan.domain.repository.PassiveDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PassiveDataRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PassiveDataRepository {
    override val passiveDataEnabled: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[PASSIVE_DATA_ENABLED] ?: false
    }

    override suspend fun setPassiveDataEnabled(enabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[PASSIVE_DATA_ENABLED] = enabled
        }
    }

    override val latestCalories: Flow<Double> = dataStore.data.map { prefs ->
        prefs[LATEST_CALORIES] ?: 0.0
    }

    override suspend fun storeLatestCalories(calories: Double) {
        dataStore.edit { prefs ->
            prefs[LATEST_CALORIES] = calories
        }
    }

    //todo - 유틸 클래스로 분리
    companion object {
        private val PASSIVE_DATA_ENABLED = booleanPreferencesKey("passive_data_enabled")
        private val LATEST_CALORIES = doublePreferencesKey("latest_calories")
    }
}
