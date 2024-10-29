package com.ddanddan.watch.service

import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.ddanddan.domain.repository.PassiveDataRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

/**
 * 백그라운드 데이터 구독은 기기 재시작 시 유지되지 않습니다. 이 리시버는 백그라운드 데이터가 활성화되었는지 확인하고, 활성화된 경우 다시 등록합니다.
 */
@AndroidEntryPoint
class StartupReceiver : BroadcastReceiver() {

    @Inject
    lateinit var passiveDataRepository: PassiveDataRepository

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return

        runBlocking {
            if (passiveDataRepository.passiveDataEnabled.first()) {
                val activityRecognitionPermission = context.checkSelfPermission(android.Manifest.permission.ACTIVITY_RECOGNITION)

                if (activityRecognitionPermission == PackageManager.PERMISSION_GRANTED) {
                    scheduleWorker(context)
                } else {
                    passiveDataRepository.setPassiveDataEnabled(false)
                }
            }
        }
    }

    private fun scheduleWorker(context: Context) {
        Timber.tag(TAG).i("Enqueuing worker")
        WorkManager.getInstance(context).enqueue(
            OneTimeWorkRequestBuilder<RegisterForBackgroundDataWorker>().build()
        )
    }
}
