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

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.ddanddan.data.HealthServicesRepository
import com.ddanddan.data.PassiveDataRepository
import com.ddanddan.watch.TAG
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

/**
 * Background data subscriptions are not persisted across device restarts. This receiver checks if
 * we enabled background data and, if so, registers again.
 */
class StartupReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val repository = PassiveDataRepository(context)
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return

        runBlocking {
            if (repository.passiveDataEnabled.first()) {
                // ACTIVITY_RECOGNITION 권한만 확인
                val activityRecognitionPermission =
                    context.checkSelfPermission(android.Manifest.permission.ACTIVITY_RECOGNITION)

                if (activityRecognitionPermission == PackageManager.PERMISSION_GRANTED) {
                    // 권한이 허용된 경우 작업을 스케줄링
                    scheduleWorker(context)
                } else {
                    // 권한이 없는 경우 데이터를 비활성화
                    repository.setPassiveDataEnabled(false)
                }
            }
        }
    }

    private fun scheduleWorker(context: Context) {
        // BroadcastReceiver의 onReceive는 10초 이내에 완료되어야 합니다.
        Log.i(TAG, "Enqueuing worker")
        WorkManager.getInstance(context).enqueue(
            OneTimeWorkRequestBuilder<RegisterForBackgroundDataWorker>().build()
        )
    }
}


class RegisterForBackgroundDataWorker(
    private val appContext: Context,
    workerParams: WorkerParameters
) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        Log.i(TAG, "Worker running")
        val healthServicesRepository = HealthServicesRepository(appContext)
        healthServicesRepository.registerForCaloriesData()
        return Result.success()
    }
}
