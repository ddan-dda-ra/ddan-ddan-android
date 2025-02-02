package com.ddanddan.ddanddan.service

import android.content.ContentValues.TAG
import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ddanddan.ddanddan.domain.repository.HealthServicesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

@HiltWorker
class RegisterForBackgroundDataWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val healthServicesRepository: HealthServicesRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        Timber.tag(TAG).i("Worker running")
        healthServicesRepository.registerForCaloriesData()
        return Result.success()
    }
}
