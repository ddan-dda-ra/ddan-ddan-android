package com.ddanddan.data.repository

import android.content.ContentValues.TAG
import android.content.Context
import androidx.concurrent.futures.await
import androidx.health.services.client.HealthServices
import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.PassiveListenerConfig
import com.ddanddan.domain.repository.HealthServicesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

/**
 * [HealthServicesClient] API의 진입 지점입니다. 또한 이러한 API를 코루틴에서 사용할 수 있도록 suspend 함수도 제공합니다.
 */
class HealthServicesRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context
) : HealthServicesRepository {
    private val healthServicesClient = HealthServices.getClient(context)
    private val passiveMonitoringClient = healthServicesClient.passiveMonitoringClient
    private val dataTypes = setOf(DataType.CALORIES_DAILY)

    private val passiveListenerConfig = PassiveListenerConfig(
        dataTypes = dataTypes,
        shouldUserActivityInfoBeRequested = true, //사용자 활동 정보를 수집하기 위한 요청, 칼로리 수집에 필요한 것인지는 불확실 (24/10/19)
        dailyGoals = setOf(),
        healthEventTypes = setOf()
    )

    override suspend fun hasCaloriesCapability(): Boolean {
        val capabilities = passiveMonitoringClient.getCapabilitiesAsync().await() //디바이스에서 지원 가능한 DataType 확인
        return DataType.CALORIES_DAILY in capabilities.supportedDataTypesPassiveMonitoring //백그라운드에서 자동 수집 가능한 데이터 타입 여부 확인
    }

    //백그라운드에서 PassiveDataService가 칼로리 데이터를 수신하도록 설정
    override suspend fun registerForCaloriesData() {
        Timber.i(TAG, "Registering listener")
        passiveMonitoringClient.setPassiveListenerServiceAsync(
            PassiveDataService::class.java,
            passiveListenerConfig
        ).await()
    }

    //더 이상 PassiveDataService가 칼로리 데이터를 수신하지 않도록 리스너 해제
    override suspend fun unregisterForCaloriesData() {
        Timber.i(TAG, "Unregistering listeners")
        passiveMonitoringClient.clearPassiveListenerServiceAsync().await()
    }
}
