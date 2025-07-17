package com.ddanddan.ddanddan.presentation.kangmin

import android.app.ActivityManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.health.services.client.PassiveListenerService
import androidx.health.services.client.data.DataPointContainer
import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.IntervalDataPoint
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.domain.repository.PassiveDataRepository
import com.ddanddan.ddanddan.util.WatchToPhoneDataSender
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
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
    
    @Inject
    lateinit var watchToPhoneDataSender: WatchToPhoneDataSender
    
    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        // Move startForeground to onStartCommand

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.tag("kangmi")
            .d("onStartCommand called with intent: ${intent?.action}, startId: $startId")
        
        return try {
            // 이미 포그라운드 서비스로 실행 중인지 확인
            if (!isServiceRunningInForeground()) {
                Timber.tag("kangmi").d("Starting foreground service")
                startForeground(NOTIFICATION_ID, createNotification())
                Timber.tag("kangmi").d("Foreground service started successfully")
            } else {
                Timber.tag("kangmi").d("Service is already running in foreground")
            }
            
            // 서비스가 종료되더라도 시스템이 다시 시작하도록 함
            START_STICKY
            
        } catch (e: Exception) {
            Timber.tag("kangmi").e(e, "Error in onStartCommand")
            // 오류 발생 시에도 시스템이 서비스를 다시 시작할 수 있도록 함
            START_STICKY
        }
    }
    
    private fun isServiceRunningInForeground(): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
            ?: return false
            
        return try {
            manager.getRunningServices(Integer.MAX_VALUE).any { 
                it.service.className == this::class.java.name && it.foreground 
            }
        } catch (e: Exception) {
            Timber.tag("kangmi").e(e, "Error checking if service is in foreground")
            false
        }
    }
    


    // 수집한 칼로리 수신
    override fun onNewDataPointsReceived(dataPoints: DataPointContainer) {
        Timber.tag("kangmi").d("onNewDataPointsReceived called with dataPoints: $dataPoints")
        
        try {
            val caloriesData = dataPoints.getData(DataType.CALORIES_DAILY)
            Timber.tag("kangmi").d("Calories data received: $caloriesData")
            
            if (caloriesData.isEmpty()) {
                Timber.tag("kangmi").w("No calories data found in the data points")
                return
            }
            
            val latestCalories = caloriesData.latestCalories()
            Timber.tag("kangmi").d("Latest calories: $latestCalories")
            
            if (latestCalories != null) {
                serviceScope.launch {
                    try {
                        Timber.tag("kangmi").d("Storing latest calories: $latestCalories")
                        // 로컬에 저장
                        passiveDataRepository.storeLatestCalories(latestCalories)
                        
                        // 스마트폰으로 전송
                        Timber.tag("kangmi").d("Sending calories to phone: $latestCalories")
                        watchToPhoneDataSender.sendCalories(
                            calories = latestCalories,
                            timestamp = System.currentTimeMillis()
                        )
                        Timber.tag("kangmi").d("Successfully sent calories to phone")
                    } catch (e: Exception) {
                        Timber.tag("kangmi").e(e, "Failed to process or send calories data")
                    }
                }
            } else {
                Timber.tag("kangmi").w("Latest calories is null")
            }
        } catch (e: Exception) {
            Timber.tag("kangmi").e(e, "Error in onNewDataPointsReceived")
        }
    }


    private fun createNotification(): Notification {
        createNotificationChannel()

        val intent = packageManager.getLaunchIntentForPackage(packageName)?.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("활동 추적 중")
            .setContentText("칼로리 소모량을 모니터링하고 있습니다.")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "활동 추적",
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = "칼로리 추적을 위한 채널"
        }

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    override fun onDestroy() {
        super.onDestroy()
        setServiceRunning(false)
        Timber.tag("kangmi").d("Service stopped")
    }

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "activity_tracking_channel"
        
        @Volatile
        private var isServiceRunning = false
        private val serviceLock = Any()

        fun startService(context: Context) {
            synchronized(serviceLock) {
                if (isServiceRunning) {
                    Timber.tag("kangmi").d("Service is already running, not starting again")
                    return
                }

                Timber.tag("kangmi").d("Starting service...")
                val serviceIntent = Intent(context, PassiveDataService::class.java)
                try {
                    context.startForegroundService(serviceIntent)
                    isServiceRunning = true
                    Timber.tag("kangmi").d("Service start requested")
                } catch (e: Exception) {
                    Timber.tag("kangmi").e(e, "Failed to start service")
                    isServiceRunning = false
                }
            }
        }
        
        private fun setServiceRunning(running: Boolean) {
            synchronized(serviceLock) {
                isServiceRunning = running
            }
        }
    }
}

fun List<IntervalDataPoint<Double>>.latestCalories(): Double? {
    return this
        .filter { it.value > 0 }
        .maxByOrNull { it.endDurationFromBoot }?.value  // 가장 최신 데이터를 가져옴
}
