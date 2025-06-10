package com.ddanddan.ddanddan.util

import android.content.Context
import com.google.android.gms.wearable.Node
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable
import timber.log.Timber

object WatchToPhoneUtils {

    /**
     * 워치 연결 여부 확인
     */
    private fun checkWatchConnection(
        context: Context,
        onConnected: (List<Node>) -> Unit,
        onNotConnected: () -> Unit
    ) = context.run {
        Wearable.getNodeClient(this).connectedNodes
            .addOnSuccessListener { nodes ->
                if (nodes.isNotEmpty()) {
                    Timber.tag("kangmi").d("connect")
                    onConnected(nodes) // 연결된 노드 전달
                    Timber.d("Connected to phone: ${nodes.map { it.displayName }}")
                } else {
                    Timber.tag("kangmi").d("not connect")
                    onNotConnected() // 연결 안 됨
                    Timber.d("No connected phone.")
                }
            }
            .addOnFailureListener { e ->
                Timber.e("Failed to check connection: ${e.message}")
            }
    }

    /**
     * 데이터를 폰으로 전송
     */
    private fun sendCaloriesDataToPhone(context: Context, calories: Double) = context.run {
        val dataClient = Wearable.getDataClient(this)

        val putDataReq = PutDataMapRequest.create("/calories_data").run {
            dataMap.putDouble("calories", calories)
            dataMap.putLong("timeStamp", System.currentTimeMillis())
            asPutDataRequest()
        }

        dataClient.putDataItem(putDataReq)
            .addOnSuccessListener {
                Timber.d("Calories data sent successfully: $calories")
            }
            .addOnFailureListener { e ->
                Timber.e("Failed to send calories data: ${e.message}")
            }
    }

    /**
     * 연결 상태 확인 후 데이터 전송
     */
    fun checkAndSendData(context: Context, calories: Double) {
        checkWatchConnection(
            context,
            onConnected = {
                sendCaloriesDataToPhone(context, calories) // 연결되었으면 데이터 전송
            },
            onNotConnected = {
                Timber.d("Phone not connected. Data will be stored for later.")
            }
        )
    }

    /**
     * 연결 상태 확인 후 토큰 만료 신호 전송
     */
    fun checkAndSendTokenExpired(context: Context) {
        checkWatchConnection(
            context,
            onConnected = {
                sendTokenExpired(context)
            },
            onNotConnected = {
                Timber.d("Phone not connected. Token refresh request cannot be sent.")
            }
        )
    }

    /**
     * 토큰 만료 신호 전송
     */
    private fun sendTokenExpired(context: Context) = context.run {
        val dataClient = Wearable.getDataClient(this)

        val putDataReq = PutDataMapRequest.create("/refresh_token_expired").run {
            dataMap.putLong("timestamp", System.currentTimeMillis())
            asPutDataRequest()
        }

        dataClient.putDataItem(putDataReq)
            .addOnSuccessListener {
                Timber.d("Token refresh request sent successfully.")
            }
            .addOnFailureListener { e ->
                Timber.e("Failed to send token refresh request: ${e.message}")
            }
    }
}
