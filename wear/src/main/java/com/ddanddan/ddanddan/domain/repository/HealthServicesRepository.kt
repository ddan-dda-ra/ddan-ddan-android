package com.ddanddan.ddanddan.domain.repository

interface HealthServicesRepository {
    suspend fun hasCaloriesCapability(): Boolean
    suspend fun registerForCaloriesData()
    suspend fun unregisterForCaloriesData()
}