package com.ddanddan.ddanddan.util

import android.content.Context
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.ext.showDebugToast
import com.google.android.gms.wearable.Node
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable
import timber.log.Timber

/**
 * 전제 조건
 * 1. 워치와 스마트폰이 연결되어있어야 함
 * 2. data를 보내는 스마트폰 앱의 applicationId와 수신하는 워치 쪽 applicationId가 일치해야 함
 */
object WatchUtils {

    /**
     * 워치 연결 상태를 확인하는 함수
     */
    fun checkWatchConnection(
        context: Context,
        onConnected: (List<Node>) -> Unit,
        onNotConnected: () -> Unit
    ) = context.run {
        Wearable.getNodeClient(this).connectedNodes
            .addOnSuccessListener { nodes ->
                if (nodes.isEmpty()) {
                    // 연결된 워치가 없을 경우 콜백 호출
                    onNotConnected()
                    showDebugToast(getString(R.string.no_watch_connected))
                } else {
                    // 연결된 워치가 있을 경우 연결된 노드 리스트를 콜백으로 전달
                    onConnected(nodes)
                    Timber.d("Connected Watches: ${nodes.map { it.displayName }}")
                }
            }
            .addOnFailureListener { e ->
                showDebugToast(getString(R.string.watch_connection_check_failed, e.message))
                Timber.e("Watch connection check failed: ${e.message}")
            }
    }

    /**
     * 워치로 액세스 토큰을 전송하는 함수
     */
    fun sendAccessTokenToWatch(context: Context, accessToken: String, node: Node) = context.run {
        val dataClient = Wearable.getDataClient(this)

        val putDataReq = PutDataMapRequest.create("/access_token").run {
            dataMap.putString("accessToken", accessToken)
            dataMap.putLong("timeStamp", System.currentTimeMillis())
            asPutDataRequest()
        }

        dataClient.putDataItem(putDataReq)
            .addOnSuccessListener {
                showDebugToast(getString(R.string.watch_send_token_success))
                Timber.d(getString(R.string.watch_send_token_success))
            }
            .addOnFailureListener { e ->
                showDebugToast(getString(R.string.watch_send_token_failure, e.message))
                Timber.e(getString(R.string.watch_send_token_failure, e.message))
            }
    }
}
