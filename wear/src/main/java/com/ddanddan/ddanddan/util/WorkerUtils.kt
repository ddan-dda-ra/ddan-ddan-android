package com.ddanddan.ddanddan.util

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.ddanddan.ddanddan.worker.MidnightWorker
import com.ddanddan.ddanddan.worker.SyncCaloriesWorker
import java.util.Calendar
import java.util.concurrent.TimeUnit

object WorkerUtils {

    // 다음 자정까지 남은 시간을 계산하는 함수
    private fun getMillisUntilNextMidnight(): Long {
        val now = Calendar.getInstance()
        val nextMidnight = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            add(Calendar.DAY_OF_YEAR, 1)
        }
        return nextMidnight.timeInMillis - now.timeInMillis
    }

    // 매일 자정에 실행되도록 설정하는 WorkManager 설정 함수
    fun setupDailyMidnightResetWorker(context: Context) {
        val millisUntilNextMidnight = getMillisUntilNextMidnight() // 자정까지의 남은 시간 계산

        val midnightResetRequest = PeriodicWorkRequestBuilder<MidnightWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(millisUntilNextMidnight, TimeUnit.MILLISECONDS) // 첫 실행을 자정에 맞춤
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.NOT_REQUIRED) // 네트워크 불필요
                    .setRequiresBatteryNotLow(false) // 배터리가 낮은 경우에도 실행
                    .build()
            )
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            MIDNIGHT_RESET_WORKER,
            ExistingPeriodicWorkPolicy.UPDATE, // 기존 작업 유지, 새로운 설정은 다음 주기에 적용
            midnightResetRequest
        )
    }

    fun scheduleSyncCaloriesWorker(context: Context) {
        val workRequest = PeriodicWorkRequestBuilder<SyncCaloriesWorker>(1, TimeUnit.HOURS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED) // 네트워크 연결 필요
                    .setRequiresBatteryNotLow(false) // 배터리가 낮은 경우에도 실행
                    .build()
            )
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            SYNC_CALORIES_WORKER,
            ExistingPeriodicWorkPolicy.UPDATE, // 기존 작업 유지, 새로운 설정은 다음 주기에 적용
            workRequest
        )
    }

    const val SYNC_CALORIES_WORKER = "SyncCaloriesWorker"
    const val MIDNIGHT_RESET_WORKER = "MidnightResetWorker"

}
