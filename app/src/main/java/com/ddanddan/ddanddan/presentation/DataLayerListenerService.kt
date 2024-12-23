package com.ddanddan.ddanddan.presentation

import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.ddanddan.ddanddan.presentation.setting.viewModel.SettingViewModel
import com.ddanddan.domain.repository.UserRepository
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

//todo - 추후 적절한 레이어로 분리 예정
@AndroidEntryPoint
class DataLayerListenerService : WearableListenerService() {

    @Inject
    lateinit var userRepository: UserRepository

    override fun onDataChanged(dataEvents: DataEventBuffer) {
        dataEvents.forEach { event ->
            if (event.type == DataEvent.TYPE_CHANGED) {
                val dataItem = event.dataItem

                when(dataItem.uri.path){
                    "/calories_data" -> {
                        val dataMap = DataMapItem.fromDataItem(dataItem).dataMap
                        val calories = dataMap.getDouble("calories")
                        val timeStamp = dataMap.getLong("timeStamp")
                        Log.d("Received calories data", "$calories at $timeStamp")

                        runBlocking {
                            userRepository.saveCalories(calories) //수신한 칼로리 저장
                        }
                    }
                    "/refresh_token_expired" -> {
                        //todo - 로그아웃
                    }

                    "/refresh_token_expired" -> {
                        val intent = Intent("com.ddanddan.ddanddan.Logout") //todo - 추후 유틸이나 상수로 분리
                        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
                    }
                }
            }
        }
    }

    override fun onMessageReceived(messageEvent: MessageEvent) {
        // 추가 메시지 처리가 필요한 경우 이곳에 구현
    }
}
