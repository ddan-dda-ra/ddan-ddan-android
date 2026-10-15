package com.ddanddan.ddanddan

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import coil.Coil
import coil.ImageLoader
import coil.decode.SvgDecoder
import com.ddanddan.ddanddan.util.WorkerUtils
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class WatchApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()

        Coil.setImageLoader(
            ImageLoader.Builder(this)
                .components { add(SvgDecoder.Factory()) }
                .build()
        )

        WorkerUtils.run {
            setupDailyMidnightResetWorker(this@WatchApp) // 자정에 칼로리 초기화
            scheduleSyncCaloriesWorker(this@WatchApp) // 1시간 간격으로 칼로리 보정
        }
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }
}
