package com.ddanddan.domain.repository

import com.ddanddan.domain.entity.AuthInfo
import com.ddanddan.domain.entity.Pet
import com.ddanddan.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUser(): User
    suspend fun putUser(name: String, purposeCalorie: Int): User
    suspend fun deleteUser(cause: String): Boolean
    suspend fun getMainPet(): Pet
    suspend fun postMainPet(petId: String): Pet
    suspend fun postLogin(token: String): AuthInfo
    fun getCaloriesFlow(): Flow<Float>
    suspend fun saveCalories(calories: Double): Unit
    suspend fun patchPushSetting(isOn: Boolean): Boolean
}