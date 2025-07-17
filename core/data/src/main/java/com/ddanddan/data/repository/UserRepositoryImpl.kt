package com.ddanddan.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import com.ddanddan.data.datasource.remote.RemoteUserDataSource
import com.ddanddan.domain.entity.Pet
import com.ddanddan.domain.entity.User
import com.ddanddan.domain.ddanddanDataStore
import com.ddanddan.domain.entity.AuthInfo
import com.ddanddan.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Calendar
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: RemoteUserDataSource,
    private val ddanddanDataStore: ddanddanDataStore,
    private val dataStore: DataStore<Preferences>
) : UserRepository {
    override suspend fun getUser(): User {
        return userDataSource.getUser().toUser()
    }

    override suspend fun putUser(name: String, purposeCalorie: Int): User {
        return userDataSource.putUser(name, purposeCalorie).toUser()
    }

    override suspend fun deleteUser(cause: String): Boolean {
        return userDataSource.deleteUser(cause)
    }

    override suspend fun getMainPet(): Pet {
        return userDataSource.getMainPet().mainPet.toPet()
    }

    override suspend fun postMainPet(petId: String): Pet {
        return userDataSource.postMainPet(petId).mainPet.toPet()
    }

    override suspend fun postLogin(token: String, deviceToken: String?): AuthInfo {
        val result = userDataSource.postLogin(token, deviceToken)
        val bearerAccessToken = "Bearer ${result.accessToken}"
        val bearerRefreshToken = "Bearer ${result.refreshToken}"

        ddanddanDataStore.run {
            userToken = bearerAccessToken
            refreshToken = bearerRefreshToken
        }

        return AuthInfo(bearerAccessToken, bearerRefreshToken, result.isOnboardingComplete)
    }

    override fun getCaloriesFlow(): Flow<Float> = ddanddanDataStore.caloriesFlow

    override suspend fun saveCalories(calories: Double) {
        ddanddanDataStore.calories = calories.toFloat()
    }

    override suspend fun patchPushSetting(isOn: Boolean): Boolean = userDataSource.patchPushSetting(isOn).isAppPushOn

    override suspend fun patchDailyCalories(calorie: Int) {
        userDataSource.patchDailyCalories(calorie)
    }

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