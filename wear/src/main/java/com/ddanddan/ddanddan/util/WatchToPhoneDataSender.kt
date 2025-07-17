package com.ddanddan.ddanddan.util

import android.content.Context
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WatchToPhoneDataSender @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataClient: DataClient = Wearable.getDataClient(context)
    private val messageClient: MessageClient = Wearable.getMessageClient(context)
    private val nodeClient = Wearable.getNodeClient(context)
    private val tag = "kangmi"

    fun sendCalories(calories: Double, timestamp: Long) {
        try {
            val request = PutDataMapRequest.create("/calories_data").apply {
                dataMap.putDouble("calories", calories)
                dataMap.putLong("timestamp", timestamp)
                // 7일 후 만료되도록 설정 (선택사항)
                setUrgent()
            }.asPutDataRequest()

            val result = dataClient.putDataItem(request)
            Timber.tag(tag).d("Data sent successfully: $result")
        } catch (e: Exception) {
            Timber.tag(tag).e(e, "Error sending data")
        }
    }
    
    /**
     * 폰에 메시지 전송
     * @param messagePath 전송할 메시지 경로 (예: "/request_user_info")
     * @param data 메시지와 함께 보낼 데이터 (선택사항)
     */
    suspend fun sendMessage(messagePath: String, data: ByteArray? = null) {
        try {
            // 연결된 노드(폰) 가져오기
            val nodes = nodeClient.connectedNodes.await()
            
            if (nodes.isEmpty()) {
                Timber.tag(tag).w("No connected nodes available")
                return
            }
            
            // 모든 연결된 노드에 메시지 전송 (일반적으로 하나의 기기에만 연결됨)
            nodes.forEach { node ->
                try {
                    messageClient.sendMessage(
                        node.id,
                        messagePath,
                        data ?: byteArrayOf()
                    ).addOnSuccessListener {
                        Timber.tag(tag)
                            .d("Message sent successfully to ${node.displayName}: $messagePath")
                    }.addOnFailureListener { e ->
                        Timber.tag(tag).e(e, "Failed to send message to ${node.displayName}: $messagePath")
                    }
                } catch (e: Exception) {
                    Timber.tag(tag)
                        .e(e, "Error sending message to ${node.displayName}: $messagePath")
                }
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Timber.tag(tag).e(e, "Error in sendMessage")
        }
    }
}
