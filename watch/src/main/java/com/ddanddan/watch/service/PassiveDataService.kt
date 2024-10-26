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
package com.ddanddan.watch.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.health.services.client.PassiveListenerService
import androidx.health.services.client.data.DataPointContainer
import androidx.health.services.client.data.DataType
import com.ddanddan.data.PassiveDataRepository
import com.ddanddan.data.latestCalories
import com.ddanddan.watch.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Service to receive data from Health Services.
 *
 * Passive data is delivered from Health Services to this service. Override the appropriate methods
 * in [PassiveListenerService] to receive updates for new data points, goals achieved etc.
 */
class PassiveDataService : PassiveListenerService(), CoroutineScope {

    private val job = Job()  // 코루틴 작업을 관리할 Job 객체
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job  // IO 스레드에서 코루틴을 실행

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "PassiveDataService started")

        // 포어그라운드 서비스 시작
        val notification = createNotification() // 알림을 생성하는 함수
        startForeground(1, notification)

        // 1초마다 로그 출력하는 작업 시작
        startLoggingTask()
    }

    // 1초 간격으로 로그를 찍는 함수
    private fun startLoggingTask() {
        launch {
            while (isActive) {  // 코루틴이 활성화되어 있는 동안 반복
                Log.d("PassiveDataService", "Service is running...")
                delay(1000L)  // 1초 대기
            }
        }
    }

    // 알림을 생성하는 함수
    private fun createNotification(): Notification {
        val notificationChannelId = "PASSIVE_DATA_CHANNEL"
        val channel = NotificationChannel(
            notificationChannelId,
            "Passive Data Service",
            NotificationManager.IMPORTANCE_LOW  // 중요도 설정
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)

        return NotificationCompat.Builder(this, notificationChannelId)
            .setContentTitle("Passive Data Service")
            .setContentText("Tracking your daily calories...")
            .setSmallIcon(coil.compose.base.R.drawable.ic_100tb)
            .build()
    }

    override fun onNewDataPointsReceived(dataPoints: DataPointContainer) {
        val caloriesData = dataPoints.getData(DataType.CALORIES_DAILY)

        caloriesData.latestCalories()?.let { calories ->
            // 데이터 처리 로직: DataStore에 저장
            Log.d("PassiveDataService", "Received calories data: $calories")

            // 서비스의 CoroutineScope에서 비동기 처리
            launch {
                val repository = PassiveDataRepository(applicationContext)
                repository.storeLatestCalories(calories)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("PassiveDataService", "Service stopped")
        job.cancel()  // 서비스가 종료되면 코루틴 작업도 취소
    }

    // 서비스 재시작 설정
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY  // 서비스가 종료되면 다시 시작
    }
}
