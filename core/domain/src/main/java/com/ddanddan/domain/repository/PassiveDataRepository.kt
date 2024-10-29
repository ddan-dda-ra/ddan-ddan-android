package com.ddanddan.domain.repository

import kotlinx.coroutines.flow.Flow

interface PassiveDataRepository {
    val passiveDataEnabled: Flow<Boolean>
    suspend fun setPassiveDataEnabled(enabled: Boolean)

    val latestCalories: Flow<Double>
    suspend fun storeLatestCalories(calories: Double)
}