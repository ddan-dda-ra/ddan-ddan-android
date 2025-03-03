package com.ddanddan.ddanddan.service

import android.content.ContentValues
import androidx.health.services.client.PassiveListenerService
import androidx.health.services.client.data.DataPointContainer
import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.IntervalDataPoint
import com.ddanddan.ddanddan.domain.repository.PassiveDataRepository
import com.ddanddan.ddanddan.util.WatchToPhoneUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

/**
 * Health Services로부터 데이터를 수신하는 서비스입니다.
 * Health Services에서 전달되는 패시브 데이터를 이 서비스에서 수신합니다. 새로운 데이터 포인트, 목표 달성 등의 업데이트를 받으려면 [PassiveListenerService]의 적절한 메서드를 재정의하세요.
 */
@AndroidEntryPoint
class PassiveDataService : PassiveListenerService() {

    @Inject
    lateinit var passiveDataRepository: PassiveDataRepository

    override fun onCreate() {
        super.onCreate()
        Timber.tag(ContentValues.TAG).i("PassiveDataService started")
    }

    // 수집한 칼로리 수신
    override fun onNewDataPointsReceived(dataPoints: DataPointContainer) {
        val caloriesData = dataPoints.getData(DataType.CALORIES_DAILY)

        caloriesData.latestCalories()?.let { calories ->
            runBlocking {
                passiveDataRepository.storeLatestCalories(calories)

                WatchToPhoneUtils.checkAndSendData(this@PassiveDataService, calories)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.tag("PassiveDataService").d("Service stopped")
    }
}

fun List<IntervalDataPoint<Double>>.latestCalories(): Double? {
    return this
        .filter { it.value > 0 }
        .maxByOrNull { it.endDurationFromBoot }?.value  // 가장 최신 데이터를 가져옴
}
