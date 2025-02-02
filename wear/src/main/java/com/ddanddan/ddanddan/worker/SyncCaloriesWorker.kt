package com.ddanddan.ddanddan.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ddanddan.ddanddan.data.model.request.RequestDailyCalorie
import com.ddanddan.ddanddan.data.repository.PassiveDataRepositoryImpl.Companion.CALORIES_FEED_UNIT
import com.ddanddan.ddanddan.domain.repository.DdanDdanRepository
import com.ddanddan.ddanddan.domain.repository.PassiveDataRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import timber.log.Timber

@HiltWorker
class SyncCaloriesWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val passiveDataRepository: PassiveDataRepository,
    private val ddanDdanRepository: DdanDdanRepository
) : CoroutineWorker(context, workerParams) {

    /** 로컬과 서버 칼로리 값 동기화
     * ex) 유저 정보를 조회했을 때 먹이가 3개면 서버로 전송된 누적 칼로리는 300이어야 함
     * 만약 로컬엔 400이 있다면 100만큼 누락이 됐다는 것이니 보정을 해주는 것
     * **/
    override suspend fun doWork(): Result {
        return runCatching {
            val totalCalories = passiveDataRepository.totalCalories.first()
            val serverFeedCount = ddanDdanRepository.getUser().foodQuantity
            val serverCalories = serverFeedCount * CALORIES_FEED_UNIT

            if (totalCalories > serverCalories) { // 로컬 누적 칼로리가 서버보다 클 경우, 부족한 만큼 서버에 전송
                val caloriesToSend = (totalCalories / CALORIES_FEED_UNIT).toInt() * CALORIES_FEED_UNIT // 100 단위로 보낼 칼로리 계산
                val requestBody = RequestDailyCalorie(caloriesToSend.toInt())
                ddanDdanRepository.patchDailyCalorie(requestBody)
            }

            Result.success()
        }.getOrElse { e ->
            Timber.e(e, "Error synchronizing calories in SyncCaloriesWorker")
            Result.retry() // 실패 시 재시도 요청
        }
    }
}
