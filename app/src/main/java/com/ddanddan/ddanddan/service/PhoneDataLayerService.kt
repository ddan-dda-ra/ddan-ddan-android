package com.ddanddan.ddanddan.service


import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.Build.VERSION_CODES
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.ddanddan.ddanddan.R
import com.ddanddan.domain.repository.UserRepository
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.Wearable
import com.google.android.gms.wearable.WearableListenerService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class PhoneDataLayerService : WearableListenerService() {
    @Inject
    lateinit var userRepository: UserRepository

    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val tag = "kangmi"
    private val messageClient by lazy { Wearable.getMessageClient(this) }
    private val nodeClient by lazy { Wearable.getNodeClient(this) }

    override fun onCreate() {
        super.onCreate()

        val hasPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.BODY_SENSORS
        ) == PackageManager.PERMISSION_GRANTED

        if (hasPermission) {
            startForegroundService()
            requestLatestCaloriesFromWatch()
        } else {
            stopSelf()  // 권한 없으면 바로 종료
        }
    }

    @androidx.annotation.RequiresApi(VERSION_CODES.UPSIDE_DOWN_CAKE)
    private fun startForegroundServiceUpsideDownCake(notification: android.app.Notification) {
        // FOREGROUND_SERVICE_TYPE_HEALTH = 0x00004000 (Android 14+)
        startForeground(1, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_HEALTH)
    }

    private fun startForegroundService() {
        val channel = NotificationChannel(
            "data_layer_channel",
            "Data Layer Service",
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = "Handles data sync with watch"
        }

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(this, "data_layer_channel")
            .setContentTitle("데이터 동기화 중")
            .setContentText("워치와 데이터를 동기화하고 있습니다.")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .build()

        if (Build.VERSION.SDK_INT >= VERSION_CODES.UPSIDE_DOWN_CAKE) {
            startForegroundServiceUpsideDownCake(notification)
        } else {
            startForeground(1, notification)
        }
    }

    override fun onDataChanged(dataEvents: DataEventBuffer) {
        super.onDataChanged(dataEvents)
        
        for (event in dataEvents) {
            if (event.type == DataEvent.TYPE_CHANGED) {
                when (event.dataItem.uri.path) {
                    "/calories_data" -> {
                        val dataMap = DataMapItem.fromDataItem(event.dataItem).dataMap
                        val calories = dataMap.getDouble("calories")
                        val timestamp = dataMap.getLong("timestamp")

                        Timber.tag(tag).d("Received calories data: $calories kcal at $timestamp")

                        serviceScope.launch {
                            try {
                                userRepository.saveCalories(calories, timestamp)
                                Timber.tag(tag).d("Calories data saved successfully")
                            } catch (e: Exception) {
                                Timber.tag(tag).e(e, "Error saving calories data")
                            }
                        }
                    }
                }
            }
        }
    }
    
    override fun onMessageReceived(messageEvent: MessageEvent) {
        Timber.tag(tag).d("Message received from watch: ${messageEvent.path}")
        when (messageEvent.path) {
            "/request_calories" -> {
                // 워치에서 데이터 요청을 받으면 처리
                requestLatestCaloriesFromWatch()
            }
            "/request_user_info" -> {
                // 워치에서 유저 정보 요청을 받으면 처리
                Timber.tag(tag).d("Received user info request from watch")
                sendUserInfoToWatch()
            }
        }
    }
    
    private fun requestLatestCaloriesFromWatch() {
        Timber.tag(tag).d("Requesting latest calories from watch")
        serviceScope.launch {
            try {
                nodeClient.connectedNodes.addOnSuccessListener { nodes ->
                    nodes.forEach { node ->
                        messageClient.sendMessage(
                            node.id,
                            "/request_latest_calories",
                            byteArrayOf()
                        ).addOnSuccessListener {
                            Timber.tag(tag).d("Successfully requested latest calories from watch")
                        }.addOnFailureListener { e ->
                            Timber.tag(tag).e(e, "Failed to request calories from watch")
                        }
                    }
                }.addOnFailureListener {
                    Timber.tag(tag).e("Error requesting calories from watch")
                }
            } catch (e: Exception) {
                Timber.tag(tag).e(e, "Error requesting calories from watch")
            }
        }
    }
    
    private fun sendUserInfoToWatch() {
        serviceScope.launch {
            try {
                // 유저 정보 가져오기
                val user = userRepository.getUser()
                val pet = userRepository.getMainPet()

                Timber.tag(tag).d("Sending user info to watch: $user, $pet")
                
                // JSON 형식으로 데이터 생성
                val jsonData = """
                    {
                        "pet_type": "${pet.type.name}",
                        "pet_level": ${pet.level},
                        "target_calories": ${user.purposeCalorie}
                    }
                """.trimIndent()

                Timber.tag(tag).d("Sending JSON data: $jsonData")

                // 연결된 모든 노드(워치)에 메시지 전송
                nodeClient.connectedNodes.addOnSuccessListener {
                    it.forEach { node ->
                        messageClient.sendMessage(
                            node.id,
                            "/user_info",
                            jsonData.toByteArray()
                        ).addOnSuccessListener {
                            Timber.tag(tag).d("Message sent successfully to ${node.displayName}")
                        }.addOnFailureListener { e ->
                            Timber.tag(tag).e(e, "Failed to send message to ${node.displayName}")
                        }
                    }
                }
            } catch (e: Exception) {
                Timber.tag(tag).e(e, "Error sending user info to watch")
            }
        }
    }
}
