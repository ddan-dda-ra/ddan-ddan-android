package com.ddanddan.ddanddan.worker

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ddanddan.ddanddan.util.PreferencesKeys
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

@HiltWorker
class MidnightWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val dataStore: DataStore<Preferences>
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            dataStore.edit { prefs ->
                prefs[PreferencesKeys.TOTAL_CALORIES] = 0.0 // 누적 칼로리 초기화
            }
            Timber.d("Calories reset to 0 at midnight.")
            Result.success()
        } catch (e: Exception) {
            Timber.e(e, "Failed to reset calories at midnight.")
            Result.retry()
        }
    }
}
