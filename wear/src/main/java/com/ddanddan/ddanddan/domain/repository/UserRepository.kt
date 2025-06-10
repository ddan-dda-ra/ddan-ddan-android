package com.ddanddan.ddanddan.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun saveCalories(calories: Double, timestamp: Long)
    fun getTodayCalories(): Flow<Double>
}