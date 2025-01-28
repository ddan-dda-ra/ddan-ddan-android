package com.ddanddan.data.repository

import com.ddanddan.data.datasource.remote.RemoteUserDataSource
import com.ddanddan.domain.entity.Pet
import com.ddanddan.domain.entity.User
import com.ddanddan.domain.ddanddanDataStore
import com.ddanddan.domain.entity.AuthInfo
import com.ddanddan.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: RemoteUserDataSource,
    private val ddanddanDataStore: ddanddanDataStore
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

    override suspend fun postLogin(token: String): AuthInfo {
        val result = userDataSource.postLogin(token)
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
}