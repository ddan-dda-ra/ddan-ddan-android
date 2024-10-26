/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ddanddan.data

import android.content.Context
import android.util.Log
import androidx.concurrent.futures.await
import androidx.health.services.client.HealthServices
import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.PassiveListenerConfig

/**
 * Entry point for [HealthServicesClient] APIs. This also provides suspend functions around
 * those APIs to enable use in coroutines.
 */
class HealthServicesRepository(context: Context) {
    private val healthServicesClient = HealthServices.getClient(context)
    private val passiveMonitoringClient = healthServicesClient.passiveMonitoringClient
    private val dataTypes = setOf(DataType.CALORIES_DAILY)

    private val passiveListenerConfig = PassiveListenerConfig(
        dataTypes = dataTypes,
        shouldUserActivityInfoBeRequested = true, //사용자 활동 정보를 수집하기 위한 요청, 칼로리 수집에 필요한 것인지는 모르겠음.
        dailyGoals = setOf(),
        healthEventTypes = setOf()
    )

    suspend fun hasCaloriesCapability(): Boolean {
        val capabilities = passiveMonitoringClient.getCapabilitiesAsync().await() //디바이스에서 지원 가능한 DataType 확인
        return DataType.CALORIES_DAILY in capabilities.supportedDataTypesPassiveMonitoring //백그라운드에서 자동 수집 가능한 데이터 타입
    }

    suspend fun registerForCaloriesData() {
        Log.i(TAG, "Registering listener")
        passiveMonitoringClient.setPassiveListenerServiceAsync(
            PassiveDataService::class.java,
            passiveListenerConfig
        ).await()
    }

    suspend fun unregisterForCaloriesData() {
        Log.i(TAG, "Unregistering listeners")
        passiveMonitoringClient.clearPassiveListenerServiceAsync().await()
    }
}
