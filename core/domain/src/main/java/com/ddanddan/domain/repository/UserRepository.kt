package com.ddanddan.domain.repository

import com.ddanddan.domain.entity.Pet
import com.ddanddan.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUser(): User
    suspend fun putUser(name: String, purposeCalorie: Int): User
    suspend fun deleteUser(): Boolean
    suspend fun getMainPet(): Pet
    suspend fun postMainPet(petId: String): Pet
    suspend fun login(token: String): Result<Boolean>
    fun getCaloriesFlow(): Flow<Float>
    suspend fun saveCalories(calories: Double): Unit
}