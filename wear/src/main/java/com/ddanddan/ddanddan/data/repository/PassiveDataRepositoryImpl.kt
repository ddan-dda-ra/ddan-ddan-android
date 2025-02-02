package com.ddanddan.ddanddan.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.ddanddan.ddanddan.domain.repository.PassiveDataRepository
import com.ddanddan.ddanddan.util.PreferencesKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PassiveDataRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PassiveDataRepository {

    // todo - 활용 확인
    private val _calorieCollectedSignal = MutableSharedFlow<Unit>(replay = 0)
    override val calorieCollectedSignal: Flow<Unit> get() = _calorieCollectedSignal

    // 패시브 데이터 기능 활성화 여부
    // todo - 활용 확인
    override val passiveDataEnabled: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[PreferencesKeys.PASSIVE_DATA_ENABLED] ?: false
    }

    override suspend fun setPassiveDataEnabled(enabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[PreferencesKeys.PASSIVE_DATA_ENABLED] = enabled
        }
    }

    // todo - 활용 확인
    override val latestCalories: Flow<Double> = dataStore.data.map { prefs ->
        prefs[PreferencesKeys.LATEST_CALORIES] ?: 0.0
    }

    // 새로운 칼로리 데이터를 저장하고 누적 칼로리 값을 업데이트
    override suspend fun storeLatestCalories(calories: Double) {
        dataStore.edit { prefs ->
            val currentTotal = prefs[PreferencesKeys.TOTAL_CALORIES] ?: 0.0
            prefs[PreferencesKeys.LATEST_CALORIES] = calories // 마지막 수집 칼로리 저장
            prefs[PreferencesKeys.TOTAL_CALORIES] = currentTotal + calories // 누적 칼로리 갱신
        }
        _calorieCollectedSignal.emit(Unit)
    }

    override val totalCalories: Flow<Double> = dataStore.data.map { prefs ->
        prefs[PreferencesKeys.TOTAL_CALORIES] ?: 0.0
    }

    //todo - 로직 검증 필요
    override suspend fun getCaloriesToSend(): Double? {
        val totalCalories = totalCalories.first()
        val caloriesToSend = (totalCalories / CALORIES_FEED_UNIT).toInt() * CALORIES_FEED_UNIT

        return caloriesToSend.takeIf { caloriesToSend > 0 }
    }


    companion object {
        const val CALORIES_FEED_UNIT = 100.0 // 100 kcal 당 먹이 1개
    }
}
